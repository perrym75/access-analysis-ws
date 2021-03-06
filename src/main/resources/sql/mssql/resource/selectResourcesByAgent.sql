DECLARE @model_id BIGINT = ?
DECLARE @agent_id BIGINT = ?
DECLARE @active_model_id BIGINT

SET @active_model_id = (SELECT ROLE_MODEL_ID
                        FROM ROLE_MODEL
                        WHERE ACTIVE > 0)

IF @model_id = 0
  BEGIN
    SELECT
      res.RESOURCE_ID,
      res.PARENT_ID,
      res.NAME,
      res.SYSTEM_ID,
      rt.NAME as TYPE_NAME,
      res.AGENT_ID,
      CASE WHEN
        (SELECT COUNT(*)
         FROM
           LINK_ROLE2_RESOURCE
         WHERE
           ROLE2_ID IN (SELECT ROLE2_ID
                        FROM ROLE2
                        WHERE ROLE_MODEL_ID = @active_model_id) AND
           RESOURCE_ID = res.RESOURCE_ID) > 0
        THEN
          1
      WHEN
        (SELECT COUNT(*)
         FROM
           ACCESS_ENTRY ae
           INNER JOIN
           T_SP tsp
             ON
               ae.SECURITY_PRINCIPAL_ID = tsp.PARENT_ID
           INNER JOIN
           USER_ACCOUNT ua
             ON
               ua.SECURITY_PRINCIPAL_ID = tsp.UA_SP_ID
           INNER JOIN
           PERSONAGE p
             ON
               ua.PERSONAGE_ID = p.PERSONAGE_ID
         WHERE
           ae.RESOURCE_ID = res.RESOURCE_ID) > 0
        THEN
          2
      ELSE
        0
      END     AS STATUS
    FROM
      [RESOURCE] res
      INNER JOIN
      RESOURCE_TYPE rt
        ON
          res.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID
    WHERE
      res.AGENT_ID = @agent_id AND
      res.PARENT_ID IS NULL
  END
ELSE
  BEGIN
    SELECT
      res.RESOURCE_ID,
      res.PARENT_ID,
      res.NAME,
      res.SYSTEM_ID,
      rt.NAME as TYPE_NAME,
      res.AGENT_ID,
      CASE WHEN
        (SELECT COUNT(*)
         FROM
           LINK_ROLE2_RESOURCE
         WHERE
           ROLE2_ID IN (SELECT ROLE2_ID
                        FROM ROLE2
                        WHERE ROLE_MODEL_ID = @model_id) AND
           RESOURCE_ID = res.RESOURCE_ID) > 0 AND
        (SELECT COUNT(*)
         FROM
           LINK_ROLE2_RESOURCE
         WHERE
           ROLE2_ID IN (SELECT ROLE2_ID
                        FROM ROLE2
                        WHERE ROLE_MODEL_ID = @active_model_id) AND
           RESOURCE_ID = res.RESOURCE_ID) = 0
        THEN
          1
      WHEN
        (SELECT COUNT(*)
         FROM
           LINK_ROLE2_RESOURCE
         WHERE
           ROLE2_ID IN (SELECT ROLE2_ID
                        FROM ROLE2
                        WHERE ROLE_MODEL_ID = @model_id) AND
           RESOURCE_ID = res.RESOURCE_ID) = 0 AND
        (SELECT COUNT(*)
         FROM
           LINK_ROLE2_RESOURCE
         WHERE
           ROLE2_ID IN (SELECT ROLE2_ID
                        FROM ROLE2
                        WHERE ROLE_MODEL_ID = @active_model_id) AND
           RESOURCE_ID = res.RESOURCE_ID) > 0
        THEN
          2
      ELSE
        0
      END     AS STATUS
    FROM
      [RESOURCE] res
      INNER JOIN
      RESOURCE_TYPE rt
        ON
          res.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID
    WHERE
      res.AGENT_ID = @agent_id AND
      res.PARENT_ID IS NULL
  END