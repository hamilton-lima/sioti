<?php
	$hostname = "dbserver2.interligando.com.br";
	$database = "sioti";
	$username = "sioti";
	$password = "sioti1637!!";
	$connection = mysql_connect($hostname, $username, $password) or trigger_error(mysql_error(),E_USER_ERROR);
	mysql_select_db($database); 
?>