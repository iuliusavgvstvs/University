<?php
require_once("dbh.php");

$sql = "SELECT destination_station FROM routes WHERE departure_station = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $_GET['q']);
$stmt->execute();
$res = $stmt->get_result();
echo "<table>";
while ($row = mysqli_fetch_assoc($res)) {
        echo "<tr><th>";
        echo $row['destination_station']; 
        echo "</th></tr>";
    }
echo "</table>";
?> 