package service;

import domain.Entity;
import domain.Grade;
import domain.validators.GradeValidator;
import domain.validators.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import repository.GradeXMLRepo;
import utils.events.ChangeEventType;
import utils.events.GradeChangeEvent;
import utils.events.HomeworkChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GradeService<ID, E extends Entity<ID>> implements Observable<GradeChangeEvent> {

    //private GeneralRepository<ID, E> repo;
    private GradeXMLRepo<ID, Grade> repo;
    private GradeValidator val;
    private List<Observer<GradeChangeEvent>> observers = new ArrayList<>();

    public GradeService(GradeXMLRepo<ID, Grade> rep) {
        //repo = new GeneralRepository<>();
        repo = rep;
        val = new GradeValidator();
    }


    public Iterable<Grade<ID>> getAll() {
        return repo.findAll();
    }

    public Grade<ID> add(Grade<ID> entity) throws ValidationException {
        val.validate(entity);
        Grade<ID> grade = repo.save(entity);
        if ( grade == null) {
            notifyObservers(new GradeChangeEvent(ChangeEventType.ADD,grade));
        }
        return grade;
    }
    public ID getlastID(){
        return repo.getlastID();
    }

    public void setlastID(ID id){
        repo.setLastId(id);
    }

    public void writeJson(Grade grade, String full_name) throws ParserConfigurationException {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root = document.createElement("Grades");
            document.appendChild(root);
            Element e = createElementFromGrade(document, grade);
            root.appendChild(e);

            //write Document to file
            Transformer transformer = TransformerFactory
                    .newInstance()
                    .newTransformer();
            try {
                BufferedWriter pw = new BufferedWriter(new FileWriter(full_name, true));
                Source source = new DOMSource(document);
                transformer.transform(source, new StreamResult(full_name));
            }
            catch (Exception er) {
                er.printStackTrace();
            }
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

    }

    private Element createElementFromGrade(Document document, Grade grade) {
        Element element = document.createElement("Grade");
        element.setAttribute("id",grade.getId().toString());

        Element feedback = document.createElement("feedback");
        feedback.setTextContent(grade.getFeedback());
        element.appendChild(feedback);

        Element homeworkID = document.createElement("homeworkID");
        homeworkID.setTextContent((String) grade.getHomeworkID());
        element.appendChild(homeworkID);

        Element gradeval = document.createElement("grade");
        gradeval.setTextContent(Integer.toString(grade.getGrade()));
        element.appendChild(gradeval);

        Element date= document.createElement("date");
        date.setTextContent(grade.getDate());
        element.appendChild(date);

        return element;
    }

    @Override
    public void addObserver(Observer<GradeChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<GradeChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(GradeChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }


}

