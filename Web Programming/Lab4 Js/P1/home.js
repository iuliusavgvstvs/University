
function main(){
    const options = document.getElementsByTagName('option');
    for(const op of options){
        op.ondblclick = transfer;
    }
}

function transfer(){
    const cars = document.getElementById('cars');
    const cars2 = document.getElementById('cars2');
    var name = this.parentNode.id;
    if(name == 'cars'){
        cars2.append(this);
    }
    if(name == 'cars2'){
        cars.append(this);
    }
}

main();
