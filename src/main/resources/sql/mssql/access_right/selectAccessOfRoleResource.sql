SELECT
  DISTINCT
  ar.ACCESS_RIGHT_ID,
  ar.DISPLAY_NAME
FROM
  ROLE2 r2
  INNER JOIN LINK_ROLE2_RESOURCE lrr ON r2.ROLE2_ID = lrr.ROLE2_ID
  INNER JOIN ACCESS_RULE2 ar2 ON lrr.ACCESS_RULE2_ID = ar2.ACCESS_RULE2_ID
  INNER JOIN V_ACCESS_RULE2_ARS ars ON ar2.ACCESS_RULE2_ID = ars.PARENT_ID
  INNER JOIN ACCESS_RIGHT ar ON ars.VALUE_ID = ar.ACCESS_RIGHT_ID
WHERE
  r2.ROLE2_ID = ?
  AND lrr.RESOURCE_ID = ?
ORDER BY
  ar.ACCESS_RIGHT_ID
