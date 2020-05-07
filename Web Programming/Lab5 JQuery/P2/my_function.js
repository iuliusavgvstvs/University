$(document).ready(function() {
  $("#contact_submit button").click(function(event){

    var nume = $( "#contact_name" );
    var data = $( "#contact_data" );
    var ndata = Date.parse(data.val());
    var varsta = $( "#contact_varsta" );
    var email = $( "#contact_email" );
    console.log(nume);
    console.log(data);
    console.log(varsta);
    console.log(email);

    let errors= [];
    let invalidParameters =[];

    if(nume.val() === ''){
        errors.push('nume');
        nume.addClass('error');
    }
    else{nume.addClass('noterror');}

    if(ndata> Date.now()||!ndata){
        errors.push('data nasterii');
        data.addClass('error');
    }
    else{data.addClass('noterror');}

    if(!Number.parseInt(varsta.val())){
        errors.push('varsta')
        varsta.addClass('error');
    }
    else{varsta.addClass('noterror');}

    if(! (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email.val()))){
        errors.push('email');
        email.addClass('error');
    }
    else{email.addClass('noterror');}
    if(errors== ''){
        alert('Datele sunt completate corect'); 
    }
    else
        {
          event.preventDefault();
            var err='Campurile ';
            for(i=0;i<errors.length-1;i++){
                err+= errors[i];
                err+= ' si ';
            }
            err+= errors[errors.length-1];
            err += ' sunt incorecte';
            alert(err);
        }
   
});
});