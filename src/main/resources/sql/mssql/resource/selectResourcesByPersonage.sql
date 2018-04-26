DECLARE @model_id BIGINT = ?
DECLARE @pers_id BIGINT = ?
DECLARE @active_model_id BIGINT

SET @active_model_id = (SELECT ROLE_MODEL_ID
                        FROM dbo.ROLE_MODEL
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
              ts.PARENT_ID = sp.CHILD_ID AND
              CHARINDEX(':' + CAST(sp.PARENT_ID AS NVARCHAR) + ':', SP_PATH) = 0
      )
    SELECT
      DISTINCT
      res.RESOURCE_ID,
      res.PARENT_ID,
      res.NAME,
      res.SYSTEM_ID,
      rt.NAME AS TYPE_NAME,
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
      dbo.PERSONAGE pers
      INNER JOIN
      dbo.USER_ACCOUNT ua
        ON
          pers.PERSONAGE_ID = ua.PERSONAGE_ID
      INNER JOIN
      TreeSP tsp
        ON
          ua.SECURITY_PRINCIPAL_ID = tsp.CHILD_ID
      INNER JOIN
      dbo.ACCESS_ENTRY ae
        ON
          ae.SECURITY_PRINCIPAL_ID = tsp.PARENT_ID
      INNER JOIN
      dbo.[RESOURCE] res
        ON
          ae.RESOURCE_ID = res.RESOURCE_ID
      INNER JOIN
      dbo.RESOURCE_TYPE rt
        ON
          res.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID
    WHERE
      pers.IS_DELETED = 0 AND
      pers.PERSONAGE_ID = @pers_id
  END
ELSE
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
              ts.PARENT_ID = sp.CHILD_ID AND
              CHARINDEX(':' + CAST(sp.PARENT_ID AS NVARCHAR) + ':', SP_PATH) = 0
      )
    SELECT
      DISTINCT
      res.RESOURCE_ID,
      res.PARENT_ID,
      res.NAME,
      res.SYSTEM_ID,
      rt.NAME AS TYPE_NAME,
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
      dbo.PERSONAGE pers
      INNER JOIN
      dbo.USER_ACCOUNT ua
        ON
          pers.PERSONAGE_ID = ua.PERSONAGE_ID
      INNER JOIN
      TreeSP tsp
        ON
          ua.SECURITY_PRINCIPAL_ID = tsp.CHILD_ID
      INNER JOIN
      dbo.ACCESS_ENTRY ae
        ON
          ae.SECURITY_PRINCIPAL_ID = tsp.PARENT_ID
      INNER JOIN
      dbo.[RESOURCE] res
        ON
          ae.RESOURCE_ID = res.RESOURCE_ID
      INNER JOIN
      dbo.RESOURCE_TYPE rt
        ON
          res.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID
    WHERE
      pers.IS_DELETED = 0 AND
      pers.PERSONAGE_ID = @pers_id
  END