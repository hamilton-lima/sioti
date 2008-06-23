<?php
	include("global.php"); 
	include("session.php");
	include("config.php");

	$id = addslashes($_POST["id"]);

	$sql = "SELECT * FROM sioti_profissao ";
	if( $id ) {
		$sql = $sql . " WHERE id = '$id'";
	}
	
	$result = mysql_query($sql, $connection) or die(mysql_error("MSG_ERROR_CONNECTION"));
	$row = mysql_fetch_array($result);

	$response = array();		
	while( $row ){
		array_push( $response, $row );
		$row = mysql_fetch_array($result);	
	}

	mysql_close($connection);	
	echo json_encode($response);

?>