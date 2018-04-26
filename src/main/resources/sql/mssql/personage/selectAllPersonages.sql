SELECT
  pers.PERSONAGE_ID
  , emp.FIRST_NAME
  , emp.SECOND_NAME
  , emp.LAST_NAME
  , pers.EMAIL
  , post.DEPARTMENT_ID
  ,
  (
    CASE
    (
      SELECT COUNT(*)
      FROM
        USER_ACCOUNT ua
      WHERE
        ua.PERSONAGE_ID = pers.PERSONAGE_ID
    )
    WHEN
      0
    THEN
      0
    ELSE
      1
    END
  ) AS STATUS
FROM
  POST post
  INNER JOIN
  PERSONAGE pers
    ON
      post.POST_ID = pers.POST_ID
    INNER JOIN
    EMPLOYEE emp
      ON
        pers.EMPLOYEE_ID = emp.EMPLOYEE_ID
WHERE
  post.IS_DELETED = 0 AND
  pers.IS_DELETED = 0 AND
  emp.IS_DELETED = 0