<?php
	include("global.php"); 
	include("config.php");

	$username = addslashes($_POST["u"]);
	$password = addslashes($_POST["p"]);

	$sql = "SELECT id, username FROM mdl_user ".
			"WHERE username = '$username' ".
			"AND password = md5('$password')";
	
	$result = mysql_query($sql, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));
	$row = mysql_fetch_array($result);
	
	if ($row) {
		$_SESSION["currentUser"] = $row['username'];
		$_SESSION["currentUserId"] = $row['id'];
		$id = $_SESSION["currentUserId"];
		
		$sqlRole = "SELECT shortname FROM mdl_role, mdl_role_assignments ".
					"WHERE mdl_role_assignments.roleid = mdl_role.id ".
					"AND mdl_role_assignments.userid = $id ";

		$resultRole = mysql_query($sqlRole, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));
		$rowRole = mysql_fetch_array($resultRole);

		$roles = array();		
		while ($rowRole) {
			array_push( $roles, $rowRole['shortname'] );
			$rowRole = mysql_fetch_array($resultRole);
		}
		mysql_free_result($resultRole);

		$_SESSION["currentRoles"] = $roles;
		$message = session_id();

	} else {
		$message = "-1";
	}
	
	mysql_free_result($result);
	mysql_close($connection);
	
	if( $message ){
		echo $message;	
	}

?>