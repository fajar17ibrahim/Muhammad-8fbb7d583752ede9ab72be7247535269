<?php
error_reporting(0);
include_once "koneksi.php";
if ($_SERVER['REQUEST_METHOD'] =='POST'){
    
	class usr{}
	$username = $_POST["username"];
	$login_time = $_POST["login_time"];
	$login_state = "login";
    $password = md5($_POST["password"]);
    
    // $username = "fajaruser";
	// $password = md5("fajaruser");

	$query = mysqli_query($con, "SELECT * FROM user WHERE username='$username' AND pass='$password'");
	$row = mysqli_fetch_array($query);

	if (!empty($row)){
		mysqli_query($con, "update user set login_time='$login_time', login_state='$login_state' WHERE username='$username'");
        $response = new usr();
        $response->success = "1";
        $response->message = "Berhsil Login";
		$response->username = $row['username'];
        die(json_encode($response));
	    
	} else {
		$response = new usr();
		$response->success = 0;
		$response->message = "Telepon atau password salah";
		die(json_encode($response));
	}

	mysqli_close($con);
}
?>
