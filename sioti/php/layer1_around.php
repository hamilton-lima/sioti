<?php 
	include("global.php"); 
	include("session.php"); 
	include("config.php");

	// calculate the current chunk
	$chunk_x = floor( $_POST["x"] / $GLOBALS["chunk_width"] ) * $GLOBALS["chunk_width"];
	$chunk_y = floor( $_POST["y"] / $GLOBALS["chunk_height"] ) * $GLOBALS["chunk_height"];

	// calculate the chunks around

	//west
	$west_x = $chunk_x - $GLOBALS["chunk_width"];
	if( $west_x < 0 ){
		$west_x = $GLOBALS["max_chunk_x"];
	}
	$west_y = $chunk_y;

	//east
	$east_x = $chunk_x + $GLOBALS["chunk_width"];
	if( $east_x > $GLOBALS["max_chunk_x"] ){
		$east_x = 0;
	}
	$east_y = $chunk_y;

	// north
	$north_y = $chunk_y - $GLOBALS["chunk_height"];
	if( $north_y < 0 ){
		$north_y = 0;
		$north_x = $chunk_x + floor($GLOBALS["max_chunk_x"]/2);
		$north_x = floor( $north_x / $GLOBALS["chunk_width"] ) * $GLOBALS["chunk_width"];
		if( $north_x > $GLOBALS["max_chunk_x"] ){
			$north_x = $north_x - $GLOBALS["max_chunk_x"];
		}
	}
	else {
		$north_x = $chunk_x;
	}

		//northwest
		$northwest_x = $north_x - $GLOBALS["chunk_width"];
		if( $northwest_x < 0 ){
			$northwest_x = $GLOBALS["max_chunk_x"];
		}
		$northwest_y = $north_y;
	
		//northeast
		$northeast_x = $north_x + $GLOBALS["chunk_width"];
		if( $northeast_x > $GLOBALS["max_chunk_x"] ){
			$northeast_x = 0;
		}
		$northeast_y = $north_y;
	
	
	// south
	$south_y = $chunk_y + $GLOBALS["chunk_height"];
	if( $south_y > $GLOBALS["max_chunk_y"] ){
		$south_y = $GLOBALS["max_chunk_y"];
		$south_x = $chunk_x + floor($GLOBALS["max_chunk_x"]/2);
		$south_x = floor( $south_x / $GLOBALS["chunk_width"] ) * $GLOBALS["chunk_width"];

		if( $south_x > $GLOBALS["max_chunk_x"] ){
			$south_x = $south_x - $GLOBALS["max_chunk_x"];
		}
	}
	else {
		$south_x = $chunk_x;
	}
	
		//southwest
		$southwest_x = $south_x - $GLOBALS["chunk_width"];
		if( $southwest_x < 0 ){
			$southwest_x = $GLOBALS["max_chunk_x"];
		}
		$southwest_y = $south_y;
	
		//southeast
		$southeast_x = $south_x + $GLOBALS["chunk_width"];
		if( $southeast_x > $GLOBALS["max_chunk_x"] ){
			$southeast_x = 0;
		}
		$southeast_y = $south_y;

			
	$sql = "SELECT x,y,data FROM sioti_layer1 WHERE (x = $chunk_x AND y = $chunk_y) "
		. " OR (x = $north_x AND y = $north_y) "
		. " OR (x = $northwest_x AND y = $northwest_y) "
		. " OR (x = $northeast_x AND y = $northeast_y) "
		. " OR (x = $south_x AND y = $south_y) "
		. " OR (x = $southwest_x AND y = $southwest_y) "
		. " OR (x = $southeast_x AND y = $southeast_y) "
		. " OR (x = $east_x AND y = $east_y) "
		. " OR (x = $west_x AND y = $west_y) ";

	$result = mysql_query($sql, $connection) or die(mysql_error("Connection Error !"));
	$row = mysql_fetch_array($result);
	
    while($row){ 
    	$x = $row['x'];
    	$y = $row['y'];
    	$position = "C";
		
    	if($x == $north_x && $y == $north_y) {
    		$position = "N";
    	} else if( $x == $northwest_x && $y == $northwest_y ){
    		$position = "NW";
    	} else if( $x == $northeast_x && $y == $northeast_y ){
    		$position = "NE";
    	} else if( $x == $south_x && $y == $south_y ){
    		$position = "S";
    	} else if( $x == $southeast_x && $y == $southeast_y ){
    		$position = "SE";
    	} else if( $x == $southwest_x && $y == $southwest_y ){
    		$position = "SW";
    	} else if( $x == $east_x && $y == $east_y ){
    		$position = "E";
    	} else if( $x == $west_x && $y == $west_y ){
    		$position = "W";
    	} 
    	
		echo $position .','. $x .','. $y .','. $row['data'] .';' ."\n";
		$row = mysql_fetch_array($result);
	}
	 
?>