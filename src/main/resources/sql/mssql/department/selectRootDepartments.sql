SELECT
  DEPARTMENT_ID
  , PARENT_ID
  , NAME
FROM
  dbo.DEPARTMENT
WHERE
  PARENT_ID IS NULL AND
  IS_DELETED = 0