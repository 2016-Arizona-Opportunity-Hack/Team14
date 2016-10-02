<?php

Server: samplemilk.database.windows.net,1433 \r\nSQL Database: milk\r\nUser Name: admin1234\r\n\r\nPHP Data Objects(PDO) Sample Code:\r\n\r\ntry {\r\n   $conn = new PDO ( \"sqlsrv:server = tcp:samplemilk.database.windows.net,1433; Database = milk\", \"admin1234\", \"{Milkmoney1}\");
\r\n    $conn->setAttribute( PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION );
\r\n}\r\ncatch ( PDOException $e ) {\r\n   print( \"Error connecting to SQL Server.\" );
\r\n   die(print_r($e));
\r\n}\r\n\rSQL Server Extension Sample Code:\r\n\r\n$connectionInfo = array(\"UID\" => \"admin1234@samplemilk\", \"pwd\" => \"{Milkmoney1}\", \"Database\" => \"milk\", \"LoginTimeout\" => 30, \"Encrypt\" => 1, \"TrustServerCertificate\" => 0);
\r\n$serverName = \"tcp:samplemilk.database.windows.net,1433\";
\r\n$conn = sqlsrv_connect($serverName, $connectionInfo);
?>