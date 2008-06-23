<?php
	include("global.php"); 
	include("session.php");
	include("config.php");

	$id = addslashes($_POST["id"]);
	$message = '';
	
	if( $id ) {
		
		$sql = "DELETE FROM sioti_profissao WHERE id = '$id' ";
	
		mysql_query($sql, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));
		mysql_close($connection);	
		$message = "MSG_PROFISSAO_DELETE_OK";
	}
	else {
		$message = "MSG_ERROR_PROFISSAO_DELETE_NO_ID";	
	}
	
	echo $message;

?>