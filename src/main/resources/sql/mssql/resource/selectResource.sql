DECLARE @model_id BIGINT = ?
DECLARE @res_id BIGINT = ?
DECLARE @active_model_id BIGINT

SET @active_model_id = (SELECT ROLE_MODEL_ID
                        FROM ROLE_MODEL
                        WHERE ACTIVE > 0)

IF @model_id = 0
  BEGIN
    ;
    WITH
        TreeSP
      ( ROOT_ID
        , PARENT_ID
        , CHILD_ID
        , LEV
        , SP_PATH )
      AS
      (
        SELECT
          SECURITY_PRINCIPAL_ID,
          SECURITY_PRINCIPAL_ID,
          SECURITY_PRINCIPAL_ID,
          0                                                                          AS LEV,
          CAST(':' + CAST(SECURITY_PRINCIPAL_ID AS NVARCHAR) + ':' AS NVARCHAR(MAX)) AS SP_PATH
        FROM
          USER_ACCOUNT
        UNION ALL
        SELECT
          ts.ROOT_ID,
          sp.PARENT_ID,
          sp.CHILD_ID,
          ts.LEV + 1,
          CAST(ts.SP_PATH + CAST(sp.PARENT_ID AS NVARCHAR) + ':' AS NVARCHAR(MAX))
        FROM
          SP_RELATION sp
          INNER JOIN
          TreeSP ts
            ON
              ts.PARENT_ID = sp.CHILD_ID
        WHERE
          CHARINDEX(':' + CAST(sp.PARENT_ID AS NVARCHAR) + ':', SP_PATH) = 0 AND
          sp.EXISTENCE = 1
      )
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
           TreeSP tsp
             ON
               ae.SECURITY_PRINCIPAL_ID = tsp.PARENT_ID
           INNER JOIN
           USER_ACCOUNT ua
             ON
               ua.SECURITY_PRINCIPAL_ID = tsp.ROOT_ID
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
      res.RESOURCE_ID = @res_id
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
      res.RESOURCE_ID = @res_id
  END
