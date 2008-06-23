<?php
	include("global.php"); 
	include("session.php");
	include("config.php");

	
	$id_tipo_item = addslashes($_POST["id_tipo_item"]);
	$nome = addslashes($_POST["nome"]);
	$nivel_requerido = addslashes($_POST["nivel_requerido"]);
	$atributos = addslashes($_POST["atributos"]);
	$nivel = addslashes($_POST["nivel"]);

	$sql = "INSERT INTO sioti_item ( id_tipo_item, nome, nivel_requerido, atributos, nivel ) "
			."values ( ".
			"'$id_tipo_item',  ".
			"'$nome',  ".
			"'$nivel_requerido',  ".
			"'$atributos',  ".
			"'$nivel' ".
			")";
	
	mysql_query($sql, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));

	$sqlId = "select LAST_INSERT_ID() as id";
	$result = mysql_query($sqlId, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));
	$row = mysql_fetch_array($result);
	echo json_encode($row);

	mysql_close($connection);	

?>