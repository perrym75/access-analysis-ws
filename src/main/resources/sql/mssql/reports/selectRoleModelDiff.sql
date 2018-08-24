declare @rm1_id integer = ?
declare @rm2_id integer = ?
select
	a1.*
from
(
	select
		distinct
		r.RESOURCE_ID,
		r.NAME as RESOURCE_NAME,
		p.PERSONAGE_ID,
		e.FIRST_NAME + ' ' + e.LAST_NAME + ' ' + e.SECOND_NAME as PERSONAGE_NAME,
		r2.ROLE2_ID,
		r2.NAME ROLE_NAME,
		ar.ACCESS_RIGHT_ID,
		ar.DISPLAY_NAME as ACCESS_RIGHT_NAME
	from
		"RESOURCE" r
		inner join LINK_ROLE2_RESOURCE lrr on r.RESOURCE_ID = lrr.RESOURCE_ID
		inner join ROLE2 r2 on lrr.ROLE2_ID = r2.ROLE2_ID
		inner join V_PERSONAGE_ROLES2 vpr on vpr.VALUE_ID = r2.ROLE2_ID
		inner join PERSONAGE p on p.PERSONAGE_ID = vpr.PARENT_ID
		inner join EMPLOYEE e on e.EMPLOYEE_ID = p.EMPLOYEE_ID
		inner join V_ACCESS_RULE2_ARS varar on varar.PARENT_ID = lrr.ACCESS_RULE2_ID
		inner join ACCESS_RIGHT ar on ar.ACCESS_RIGHT_ID = varar.VALUE_ID
	where
		r2.ROLE_MODEL_ID = @rm1_id
) a1
where
	not exists (
		select
			*
		from
			"RESOURCE" r
			inner join LINK_ROLE2_RESOURCE lrr on r.RESOURCE_ID = lrr.RESOURCE_ID
			inner join ROLE2 r2 on lrr.ROLE2_ID = r2.ROLE2_ID
			inner join V_PERSONAGE_ROLES2 vpr on vpr.VALUE_ID = r2.ROLE2_ID
			inner join PERSONAGE p on p.PERSONAGE_ID = vpr.PARENT_ID
			inner join EMPLOYEE e on e.EMPLOYEE_ID = p.EMPLOYEE_ID
			inner join V_ACCESS_RULE2_ARS varar on varar.PARENT_ID = lrr.ACCESS_RULE2_ID
			inner join ACCESS_RIGHT ar on ar.ACCESS_RIGHT_ID = varar.VALUE_ID
		where
			r2.ROLE_MODEL_ID = @rm2_id and
			r.RESOURCE_ID = a1.RESOURCE_ID and
			p.PERSONAGE_ID = a1.PERSONAGE_ID and
			ar.ACCESS_RIGHT_ID = a1.ACCESS_RIGHT_ID
	)
union all
select
	a2.*
from
(
	select
		distinct
		r.RESOURCE_ID,
		r.NAME as RESOURCE_NAME,
		p.PERSONAGE_ID,
		e.FIRST_NAME + ' ' + e.LAST_NAME + ' ' + e.SECOND_NAME as PERSONAGE_NAME,
		r2.ROLE2_ID,
		r2.NAME ROLE_NAME,
		ar.ACCESS_RIGHT_ID,
		ar.DISPLAY_NAME as ACCESS_RIGHT_NAME
	from
		"RESOURCE" r
		inner join LINK_ROLE2_RESOURCE lrr on r.RESOURCE_ID = lrr.RESOURCE_ID
		inner join ROLE2 r2 on lrr.ROLE2_ID = r2.ROLE2_ID
		inner join V_PERSONAGE_ROLES2 vpr on vpr.VALUE_ID = r2.ROLE2_ID
		inner join PERSONAGE p on p.PERSONAGE_ID = vpr.PARENT_ID
		inner join EMPLOYEE e on e.EMPLOYEE_ID = p.EMPLOYEE_ID
		inner join V_ACCESS_RULE2_ARS varar on varar.PARENT_ID = lrr.ACCESS_RULE2_ID
		inner join ACCESS_RIGHT ar on ar.ACCESS_RIGHT_ID = varar.VALUE_ID
	where
		r2.ROLE_MODEL_ID = @rm2_id
) a2
where
	not exists (
		select
			*
		from
			"RESOURCE" r
			inner join LINK_ROLE2_RESOURCE lrr on r.RESOURCE_ID = lrr.RESOURCE_ID
			inner join ROLE2 r2 on lrr.ROLE2_ID = r2.ROLE2_ID
			inner join V_PERSONAGE_ROLES2 vpr on vpr.VALUE_ID = r2.ROLE2_ID
			inner join PERSONAGE p on p.PERSONAGE_ID = vpr.PARENT_ID
			inner join EMPLOYEE e on e.EMPLOYEE_ID = p.EMPLOYEE_ID
			inner join V_ACCESS_RULE2_ARS varar on varar.PARENT_ID = lrr.ACCESS_RULE2_ID
			inner join ACCESS_RIGHT ar on ar.ACCESS_RIGHT_ID = varar.VALUE_ID
		where
			r2.ROLE_MODEL_ID = @rm1_id and
			r.RESOURCE_ID = a2.RESOURCE_ID and
			p.PERSONAGE_ID = a2.PERSONAGE_ID and
			ar.ACCESS_RIGHT_ID = a2.ACCESS_RIGHT_ID
	)
