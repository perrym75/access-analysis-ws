;WITH
    TreeSP
  ( ROOT_ID
    , PARENT_ID
    , CHILD_ID
    , LEV
    , SP_PATH )
  AS
  (
    SELECT
      SECURITY_PRINCIPAL_ID
      , SECURITY_PRINCIPAL_ID
      , SECURITY_PRINCIPAL_ID
      , 0 LEV
      , CAST(':' + CAST(SECURITY_PRINCIPAL_ID AS NVARCHAR) + ':' AS NVARCHAR(MAX)) AS SP_PATH
    FROM
      dbo.USER_ACCOUNT
    UNION ALL
    SELECT
      ts.ROOT_ID
      , sp.PARENT_ID
      , sp.CHILD_ID
      , ts.LEV + 1
      , CAST(ts.SP_PATH + CAST(sp.PARENT_ID AS NVARCHAR) + ':' AS NVARCHAR(MAX))
    FROM
      dbo.SP_RELATION sp
      INNER JOIN
      TreeSP ts
        ON
          ts.PARENT_ID = sp.CHILD_ID AND
          CHARINDEX(':' + CAST( sp.PARENT_ID AS NVARCHAR) + ':', SP_PATH) = 0
  )
SELECT
  DISTINCT
  ar.ACCESS_RIGHT_ID
  , ar.NAME
FROM
  dbo.[RESOURCE] res
  INNER JOIN
  dbo.ACCESS_ENTRY ae
    ON
      ae.RESOURCE_ID = res.RESOURCE_ID
  INNER JOIN
  dbo.ACCESS_RIGHT ar
    ON
      ae.ACCESS_RIGHT_ID = ar.ACCESS_RIGHT_ID
  INNER JOIN
  TreeSP tsp
    ON
      ae.SECURITY_PRINCIPAL_ID = tsp.PARENT_ID
  INNER JOIN
  dbo.USER_ACCOUNT ua
    ON
      ua.SECURITY_PRINCIPAL_ID = tsp.CHILD_ID
  INNER JOIN
  dbo.PERSONAGE pers
    ON
      pers.PERSONAGE_ID = ua.PERSONAGE_ID
  INNER JOIN
  dbo.EMPLOYEE emp
    ON
      pers.EMPLOYEE_ID = emp.EMPLOYEE_ID
  INNER JOIN
  dbo.POST post
    ON
      pers.POST_ID = post.POST_ID
WHERE
  post.IS_DELETED = 0
  AND pers.IS_DELETED = 0
  AND emp.IS_DELETED = 0
  AND pers.PERSONAGE_ID = ?
  AND res.RESOURCE_ID = ?
ORDER BY
  ar.ACCESS_RIGHT_ID
