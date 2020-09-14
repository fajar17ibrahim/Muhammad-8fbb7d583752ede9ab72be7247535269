<?php
if ($_SERVER['REQUEST_METHOD'] =='POST'){
include_once "koneksi.php";

	class usr{}
	$username = $_POST["username"];
    $password = md5($_POST["password"]);
    
    // $username = "fajaruser1";
	// $password = md5("fajaruser");

	$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM user WHERE username='".$username."'"));

	if ($num_rows > 0){
		$response = new usr();
		$response->success = 0;
		$response->message = "Username sudah ada";
		die(json_encode($response));
	} else {
	    $q_user = mysqli_query($con, "INSERT INTO user (username, pass) VALUES('".$username."','".$password."')");
		if ($q_user) {
			$response = new usr();
			$response->success = 1;
			$response->message = "Register berhasil, silahkan login.";
			die(json_encode($response));
		} else {
			$response = new usr();
			$response->success = 0;
			$response->message = "Registrasi gagal!";
			die(json_encode($response));
		}
    }

	mysqli_close($con);
}
  ?>
