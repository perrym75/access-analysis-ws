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
WHERE
  pers.PERSONAGE_ID = ?
  AND res.RESOURCE_ID = ?
  AND ua.SECURITY_PRINCIPAL_ID = ?
ORDER BY
  ar.ACCESS_RIGHT_ID
