;WITH
    UNT
AS
  (SELECT
     UNIT_ID
     , PARENT_ID
     , DISPLAY_NAME
     , AGENT_ID
     , ROW_NUMBER() OVER (ORDER BY UNIT_ID) as ROW_NUM
   FROM
     UNIT)
SELECT
  *
FROM
  UNT
WHERE
  ROW_NUM >= ? AND
  ROW_NUM < ?
