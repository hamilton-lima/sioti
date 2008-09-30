<?php 
	include("global.php"); 
	include("session.php"); 

	echo 'chunk_width=' . $GLOBALS["chunk_width"] . ';'
		.'chunk_height=' . $GLOBALS["chunk_height"] . ';'
		.'max_chunk_x=' . $GLOBALS["max_chunk_x"] . ';'
		.'max_chunk_y=' . $GLOBALS["max_chunk_y"] . ';'
		.'start_point_a=' . $GLOBALS["start_point_a"] . ';'
		.'start_point_b=' . $GLOBALS["start_point_b"] . ';';

?>