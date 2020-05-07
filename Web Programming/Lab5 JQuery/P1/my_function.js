$(document).ready(function(){
    $("#cars").dblclick(function(event){
      $("#cars2").append(event.target);
    });
    $("#cars2").dblclick(function(event){
      $("#cars").append(event.target);
    });
  });