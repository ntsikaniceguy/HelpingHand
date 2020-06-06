Connection details left out
<?php
	$link = mysqli_connect("127.0.0.1", $student, $spassword, $database);
	//user type
	if(isset($_REQUEST["type"]))
	{
		$type = $_REQUEST["type"];
	}

	//email address
	if(isset($_REQUEST["email"]))
	{
		$email = $_REQUEST["email"];
	}

	//user name
	if(isset($_REQUEST["name"]))
	{
		$name = $_REQUEST["name"];
	}

	//user surname
	if(isset($_REQUEST["surname"]))
	{
		$surname = $_REQUEST["surname"];
	}

	//user location
	if(isset($_REQUEST["address"]))
	{
		$address = $_REQUEST["address"];
	}

	//user number
	if(isset($_REQUEST["contact"]))
	{
		$contact = $_REQUEST["contact"];
	}

	//user image
	if(isset($_REQUEST["image"]))
	{
		$image = $_REQUEST["image"];
	}


	if($type=="P")
	{
		$checkquery = "EXISTS(SELECT * FROM PATIENT WHERE PATIENT_EMAIL = $email)";
		$result =  mysqli_query($link,$checkquery);

		if($result == 0)
		{
			$addquery = "INSERT INTO PATIENT VALUES($email,$name,$surname,$address,$contact,$image)";

			if($link->query($addquery)==TRUE)
			{
				echo "done";
			}
			else
			{
				echo "failed";
			}
		}
		else
		{
			echo "NULL";
		}

	}

	if($type=="V")
	{
		$checkquery = "EXISTS(SELECT * FROM VOLUNTEER WHERE VOLUNTEER_EMAIL = $email)";
		$result =  mysqli_query($link,$checkquery);

		if($result == 0)
		{
			$addquery = "INSERT INTO VOLUNTEER VALUES($email,$name,$surname,$address,$contact,$image)";

			if($link->query($addquery)==TRUE)
			{
				echo "done";
			}
			else
			{
				echo "failed";
			}
		}
		else
		{
			echo "NULL";
		}
	}


?>
