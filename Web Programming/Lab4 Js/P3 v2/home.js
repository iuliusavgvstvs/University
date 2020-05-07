var nr = 0, ok=1;
var first, second;
var ascunde = "height: 100px; width: 100px;display: none;";
var arata = "height: 100px; width: 100px;display: block;";

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
			if(first.src != second.src) {
				setTimeout(function(){ 
				first.style = ascunde;
                second.style = ascunde; 
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