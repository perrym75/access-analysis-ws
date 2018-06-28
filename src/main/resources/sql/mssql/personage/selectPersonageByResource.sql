DECLARE @res_id BIGINT = ?
SELECT
  DISTINCT
  a.PERSONAGE_ID,
  a.FIRST_NAME,
  a.SECOND_NAME,
  a.LAST_NAME,
  a.EMAIL,
  a.NAME,
  a.DEPARTMENT_ID,
  1 AS STATUS
FROM
  (
    SELECT
      pers.PERSONAGE_ID,
      emp.FIRST_NAME,
      emp.SECOND_NAME,
      emp.LAST_NAME,
      pers.EMAIL,
      pos.NAME,
      post.DEPARTMENT_ID
    FROM
      [RESOURCE] res
      INNER JOIN
      ACCESS_ENTRY ae
        ON
          ae.RESOURCE_ID = res.RESOURCE_ID
      INNER JOIN
      T_SP tsp
        ON
          ae.SECURITY_PRINCIPAL_ID = tsp.PARENT_ID
      INNER JOIN
      USER_ACCOUNT ua
        ON
          ua.SECURITY_PRINCIPAL_ID = tsp.UA_SP_ID
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
      res.RESOURCE_ID = @res_id
    UNION ALL
    SELECT
      pers.PERSONAGE_ID,
      emp.FIRST_NAME,
      emp.SECOND_NAME,
      emp.LAST_NAME,
      pers.EMAIL,
      pos.NAME,
      post.DEPARTMENT_ID
    FROM
      [RESOURCE] res
      INNER JOIN
      LINK_ROLE2_RESOURCE lrr
        ON
          res.RESOURCE_ID = lrr.RESOURCE_ID
      INNER JOIN
      V_PERSONAGE_ROLES2 vpr
        ON
          lrr.ROLE2_ID = vpr.VALUE_ID
      INNER JOIN
      PERSONAGE pers
        ON
          pers.PERSONAGE_ID = vpr.PARENT_ID
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
      res.RESOURCE_ID = @res_id
  ) a
