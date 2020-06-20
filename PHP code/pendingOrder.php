<?php
	$username = "s2241186";
	$password = "Sokel@123";
	$database = "d2241186";
	$output = array();

	$link = mysqli_connect("127.0.0.1", $username, $password, $database);

	$query = "SELECT * FROM ORDERS WHERE VOLUNTEER_ID = NULL";

	$stmt = mysqli_stmt_init($link);

	if(mysqli_stmt_prepare($stmt,$query))
	{
		mysqli_stmt_execute($stmt);
		mysqli_stmt_store_result($stmt);

		while($row = $stmt->fetch_assoc())
		{
			$output[] = $row;
		}

		echo json_encode($output);
	}
?>
