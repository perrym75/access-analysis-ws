SELECT
  DEPARTMENT_ID
  , PARENT_ID
  , NAME
FROM
  dbo.DEPARTMENT
WHERE
  IS_DELETED = 0
ORDER BY
  DEPARTMENT_ID