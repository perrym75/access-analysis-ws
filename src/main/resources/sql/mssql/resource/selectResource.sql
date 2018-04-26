DECLARE @model_id BIGINT = ?
DECLARE @res_id BIGINT = ?
DECLARE @active_model_id BIGINT

SET @active_model_id = (SELECT ROLE_MODEL_ID
                        FROM dbo.ROLE_MODEL
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
           dbo.LNK_ROLE_PERS_RES_AR
         WHERE
           ROLE2_ID IN (SELECT ROLE2_ID
                        FROM dbo.ROLE2
                        WHERE ROLE_MODEL_ID = @active_model_id) AND
           RESOURCE_ID = res.RESOURCE_ID) > 0
        THEN
          1
      ELSE
        0
      END     AS STATUS
    FROM
      dbo.[RESOURCE] res
      INNER JOIN
      dbo.RESOURCE_TYPE rt
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
           dbo.LNK_ROLE_PERS_RES_AR
         WHERE
           ROLE2_ID IN (SELECT ROLE2_ID
                        FROM dbo.ROLE2
                        WHERE ROLE_MODEL_ID = @model_id) AND
           RESOURCE_ID = res.RESOURCE_ID) > 0 AND
        (SELECT COUNT(*)
         FROM
           dbo.LNK_ROLE_PERS_RES_AR
         WHERE
           ROLE2_ID IN (SELECT ROLE2_ID
                        FROM dbo.ROLE2
                        WHERE ROLE_MODEL_ID = @active_model_id) AND
           RESOURCE_ID = res.RESOURCE_ID) = 0
        THEN
          1
      WHEN
        (SELECT COUNT(*)
         FROM
           dbo.LNK_ROLE_PERS_RES_AR
         WHERE
           ROLE2_ID IN (SELECT ROLE2_ID
                        FROM dbo.ROLE2
                        WHERE ROLE_MODEL_ID = @model_id) AND
           RESOURCE_ID = res.RESOURCE_ID) = 0 AND
        (SELECT COUNT(*)
         FROM
           dbo.LNK_ROLE_PERS_RES_AR
         WHERE
           ROLE2_ID IN (SELECT ROLE2_ID
                        FROM dbo.ROLE2
                        WHERE ROLE_MODEL_ID = @active_model_id) AND
           RESOURCE_ID = res.RESOURCE_ID) > 0
        THEN
          2
      ELSE
        0
      END     AS STATUS
    FROM
      dbo.[RESOURCE] res
      INNER JOIN
      dbo.RESOURCE_TYPE rt
        ON
          res.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID
    WHERE
      res.RESOURCE_ID = @res_id
  END
