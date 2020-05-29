<?php
include 'dbh.php';

$islimit=0;

if(isset($_GET['page'])){
  $page = $_GET['page']; 
}
else{
  $page = 1;
}


if (isset($_GET['limit']))
{
  $num_per_page = $_GET['limit']; 
  $islimit=1;
}
else{
  $num_per_page = 05;
}


$start_from = ($page-1)*$num_per_page;
$sql = "SELECT * from produs limit $start_from, $num_per_page";
$result = mysqli_query($conn, $sql);

?>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">

<style>
p{
  margin: 0 0 0 0;
}

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
#selectbox{
  display: flex;
  justify-content: flex-start;
  margin: 20px 0px 20px 0px;
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
<title>Problema 2</title>
</head>
<body>

<div id ="div1">

<div id = "selectbox">
<p>Se afiseaza: </p>
<form method="GET" style="margin-left:12px">
  <select name='numpage' onchange="window.location='index.php?limit='+this.value+'&page=1';" >
    <option <?php if ($num_per_page == '2') { ?>selected="true" <?php }; ?> value="2">2</option>
    <option  <?php if ($num_per_page == '3') { ?>selected="true" <?php }; ?> value="3">3</option>
    <option <?php if ($num_per_page== '5') { ?>selected="true" <?php }; ?>  value="5">5</option>
    <option  <?php if ($num_per_page== '10') { ?>selected="true" <?php }; ?> value="10">10</option>
    <option <?php if ($num_per_page == '20') { ?>selected="true" <?php }; ?>  value="20">20</option>
  </select>
</form>
</div>

<table>
  <tr>
    <th>ID</th>
    <th>Nume</th>
    <th>Descriere</th>
    <th>Categorie</th>
    <th>Cantitate</th>
    <th>Pret</th>
  </tr>
  <tr>
    <?php
    while($row = mysqli_fetch_assoc($result)){
      echo "<tr><td>";
      echo  $row['id'];
      echo "</td><td>";
      echo $row['nume'];
      echo "</td><td>";
      echo $row['descriere'];
      echo "</td><td>";
      echo $row['categorie'];
      echo "</td><td>";
      echo $row['cantitate'];
      echo "</td><td>";
      echo $row['pret'];
      echo "</td></td>";
    }
    ?>
  </tr>
</table>
<?php

$pr_querry= "select * from produs ";
$pr_result = mysqli_query($conn, $pr_querry);
$total_record = mysqli_num_rows($pr_result);

$total_page = ceil($total_record/$num_per_page);

echo '<div id="div2">';
if($page > 1){
if($islimit==1){
  echo "<a href='index.php?limit=".$num_per_page."&page=".($page-1)."' class='previous'>&laquo; Previous</a>";
}
else{
  echo "<a href='index.php?page=".($page-1)."' class='previous'>&laquo; Previous</a>";
}
}

if($total_page>$page){
  if($islimit==1){
    echo "<a href='index.php?limit=".$num_per_page."&page=".($page+1)."' class='next'>Next &raquo;</a>";
  }
  else
    echo "<a href='index.php?page=".($page+1)."' class='next'>Next &raquo;</a>";
}
echo '</div>'

?>
</div>

</body>
</html>