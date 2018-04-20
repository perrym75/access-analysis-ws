;WITH
    RMODEL
  AS
  (SELECT
     rm.ROLE_MODEL_ID,
     rm.ACTIVE,
     rm.NAME,
     ROW_NUMBER()
     OVER (
       ORDER BY rm.ROLE_MODEL_ID ) AS ROW_NUM
   FROM
     dbo.ROLE_MODEL AS rm)
SELECT *
FROM
  RMODEL
WHERE
  ROW_NUM >= ? AND
  ROW_NUM < ?
