<?php
$output = array();

	$link = mysqli_connect("127.0.0.1", $username, $password, $database);

	$Vquery = "SELECT * FROM VOLUNTEER WHERE VOLUNTEER_ID = ?";
	$Pquery = "SELECT * FROM PATIENT WHERE PATIENT_ID = ?";

	if(isset($_REQUEST["ID"]))
	{
		$ID = $_REQUEST["ID"];
	}

	if(isset($_REQUEST["type"]))
	{
		$type = $_REQUEST["type"];
	}


	if($type == "V")
	{
		$stmt = mysqli_stmt_init($link);

		if(mysqli_stmt_prepare($stmt,$Pquery))
		{
			mysqli_stmt_bind_param($stmt,"i",$ID);
			mysqli_stmt_execute($stmt);
			$result = mysqli_stmt_get_result($stmt);

			if($row = mysqli_fetch_assoc($result))
			{
				echo $row["PATIENT_CONTACT"];
			}
			else
			{
				echo "error P";
			}

		}
		else
		{
			echo "sql error P";
		}
	}
	else if ($type=="P")
	{
		
		$stmt = mysqli_stmt_init($link);

		if(mysqli_stmt_prepare($stmt,$Vquery))
		{
			mysqli_stmt_bind_param($stmt,"i",$ID);
			mysqli_stmt_execute($stmt);
			$result = mysqli_stmt_get_result($stmt);

			if($row = mysqli_fetch_assoc($result))
			{
				echo $row["VOLUNTEER_CONTACT"];
			}
			else
			{
				echo "error V";
			}

		}
		else
		{
			echo "sql error V";
		}
	}
