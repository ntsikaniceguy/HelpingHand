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

	$volunQuery = "SELECT PATIENT_TEXT FROM CHAT WHERE PATIENT_ID = $clientID AND VOLUNTEER_ID = $userID;";

	$volunEmpty = "UPDATE CHAT SET PATIENT_TEXT ='' WHERE PATIENT_ID = $clientID AND VOLUNTEER_ID = $userID;";

	$patQuery = "SELECT VOLUNTEER_TEXT FROM CHAT WHERE PATIENT_ID = $clientID AND VOLUNTEER_ID = $userID;";

	$patEmpty = "UPDATE CHAT SET VOLUNTEER_TEXT ='' WHERE PATIENT_ID = $clientID AND VOLUNTEER_ID = $userID;";

	if($type == "P")
	{	
		if ($r = mysqli_query($link,$patQuery)) 
		{
			while ($row=$r->fetch_assoc())
			{
				$output[]=$row;
			}
		}


		if($link->query($volunEmpty)==TRUE)
		{
			mysqli_close($link);
			echo json_encode($output);
		}
		
	}

	if($type == "V")
	{
		if ($r = mysqli_query($link,$volunQuery)) 
		{
			while ($row=$r->fetch_assoc())
			{
				$output[]=$row;
			}
		}

		if($link->query($patEmpty)==TRUE)
		{
			mysqli_close($link);
			echo json_encode($output);
		}
	}
?>
