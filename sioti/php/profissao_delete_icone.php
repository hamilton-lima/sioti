<?php
	include("global.php"); 
	include("session.php");
	include("config.php");

	$id = addslashes($_POST["id"]);

	if( $id ) {
		$sql = "UPDATE sioti_profissao SET " .
				"id_midia_icone = '0' ".
				"WHERE id = '$id'";
		
									
		mysql_query($sql, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));
		mysql_close($connection);	
		
		$message = "MSG_PROFISSAO_DELETE_ICONE_OK";
	}else {
		$message = "MSG_ERROR_PROFISSAO_DELETE_ICONE_NO_ID";	
	}
	
	echo $message;

?>