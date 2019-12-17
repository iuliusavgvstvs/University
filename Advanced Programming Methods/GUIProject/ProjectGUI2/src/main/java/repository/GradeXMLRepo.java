package repository;

import domain.Entity;
import domain.Grade;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class GradeXMLRepo<ID, E extends Entity> extends GeneralRepository<ID, Grade<ID>> {
    private String file_name;
    private ID lastId;

    public GradeXMLRepo(String file_name){
        this.file_name=file_name;
        loadData();
        lastId=(ID) "0";
    }
    public ID getlastID(){
        return this.lastId;
    }
    public void setLastId(ID id){
        lastId=id;
    }

    private void loadData() {
        try{
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.file_name);
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for(int i=0;i<children.getLength();i++){
                Node GradeElement = children.item(i);
                if(GradeElement instanceof Element){
                    Grade grade = createGradeFromElement((Element) GradeElement);
                    super.save(grade);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void writeToFile(){
        try{
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root = document.createElement("Grades");
            document.appendChild(root);
            super.findAll().forEach(s->{
                Element e = createElementFromGrade(document,s);
                root.appendChild(e);
            });
            //write Document to file
            Transformer transformer = TransformerFactory
                    .newInstance()
                    .newTransformer();

            Source source = new DOMSource(document);
            transformer.transform(source,new StreamResult(file_name));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Element createElementFromGrade(Document document, Grade<ID> grade) {
        Element element = document.createElement("Grade");
        element.setAttribute("id",grade.getId().toString());

        Element studentId = document.createElement("studentId");
        studentId.setTextContent((String) grade.getStudentId());
        element.appendChild(studentId);

        Element homeworkID = document.createElement("homeworkID");
        homeworkID.setTextContent((String) grade.getHomeworkID());
        element.appendChild(homeworkID);

        Element gradeval = document.createElement("grade");
        gradeval.setTextContent(Integer.toString(grade.getGrade()));
        element.appendChild(gradeval);

        Element date= document.createElement("date");
        date.setTextContent(grade.getDate());
        element.appendChild(date);

        Element prof = document.createElement("teacher");
        prof.setTextContent(grade.getProf());
        element.appendChild(prof);

        Element feedback = document.createElement("feedback");
        feedback.setTextContent(grade.getFeedback());
        element.appendChild(feedback);

        return element;
    }

    private Grade createGradeFromElement(Element GradeElement) {
        String id = GradeElement.getAttribute("id");
        String studentId = GradeElement.getElementsByTagName("studentId")
                .item(0)
                .getTextContent();
        String homeworkID = GradeElement.getElementsByTagName("homeworkID")
                .item(0)
                .getTextContent();
        String grade = GradeElement.getElementsByTagName("grade")
                .item(0)
                .getTextContent();
        String date = GradeElement.getElementsByTagName("date")
                .item(0)
                .getTextContent();
        String prof = GradeElement.getElementsByTagName("teacher")
                .item(0)
                .getTextContent();
        String feedback = GradeElement.getElementsByTagName("feedback")
                .item(0)
                .getTextContent();

        ID idg = (ID) id;
        Grade st = new Grade(idg,studentId,homeworkID,Integer.parseInt(grade),date,prof,feedback);
        return st;
    }

    @Override
    public Grade<ID> save(Grade<ID> entity) {
        Grade<ID> grade = super.save(entity);
        if (grade == null) {
            writeToFile();
        }
        return grade;
    }
    @Override
    public Grade<ID> delete(ID id) {
        Grade<ID> grade = super.delete(id);
        if (grade != null) {
            writeToFile();
        }
        return grade;
    }
    @Override
    public Grade<ID> update(Grade<ID> entity) {
        Grade<ID> grade = super.update(entity);
        if (grade == null) {
            writeToFile();
        }
        return grade;
    }
}
