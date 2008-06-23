<?php
	// must be loaded before the session
	// the session.auto_start should be 0 (zero)
	// 
	session_start();
	$GLOBALS["action"] = $PHP_SELF;
	$GLOBALS["chunk_width"] = 80;
	$GLOBALS["chunk_height"] = 60;
	
	// if update the map database dimensions
	// get the max x, and y and add the number of columns/ lines
	$GLOBALS["max_chunk_x"] = 8720;
	$GLOBALS["max_chunk_y"] = 6240;
?>