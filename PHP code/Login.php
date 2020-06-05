//Link to database details left out
<?php 
	$output = array();

	$link = mysqli_connect("127.0.0.1", $username, $password, $database);

	if(isset($_REQUEST["email"]))
	{
		$email = $_REQUEST["email"];
	}

	if(isset($_REQUEST["password"]))
	{
		$upass = $_REQUEST["password"];
	}

	$query1 = "SELECT * FROM VOLUNTEER WHERE VOLUNTEER_EMAIL = $email and VOLUNTEER_PASSWORD=$upass";

	$result1 = mysqli_query($link,$query1);

	if($result1!=0)
	{
		echo $result1;
	}
	else
	{
		$query2 = "SELECT * FROM PATIENT WHERE PATIENT_EMAIL = $email and PATIENT_PASSWORD = $upass"

		$result2 = mysqli_query($link,$query2);

		if($result2!=0)
		{
			echo $result2;
		}
		else
		{
			echo "NULL";
		}
	}
?>
