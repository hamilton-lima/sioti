<?php 
	include("global.php"); 
	include("session.php"); 

	echo implode(",", $_SESSION["currentRoles"]);
	
?>