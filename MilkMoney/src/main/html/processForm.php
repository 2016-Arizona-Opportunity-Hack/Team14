<?php
require_once 'milkData.php';
require_once 'protect.php';

$firstName = protect($_POST['firstName']);
$lastName = protect($_POST['lastName']);
$zip = protect($_POST['zip']);
$phoneNumber = protect($_POST['phoneNumber']);
$email = protect($_POST['email']);
$fbName = protect($_POST['fbName']);
$liName = protect($_POST['liName']);


$sql=  'INSERT INTO form_results
    (first_name,
     last_name,
     zip,
     phone_number,
     email,
     fb_name,
     li_name
    )VALUES
    (' . $firstName . ',
     "' . $lastName . '",
     "' . $zip . '",
     "' . $phoneNumber . '",
     "' . $email . '",
     "' . $fbName . '",
     "' . $liName . '")';
$results = my_sqlquery($sql,$cn) or die(sql_error($cn));
?>