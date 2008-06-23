<?php
	include("global.php"); 
	include("session.php");
	include("config.php");

	$id = addslashes($_POST["id"]);
	$id_midia = addslashes($_POST["id_midia_icone"]);

	if( $id ) {
		$sql = "UPDATE sioti_profissao SET " .
				"id_midia_icone = '$id_midia' ".
				"WHERE id = '$id'";
		
									
		mysql_query($sql, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));
		mysql_close($connection);	
		
		$message = "MSG_PROFISSAO_ADD_ICONE_OK";
	}else {
		$message = "MSG_ERROR_PROFISSAO_ADD_ICONE_NO_ID";	
	}
	
	echo $message;

?>