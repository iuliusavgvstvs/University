$(document).ready(function(){
    var nr = 0, ok=1;
    var first, second;
    $(".tableCell").click(function(e){
        console.log('click on cell');
        if(ok){
            nr++;
            first = $(this).children("div:first")
            first.addClass('arata');
            first.removeClass('ascunde');
            if(nr%2 == 1) {
                second = first;
            }
            if(nr%2 == 0) {
                ok=0;
                    if(first.html() != second.html()) {
                        setTimeout(function(){ 
                        first.addClass('ascunde');
                        first.removeClass('arata')
                        second.addClass('ascunde');
                        second.removeClass('arata');
                        ok=1;
                        }, 700);
                        
                    }
                    else{
                        setTimeout(function(){ 
                            first.removeClass('ascunde');
                            first.addClass('arata');
                            second.removeClass('ascunde');
                            second.addClass('arata');
                            }, 0);
                            ok=1;
                    }
                }
            }
    });
  });



