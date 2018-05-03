;
with
    TreeSP( ROOT_ID,
      PARENT_ID,
      CHILD_ID,
      LEV,
      FULL_PATH ) as
  ( select
      SECURITY_PRINCIPAL_ID,
      SECURITY_PRINCIPAL_ID,
      SECURITY_PRINCIPAL_ID,
      0 LEV,
      cast(
          '\' + cast(SECURITY_PRINCIPAL_ID as nvarchar(12))
          + '\' as nvarchar(max))
    from USER_ACCOUNT
    union all
    select
      TreeSP.ROOT_ID,
      spr.PARENT_ID,
      spr.CHILD_ID,
      TreeSP.LEV + 1,
      TreeSP.FULL_PATH +
      cast(spr.PARENT_ID as nvarchar(12)) + '\'
    from SP_RELATION spr inner join TreeSP
        on TreeSP.PARENT_ID = spr.CHILD_ID
    where charindex(cast('\' +
                         cast(spr.PARENT_ID as
                              nvarchar(12)) + '\'
                         as nvarchar(max)),
                    TreeSP.FULL_PATH) =
          0 ),
    UnionRights(SECURITY_PRINCIPAL_ID,
      RESOURCE_ID,
      ACCESS_RIGHTS) as
  (select
     distinct
     ae2.SECURITY_PRINCIPAL_ID,
     ae2.RESOURCE_ID,
     substring(
         (select ','
                 +
                 cast(
                     ae.ACCESS_RIGHT_ID
                     as
                     nvarchar(12))
          from
            ACCESS_ENTRY ae
          where
            ae.SECURITY_PRINCIPAL_ID
            =
            ae2.SECURITY_PRINCIPAL_ID
            and
            ae.RESOURCE_ID
            =
            ae2.RESOURCE_ID
          order by
            ae.ACCESS_RIGHT_ID
          for xml path ('')),
         2,
         1000) ACCESS_RIGHTS
   from
     ACCESS_ENTRY ae2 ),
    TreeDep( ROOT_ID, PARENT_ID, CHILD_ID, LEV ) as
  (select
     DEPARTMENT_ID,
     PARENT_ID,
     DEPARTMENT_ID,
     0
   from
     DEPARTMENT
   where
     DEPARTMENT_ID
     =
     10000
   union all
   select
     td.ROOT_ID,
     d.PARENT_ID,
     d.DEPARTMENT_ID,
     td.LEV
     +
     1
   from
     DEPARTMENT d inner join
     TreeDep td
       on
         td.CHILD_ID = d.PARENT_ID
   where
     d.IS_DELETED = 0
     and
     d.PARENT_ID
     =
     td.CHILD_ID )
select
  distinct
  prs.PERSONAGE_ID,
  ur.RESOURCE_ID,
  d.DEPARTMENT_ID,
  ur.ACCESS_RIGHTS,
  d.DIRECTOR_ID,
  pst.POST_ID
from
  DEPARTMENT d inner join
  POST pst
    on
      pst.DEPARTMENT_ID = d.DEPARTMENT_ID
  inner join
  PERSONAGE prs
    on
      prs.POST_ID = pst.POST_ID
  inner join
  USER_ACCOUNT ua
    on
      ua.PERSONAGE_ID = prs.PERSONAGE_ID
  inner join
  TreeSP
    on
      TreeSP.ROOT_ID = ua.SECURITY_PRINCIPAL_ID
  inner join
  UnionRights ur
    on
      ur.SECURITY_PRINCIPAL_ID = TreeSP.PARENT_ID
  inner join
  "RESOURCE" res
    on
      res.RESOURCE_ID = ur.RESOURCE_ID
  inner join
  TreeDep
    on
      TreeDep.CHILD_ID = d.DEPARTMENT_ID
where
  prs.IS_DELETED = 0
  and
  d.IS_DELETED = 0
  and
  pst.IS_DELETED = 0
order by
  d.DEPARTMENT_ID