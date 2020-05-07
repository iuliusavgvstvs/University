function main(){
    document.getElementById('Form').addEventListener('submit', event => event.preventDefault());

}


function trimitere(){
    var nume = document.getElementById('nume');
    var data = document.getElementById('data');
    var varsta = document.getElementById('varsta');
    var email = document.getElementById('email');
    let errors= [];
    let invalidParameters =[];

    if(nume.value === ''){
        errors.push('nume');
        invalidParameters.push(nume);
    }

    if(data.value > Date.now()||!data.value){
        errors.push('data nasterii');
        invalidParameters.push(data);
    }

    if(!Number.parseInt(varsta.value)){
        errors.push('varsta')
        invalidParameters.push(varsta);
    }

    if(! (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email.value))){
        errors.push('email');
        invalidParameters.push(email);
    }
    const inputs = document.getElementsByTagName('input');
    for(const inp of inputs) {
          inp.style='';
        }
    if(errors== ''){
        alert('Datele sunt completate corect')
    }
    else
        {
            var err='Campurile ';
            for(i=0;i<errors.length-1;i++){
                err+= errors[i];
                err+= ' si ';
            }
            err+= errors[errors.length-1];
            err += ' sunt incorecte';
            alert(err);
        }
    for(i=0;i<invalidParameters.length;i++){
        invalidParameters[i].style ='border: 2px;border-style: solid;border-color: red;';
    }
   
}

main();