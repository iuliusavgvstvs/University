package com.company.service;

import com.company.entity.Entity;
import com.company.entity.Grade;
import com.company.exceptions.ValidationException;
import com.company.repository.GradeXMLRepo;
import com.company.validator.GradeValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GradeService<ID, E extends Entity<ID>> implements Service<ID, Grade<ID>> {

    //private GeneralRepository<ID, E> repo;
    private GradeXMLRepo<ID, E> repo;
    private GradeValidator val;

    public GradeService(String file_name) {
        //repo = new GeneralRepository<>();
        repo = new GradeXMLRepo<>(file_name);
        val = new GradeValidator();
    }



    @Override
    public Grade<ID> find(ID id) throws ValidationException {
        return null;
    }

    @Override
    public Iterable<Grade<ID>> getAll() {
        return repo.findAll();
    }

    @Override
    public Grade<ID> delete(ID id) throws ValidationException {
        return repo.delete(id);
    }

    @Override
    public Grade<ID> update(Grade<ID> entity) throws ValidationException {
        return null;
    }

    @Override
    public Grade<ID> add(Grade<ID> entity) throws ValidationException {
        val.validate(entity);
        if (repo.findOne(entity.getId()) == null) {
            return repo.save(entity);
        } else
            throw new IllegalArgumentException("Student already graded.");

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



    public Stream<Grade<ID>> filterHomeworkWeek(ID id, int week) {
        Stream<Grade<ID>> result = getStreamFromIterable(getAll());
        return result.filter(x->x.getHomeworkID().equals(id)).filter(x->x.getWeek()==week);
    }

    public Stream<Grade<ID>> filterHomeworkTeacher(ID id, String prof) {
        Stream<Grade<ID>> result = getStreamFromIterable(getAll());
        return result.filter(x->x.getHomeworkID().equals(id)).filter(x->x.getProf().equals(prof));
    }

    public Stream<Grade<ID>> filterHomework(ID id) {
        Stream<Grade<ID>> result = getStreamFromIterable(getAll());
        return result.filter(x->x.getHomeworkID().equals(id));
    }

    private static <T> Stream<T> getStreamFromIterable(Iterable<T> iterable)
    {
        Spliterator<T>
                spliterator = iterable.spliterator();
        return StreamSupport.stream(spliterator, false);
    }

}
