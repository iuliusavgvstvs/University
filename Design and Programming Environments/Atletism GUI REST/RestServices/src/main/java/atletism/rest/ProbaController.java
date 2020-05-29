package atletism.rest;


import atletism.repository.RepositoryException;
import atletism.repository.jbdc.ProbaDbRepository;
import model.Proba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atletism/probe")
public class ProbaController {

    private static final String template = "Hello, %s";

    public ProbaDbRepository getRepo(){
        return probaRepository;
    }
    @Autowired
    private ProbaDbRepository probaRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name",defaultValue = "World") String name){
        return String.format(template,name);
    }
    public void setRepo(ProbaDbRepository rep){
        this.probaRepository = rep;
    }

    @RequestMapping(method=RequestMethod.GET)
    public Proba[] getAll(){
        return probaRepository.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id){
        Proba proba = probaRepository.findOne(id);
        if(proba==null){
            return new ResponseEntity<String>("Proba not found", HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<Proba>(proba, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Proba create(@RequestBody Proba proba){
        return probaRepository.save(proba);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Proba update(@RequestBody Proba proba) {
        System.out.println("Updating proba ...");
        probaRepository.update(proba.getId(),proba);
        return proba;

    }

    @RequestMapping(value="/{probaname}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String probaname){
        System.out.println("Deleting proba ... "+probaname);
        try {
            probaRepository.delete(probaname);
            return new ResponseEntity<Proba>(HttpStatus.OK);
        }catch (RepositoryException ex){
            System.out.println("Ctrl Delete proba exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

/*
    @RequestMapping("/{proba}/name")
    public String name(@PathVariable String proba){
        Proba result=probaRepository.findBy(proba);
        System.out.println("Result ..."+result);

        return result.getName();
    }


 */


    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String probaError(RepositoryException e) {
        return e.getMessage();
    }


}
