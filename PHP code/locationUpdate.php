<?php
	$username = "";
	$password = "";
	$database = "";
	

	$link = mysqli_connect("127.0.0.1", $username, $password, $database);


	if(isset($_REQUEST["type"]))
	{
		$type = $_REQUEST["type"];
	}

	if(isset($_REQUEST["id"]))
	{
		$ID = $_REQUEST["id"]);
	}


	if(isset($_REQUEST["pos"]))
	{
		$pos = $_REQUEST["pos"];
	}


	$volquery = "UPDATE VOLUNTEER SET VOLUNTEER_LOCATION = ? WHERE VOLUNTEER_ID = ?";
	$patquery = "UPDATE PATIENT SET PATIENT_ADDRESS = ? WHERE PATIENT_ID = ?";


	if($type == "V")
	{
		$stmt = mysqli_stmt_init($link);

		if(mysqli_stmt_prepare($stmt,$volquery))
		{
			mysqli_stmt_bind_param($stmt,"si",$pos,$ID);
			mysqli_stmt_execute($stmt);
		}
	}
	else if($type == "P")
	{
		$stmt = mysqli_stmt_init($link);

		if(mysqli_stmt_prepare($stmt,$patquery))
		{
			mysqli_stmt_bind_param($stmt,"si",$pos,$ID);
			mysqli_stmt_execute($stmt);
		}
	}

?>
