<?php
	$hostname = "xxxxx";
	$database = "xxxxx";
	$username = "xxxxx";
	$password = "xxxxx";
	$connection = mysql_connect($hostname, $username, $password) or trigger_error(mysql_error(),E_USER_ERROR);
	mysql_select_db($database); 
?>