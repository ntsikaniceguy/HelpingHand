<?php
	$username = "";
	$password = "";
	$database = "";
	
	$link = mysqli_connect("127.0.0.1", $username, $password, $database);

	$query = "INSERT INTO ORDERS(PATIENT_ID,PATIENT_EMAIL,ORDER_DATE,ORDER_RECEIPT) VALUES(?,?,?,?)"

	if(isset($_REQUEST["id"]))
	{
		$ID = $_REQUEST["id"]);
	}

	if(isset($_REQUEST["email"]))
	{
		$email = $_REQUEST["email"]);
	}

	if(isset($_REQUEST["order"]))
	{
		$order = $_REQUEST["order"]);
	}

	$stmt = mysqli_stmt_int($link);

	if(mysqli_stmt_prepare($stmt,$query))
	{
		mysqli_stmt_bind_param($stmt,"isss",$ID,$email,date("Y-m-d"),$order);
		mysqli_stmt_execute($stmt);
		echo "done";
	}
	else
	{
		echo "sql error 1";
	}
?>
