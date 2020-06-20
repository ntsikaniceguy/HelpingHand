<?php
	$username = "";
	$password = "";
	$database = "";
	$output = array();

	$link = mysqli_connect("127.0.0.1", $username, $password, $database);

	if(isset($_REQUEST["email"]))
	{
		$email = $_REQUEST["email"]; 
	}

	$volunQuery = "SELECT * FROM VOLUNTEER WHERE VOLUNTEER_EMAIL = ?";
	$patQuery = "SELECT * FROM PATIENT WHERE PATIENT_EMAIL = ?";
	$volSet = "UPDATE VOLUNTEER SET VOLUNTEER_PASSWORD = ? WHERE VOLUNTEER_EMAIL =?";
	$patSet = "UPDATE PATIENT SET PATIENT_PASSWORD = ? WHERE PATIENT_EMAIL=?";

	$stmt = mysqli_stmt_init($link);

	if(mysqli_stmt_prepare($stmt,$volunQuery))
	{
		mysqli_stmt_bind_param($stmt,"s",$email);
		mysqli_stmt_execute($stmt);
		mysqli_stmt_store_result($stmt);
		$vresult = mysqli_stmt_num_rows($stmt);
	}
	else
	{
		echo "sql error v";
	}


	$stmt = mysqli_stmt_init($link);

	if(mysqli_stmt_prepare($stmt,$patQuery))
	{
		mysqli_stmt_bind_param($stmt,"s",$email);
		mysqli_stmt_execute($stmt);
		mysqli_stmt_store_result($stmt);
		$presult = mysqli_stmt_num_rows($stmt);
	}
	else
	{
		echo "sql error p";
	}


	if($vresult==0 && $presult==0)
	{
		echo "NE";
	}
	else if($vresult>0 &&$presult==0)
	{
		$newpass = $email[0]+$email[1]+"1234";
		$hashpwd = password_hash($newpass, PASSWORD_DEFAULT);

		$stmt = mysqli_stmt_init($link);
		

		if(mysqli_stmt_prepare($stmt,$volSet))
		{
			mysqli_stmt_bind_param($stmt,"ss",$hashpwd,$email);
			mysqli_stmt_execute($stmt);

			$msg = "New password\n$newpass";
			mail($email,"Helping hand Password",$newpass);
		}
	}
	else if($vresult==0 && $presult>0)
	{
		$newpass = $email[0]+$email[1]+"1234";
		$hashpwd = password_hash($newpass, PASSWORD_DEFAULT);

		$stmt = mysqli_stmt_init($link);
		

		if(mysqli_stmt_prepare($stmt,$patSet))
		{
			mysqli_stmt_bind_param($stmt,"ss",$hashpwd,$email);
			mysqli_stmt_execute($stmt);

			$msg = "New password\n$newpass";
			mail($email,"Helping hand Password",$newpass);
		}
	}

?>
