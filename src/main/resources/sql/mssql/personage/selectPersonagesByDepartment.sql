SELECT
  pers.PERSONAGE_ID
  , emp.FIRST_NAME
  , emp.SECOND_NAME
  , emp.LAST_NAME
  , pers.EMAIL
FROM
  dbo.POST post
  INNER JOIN
  dbo.PERSONAGE pers
    ON
      post.POST_ID = pers.POST_ID
    INNER JOIN
    dbo.EMPLOYEE emp
      ON
        pers.EMPLOYEE_ID = emp.EMPLOYEE_ID
WHERE
  post.IS_DELETED = 0 AND
  pers.IS_DELETED = 0 AND
  emp.IS_DELETED = 0 AND
  post.DEPARTMENT_ID = ?