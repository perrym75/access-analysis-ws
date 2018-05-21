;
WITH
    PERS
  AS
  (SELECT
     pers.PERSONAGE_ID,
     emp.FIRST_NAME,
     emp.SECOND_NAME,
     emp.LAST_NAME,
     pers.EMAIL,
     pos.NAME,
     post.DEPARTMENT_ID,
     (
       CASE
       (
         SELECT COUNT(*)
         FROM
           USER_ACCOUNT ua
         WHERE
           ua.PERSONAGE_ID = pers.PERSONAGE_ID
       )
       WHEN 0
         THEN 0
       ELSE 1
       END
     )                              AS STATUS,
     ROW_NUMBER()
     OVER (
       ORDER BY pers.PERSONAGE_ID ) AS ROW_NUM
   FROM
     POST post
     INNER JOIN PERSONAGE pers ON post.POST_ID = pers.POST_ID
     INNER JOIN EMPLOYEE emp ON pers.EMPLOYEE_ID = emp.EMPLOYEE_ID
     INNER JOIN POSITION pos ON post.POSITION_ID = pos.POSITION_ID
   WHERE
     post.IS_DELETED = 0 AND
     pers.IS_DELETED = 0 AND
     emp.IS_DELETED = 0 AND
     pos.IS_DELETED = 0)
SELECT *
FROM
  PERS
WHERE
  ROW_NUM >= ? AND ROW_NUM < ?