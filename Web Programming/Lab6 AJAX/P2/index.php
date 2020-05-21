<?php
include 'dbh.php';
if(isset($_GET['page'])){
  $page = $_GET['page'];
}
else{
  $page = 1;
}
$num_per_page = 03;
$start_from = ($page-1)*$num_per_page;
$sql = "SELECT * from person limit $start_from, $num_per_page";
$result = mysqli_query($conn, $sql);

?>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">

<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
#div1{
  width: 600px;
}

#div2{
  display: flex;
  justify-content: flex-end;
  margin-left: auto;
    margin-right: auto;
  padding: 10px 0;
}
a {
  text-decoration: none;
  display: inline-block;
  padding: 8px 16px;
}

a:hover {
  background-color: #ddd;
  color: black;
}

.previous {
  background-color: #f1f1f1;
  color: black;
}

.next {
  background-color: #4CAF50;
  color: white;
}
</style>
<title>Problema 1</title>
</head>
<body>

<div id ="div1">
<table>
  <tr>
    <th>Nume</th>
    <th>Prenume</th>
    <th>Telefon</th>
    <th>Email</th>
  </tr>
  <tr>
    <?php
    while($row = mysqli_fetch_assoc($result)){
      echo "<tr><td>";
      echo  $row['nume'];
      echo "</td><td>";
      echo $row['prenume'];
      echo "</td><td>";
      echo $row['telefon'];
      echo "</td><td>";
      echo $row['email'];
      echo "</td></td>";
    }
    ?>
  </tr>
</table>
<?php

$pr_querry= "select * from person ";
$pr_result = mysqli_query($conn, $pr_querry);
$total_record = mysqli_num_rows($pr_result);

$total_page = ceil($total_record/$num_per_page);

echo '<div id="div2">';
if($page > 1){
  echo "<a href='index.php?page=".($page-1)."' class='previous'>&laquo; Previous</a>";
}

for($i=1;$i<$total_page; $i++){
  echo '';
}
if($i>$page){
 echo "<a href='index.php?page=".($page+1)."' class='next'>Next &raquo;</a>";
}
echo '</div>'

?>
</div>

</body>
</html>