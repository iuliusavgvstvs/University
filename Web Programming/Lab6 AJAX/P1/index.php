<?php
include 'dbh.php';
$sql = "SELECT distinct departure_station FROM routes";
$result = mysqli_query($conn, $sql);
?>

<!DOCTYPE html>
<html lang="en">
  
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<style>
table{
    border: 2px;
    border-style: solid;
    border-color: black;
    text-align: center;   
}
</style>
<title>Problema 1</title>
</head>

<body>
<p> Selectati orasul de plecare: </p>
<select name="objectSelector" ng-model="selectedObject" ng-options="obj for obj in Objects" required onchange="val()" id="select_id">
<option value="" ng-if="!selectedObject">---Selectare Oras---</option>
  <?php
    if(mysqli_num_rows($result) > 0){
        while($row = mysqli_fetch_assoc($result)){
  ?>
          <option><?php echo $row['departure_station']; ?></option>
<?php
        }
    } else {
      echo "Nu exista rute in baza de date.";
    }
  ?>
</select>
<p></p>
<div id="txtHint">Nu ati selectat un oras.</div>
</body>
<script src="./file.js"></script>
</html> 