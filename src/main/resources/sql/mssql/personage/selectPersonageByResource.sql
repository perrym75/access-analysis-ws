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
  pers.PERSONAGE_ID,
  emp.FIRST_NAME,
  emp.SECOND_NAME,
  emp.LAST_NAME,
  pers.EMAIL,
  pos.NAME,
  post.DEPARTMENT_ID,
  1 AS STATUS
FROM
  [RESOURCE] res
  INNER JOIN
  ACCESS_ENTRY ae
    ON
      ae.RESOURCE_ID = res.RESOURCE_ID
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
  INNER JOIN
  EMPLOYEE emp
    ON
      pers.EMPLOYEE_ID = emp.EMPLOYEE_ID
  INNER JOIN
  POST post
    ON
      pers.POST_ID = post.POST_ID
  INNER JOIN
  POSITION pos
    ON
      post.POSITION_ID = pos.POSITION_ID
WHERE
  post.IS_DELETED = 0 AND
  pers.IS_DELETED = 0 AND
  emp.IS_DELETED = 0 AND
  pos.IS_DELETED = 0 AND
  res.RESOURCE_ID = ?
