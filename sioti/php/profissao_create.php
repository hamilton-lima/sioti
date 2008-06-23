<?php
	include("global.php"); 
	include("session.php");
	include("config.php");

	$nome = addslashes($_POST["nome"]);
	$descricao = addslashes($_POST["descricao"]);
	$nivel_maximo = addslashes($_POST["nivel_maximo"]);

	$sql = "INSERT INTO sioti_profissao ( nome, descricao, nivel_maximo ) values ( ".
			"'$nome',  ".
			"'$descricao',  ".
			"'$nivel_maximo' ".
			")";
	
	mysql_query($sql, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));

	$sqlId = "select LAST_INSERT_ID() as id";
	$result = mysql_query($sqlId, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));
	$row = mysql_fetch_array($result);
	echo json_encode($row);

	mysql_close($connection);	

?>