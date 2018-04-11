SELECT
  AGENT_ID,
  PARENT_ID,
  PLATFORM_ID,
  DISPLAY_NAME,
  DESCRIPTION
FROM
  dbo.AGENT
WHERE
  IS_DELETED = 0 AND
  PARENT_ID IS NULL