<?php

require_once("./PaytmChecksum.php");
    
    
    $mid = $_POST['MID'];
    $order = $_POST['ORDERID'];
    $paytmParams = array();
    
$paytmParams["MID"] =$mid;
$paytmParams["ORDERID"] = $order;

$paytmParams["CUST_ID"] = $_POST['CUST_ID'];
$paytmParams["CHANNEL_ID"] = $_POST['CHANNEL_ID'];
$paytmParams["TXN_AMOUNT"] = $_POST['TXN_AMOUNT'];
$paytmParams["WEBSITE"] = $_POST['WEBSITE'];
$paytmParams["CALLBACK_URL"] = $_POST['CALLBACK_URL'];
$paytmParams["INDUSTRY_TYPE_ID"] = $_POST['INDUSTRY_TYPE_ID'];

$paytmChecksum = PaytmChecksum::generateSignature($paytmParams, 'qIem4ome7penC0Js');
$verifySignature = PaytmChecksum::verifySignature($paytmParams, 'qIem4ome7penC0Js', $paytmChecksum);

$response["token"] = $paytmChecksum;
$response["status"]= $verifySignature;

print(json_encode($response));
?>
