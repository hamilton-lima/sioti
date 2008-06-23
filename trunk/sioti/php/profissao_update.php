<?php
	include("global.php"); 
	include("session.php");
	include("config.php");

	$id = addslashes($_POST["id"]);
	$nome = addslashes($_POST["nome"]);
	$descricao = addslashes($_POST["descricao"]);
	$nivel_maximo = addslashes($_POST["nivel_maximo"]);

	if( $id ) {
		$sql = "UPDATE sioti_profissao SET " .
				"nome = '$nome',  ".
				"descricao = '$descricao',  ".
				"nivel_maximo = '$nivel_maximo' ".
				"WHERE id = '$id'";
		
									
		mysql_query($sql, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));
		mysql_close($connection);	
		
		$message = "MSG_PROFISSAO_UPDATE_OK";
	}else {
		$message = "MSG_ERROR_PROFISSAO_UPDATE_NO_ID";	
	}
	
	echo $message;

?>