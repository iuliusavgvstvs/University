function val() {
    d = document.getElementById("select_id").value;
    showDestinations(d)
}

function showDestinations(str) {
    console.log(str);
    var xhttp;
    if (str == "") {
      document.getElementById("txtHint").innerHTML = "Nu ati selectat un oras";
      return;
    }
    else{
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
      document.getElementById("txtHint").innerHTML = this.responseText;
      }
    };
    xhttp.open("GET", "getdestinations.php?q="+str, true);
    xhttp.send();
  }
}

