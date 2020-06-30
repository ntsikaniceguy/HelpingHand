  $link = mysqli_connect("127.0.0.1", $username, $password, $database);

	$query = "UPDATE ORDERS SET VOLUNTEER_ID = ?,VOLUNTEER_EMAIL=? WHERE PATIENT_EMAIL = ? AND ORDER_DATE=?";

	if(isset($_REQUEST["volEmail"]))
	{
		$vemail = $_REQUEST["volEmail"];
	}

	if(isset($_REQUEST["volID"]))
	{
		$vID = $_REQUEST["volID"];
	}

	if(isset($_REQUEST["patEmail"]))
	{
		$pemail = $_REQUEST["patEmail"];
	}

	if(isset($_REQUEST["date"]))
	{
		$date = $_REQUEST["date"]; 
	}


	$stmt = mysqli_stmt_init($link);

	if(mysqli_stmt_prepare($stmt,$query))
	{
		mysqli_stmt_bind_param($stmt,"isss",$vID,$vemail,$pemail,$date);
		mysqli_stmt_execute($stmt);
		echo "done";
	}
	else
	{
		echo "error";
	}

?>
