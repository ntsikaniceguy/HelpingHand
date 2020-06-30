<?php
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

	$query = "INSERT INTO CHAT(PATIENT_ID,VOLUNTEER_ID) VALUES(?,?)";
	$checkquery = "SELECT * FROM CHAT WHERE PATIENT_ID = ? AND VOLUNTEER_ID = ?";


	if(isset($_REQUEST["volID"]))
	{
		$volID = $_REQUEST["volID"];
	}

	if(isset($_REQUEST["patID"]))
	{
		$patID = $_REQUEST["patID"];	
	}


	$check = mysqli_stmt_init($link);

	if(mysqli_stmt_prepare($check,$checkquery))
	{
		mysqli_stmt_bind_param($check,"ii",$patID,$volID);
		mysqli_stmt_execute($check);
		mysqli_stmt_store_result($check);
		$result = mysqli_stmt_num_rows($check);

		if($result==0)
		{
			$stmt = mysqli_stmt_init($link);

			if(mysqli_stmt_prepare($stmt,$query))
			{
				mysqli_stmt_bind_param($stmt,"ii",$patID,$volID);
				mysqli_stmt_execute($stmt);
			}
			else
			{
				echo "error";
			}
		}
	}
?>
