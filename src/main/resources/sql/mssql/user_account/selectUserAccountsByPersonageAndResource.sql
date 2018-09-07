SELECT
  sp.SECURITY_PRINCIPAL_ID,
  sp.DISPLAY_NAME,
  sp.UNIT_ID,
  sp.AGENT_ID,
  ua.SYSTEM,
  CASE WHEN ua.PERSONAGE_ID IS NULL
    THEN 0
  ELSE 1 END AS STATUS
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
  SECURITY_PRINCIPAL sp
    ON
      ua.SECURITY_PRINCIPAL_ID = sp.SECURITY_PRINCIPAL_ID
WHERE
  pers.PERSONAGE_ID = ? AND
  res.RESOURCE_ID = ?
