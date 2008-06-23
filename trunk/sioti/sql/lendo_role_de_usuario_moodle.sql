SELECT shortname FROM mdl_role, mdl_role_assignments 
WHERE mdl_role_assignments.roleid = mdl_role.id 
AND mdl_role_assignments.userid = $id_usuario
