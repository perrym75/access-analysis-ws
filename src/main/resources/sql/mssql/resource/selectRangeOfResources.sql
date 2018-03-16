;WITH
    RES
  AS
  (SELECT
     res.RESOURCE_ID
     , res.PARENT_ID
     , res.NAME
     , res.SYSTEM_ID
     , rt.NAME as TYPE_NAME
     , res.AGENT_ID, ROW_NUMBER() OVER (ORDER BY res.RESOURCE_ID) as ROW_NUM
   FROM
     dbo.[RESOURCE] as res
     INNER JOIN
     dbo.RESOURCE_TYPE as rt
       ON
         res.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID)
SELECT
  *
FROM
  RES
WHERE
  ROW_NUM >= ? AND
  ROW_NUM < ?