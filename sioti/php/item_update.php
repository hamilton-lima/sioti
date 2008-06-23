<?php
	include("global.php"); 
	include("session.php");
	include("config.php");

	$id = addslashes($_POST["id"]);
	$id_tipo_item = addslashes($_POST["id_tipo_item"]);
	$nome = addslashes($_POST["nome"]);
	$nivel_requerido = addslashes($_POST["nivel_requerido"]);
	$atributos = addslashes($_POST["atributos"]);
	$nivel = addslashes($_POST["nivel"]);

	if( $id ) {
		$sql = "UPDATE sioti_item SET " .
				"id_tipo_item = '$id_tipo_item',  ".
				"nome = '$nome',  ".
				"nivel_requerido = '$nivel_requerido',  ".
				"atributos = '$atributos', ".
				"nivel = '$nivel' ".
				"WHERE id = '$id'";
		
									
		mysql_query($sql, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));
		mysql_close($connection);	
		
		$message = "MSG_ITEM_UPDATE_OK";
	}else {
		$message = "MSG_ERROR_ITEM_UPDATE_NO_ID";	
	}
	
	echo $message;

?>