SELECT
  DISTINCT
  role.ROLE2_ID,
  role.ROLE_MODEL_ID,
  role.NAME
FROM
  ROLE2 role
  INNER JOIN ROLE_MODEL rm ON role.ROLE_MODEL_ID = rm.ROLE_MODEL_ID
  INNER JOIN LINK_ROLE2_RESOURCE l on role.ROLE2_ID = l.ROLE2_ID
WHERE role.ROLE2_ID = ? AND l.RESOURCE_ID = ? AND rm.ACTIVE = 1