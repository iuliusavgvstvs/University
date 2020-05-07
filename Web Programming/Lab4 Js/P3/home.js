var nr = 0, ok=1;
var first, second;
var arata = 'font-size: 60px; display: block;';
var ascunde = 'font-size: 60px; display: none;';

function main() {
	var tableCells = document.getElementsByClassName("tableCell");
	for(var tableCell of tableCells) {
        tableCell.onclick = showFace;
    }
	
}

function showFace() {
    if(ok){
    nr++;
    first = this.children[0];
    first.style = arata
    if(nr%2 == 1) {
		second = first;
	}
    if(nr%2 == 0) {
        ok=0;
			if(first.innerHTML != second.innerHTML) {
				setTimeout(function(){ 
				first.style = ascunde;
                second.style = first.style; 
                ok=1;
                }, 700);
				
            }
            else{
                setTimeout(function(){ 
                    first.style = arata;
                    second.style = arata;
                    }, 0);
                    ok=1;
            }
        }
    }
}


main();