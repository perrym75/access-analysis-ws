SELECT
  res.RESOURCE_ID
  , res.PARENT_ID
  , res.NAME
  , res.SYSTEM_ID
  , rt.NAME as TYPE_NAME
  , res.AGENT_ID
FROM
  dbo.[RESOURCE] as res
  INNER JOIN
  dbo.RESOURCE_TYPE as rt
    ON
      res.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID