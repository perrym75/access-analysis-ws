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
      0                                                                             LEV,
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
  DISTINCT
  ar.ACCESS_RIGHT_ID,
  ar.DISPLAY_NAME
FROM
  [RESOURCE] res
  INNER JOIN
  ACCESS_ENTRY ae
    ON
      ae.RESOURCE_ID = res.RESOURCE_ID
  INNER JOIN
  ACCESS_RIGHT ar
    ON
      ae.ACCESS_RIGHT_ID = ar.ACCESS_RIGHT_ID
  INNER JOIN
  TreeSP tsp
    ON
      ae.SECURITY_PRINCIPAL_ID = tsp.PARENT_ID
  INNER JOIN
  USER_ACCOUNT ua
    ON
      ua.SECURITY_PRINCIPAL_ID = tsp.ROOT_ID
  INNER JOIN
  PERSONAGE pers
    ON
      pers.PERSONAGE_ID = ua.PERSONAGE_ID
WHERE
  pers.PERSONAGE_ID = ?
  AND res.RESOURCE_ID = ?
  AND ua.SECURITY_PRINCIPAL_ID = ?
ORDER BY
  ar.ACCESS_RIGHT_ID
