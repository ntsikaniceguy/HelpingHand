Connection to database details left out
<?php
	$link = mysqli_connect("127.0.0.1", $username, $password, $database);

	if(isset($_REQUEST["email"])
	{
		$email = $_REQUEST["email"];
	}

	if(isset($_REQUEST["image"]))
	{
		$image = $_REQUEST["image"];
	}

	if(isset($_REQUEST["type"]))
	{
		$type = $_REQUEST["type"];
	}

	$queryP = "UPDATE PATIENT SET PATIENT_IMAGE = $image WHERE PATIENT_EMAIL = '$email';";
	$queryV = "UPDATE VOLUNTEER SET VOLUNTEER_IMAGE = $image WHERE VOLUNTEER_EMAIL = '$image';";


	if($type == "P")
	{
		if($link->query($queryP)==TRUE)
		{
			echo "Done";
		}
		else
		{
			echo "Falied";
		}
	}
	else
	{
		if($link->query($queryV)==TRUE)
		{
			echo "Done";
		}
		else
		{
			echo "Failed";
		}
	}
?>
