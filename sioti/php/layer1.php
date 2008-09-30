<?php 
	include("global.php"); 
	include("session.php"); 
	include("config.php");

	// calculate the current chunk
	$chunk_x = floor( $_POST["x"] / $GLOBALS["chunk_width"] ) * $GLOBALS["chunk_width"];
	$chunk_y = floor( $_POST["y"] / $GLOBALS["chunk_height"] ) * $GLOBALS["chunk_height"];
	
	$sql = "select * from sioti_layer1 where x = $chunk_x AND y = $chunk_y ";

	$result = mysql_query($sql, $connection) or die(mysql_error("Connection Error !"));
	$row = mysql_fetch_array($result);
				
    while($row){ 
		echo $row['x'] . ',' . $row['y'] . ',' . $row['data'];
		$row = mysql_fetch_array($result);
	}
	 
?>