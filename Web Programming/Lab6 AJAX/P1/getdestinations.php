<?php
require_once("dbh.php");

$sql = "SELECT destination_station FROM routes WHERE departure_station = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $_GET['q']);
$stmt->execute();
$stmt->bind_result($destination_station);

echo "<table>";
while ($stmt->fetch()) {
        echo "<tr><th>";
        echo $destination_station; 
        echo "</th></tr>";
    }
echo "</table>";
?> 