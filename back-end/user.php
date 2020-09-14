<?php
        include_once('koneksi.php');
        if ( $_SERVER['REQUEST_METHOD'] == 'GET' ) {
            // $username = $_GET['username'];
            $username = "fajaruser";
            if( $username ) {
                $query = "SELECT * FROM user where username='$username'";
                $result = mysqli_query($con,$query);
               
                $baris = mysqli_fetch_assoc($result);
                $array_data = $baris;
                
            }
            echo json_encode($array_data);
            mysqli_close($con);
        }
        
?>