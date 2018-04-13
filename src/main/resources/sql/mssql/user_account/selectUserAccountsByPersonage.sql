SELECT
  sp.SECURITY_PRINCIPAL_ID,
  sp.DISPLAY_NAME,
  sp.UNIT_ID,
  sp.AGENT_ID,
  CASE WHEN ua.PERSONAGE_ID IS NULL
    THEN 0
  ELSE 1 END AS STATUS
FROM
  dbo.USER_ACCOUNT ua
  INNER JOIN
  dbo.SECURITY_PRINCIPAL sp
    ON
      ua.SECURITY_PRINCIPAL_ID = sp.SECURITY_PRINCIPAL_ID
WHERE
  ua.PERSONAGE_ID = ?
