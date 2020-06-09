<?php
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

	if(isset($_REQUEST["UserID"]))
	{
		$userID = $_REQUEST["UserID"];
	}

	if(isset($_REQUEST["ClientID"]))
	{
		$clientID = $_REQUEST["ClientID"];
	}

	if(isset($_REQUEST["type"]))
	{
		$type = $_REQUEST["type"];
	}

	if(isset($_REQUEST["sent"]))
	{
		$text = isset($_REQUEST["sent"]);
	}


	$volunadd = "UPDATE CHAT SET VOLUNTEER_TEXT = VOLUNTEER_TEXT + '$text' WHERE PATIENT_ID = $clientID AND VOLUNTEER_ID = $userID;";
	$patadd = "UPDATE CHAT SET PATIENT_TEXT = VOLUNTEER_TEXT + '$text' WHERE PATIENT_ID = $clientID AND VOLUNTEER_ID = $userID;"


	if($type == "V")
	{
		if($link->query($volunadd))
		{
			mysqli_close($link);
		}
	}
	else
	{
		if($link->query($patadd))
		{
			mysqli_close($link);
		}
	}
?>
