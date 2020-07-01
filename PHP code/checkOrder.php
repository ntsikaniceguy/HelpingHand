<?php
  $link = mysqli_connect("127.0.0.1", $username, $password, $database);
	$query = "SELECT * FROM ORDERS WHERE PATIENT_ID = ? AND ORDER_DATE=?";

	if(isset($_REQUEST["id"]))
	{
		$ID = $_REQUEST["id"];
	}

	if(isset($_REQUEST["date"]))
	{
		$date = $_REQUEST["date"];
	}


	$stmt = mysqli_stmt_init($link);

	if(mysqli_stmt_prepare($stmt,$query))
	{
		mysqli_stmt_bind_param($stmt,"is",$ID,$date);
		mysqli_stmt_execute($stmt);
		$result = mysqli_stmt_get_result($stmt);

		if($row = mysqli_fetch_assoc($result))
		{
			if(is_null($row["VOLUNTEER_ID"]))
			{
				echo "no";
			}
			else
			{
				echo json_encode($row);
			}
		}
	}
	else
	{
		echo "error";
	}
?>
