package repository;

import domain.Entity;
import domain.Student;
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

public class StudentXMLRepo<ID, E extends Entity> extends GeneralRepository<ID, Student<ID>> {
    private String file_name;
    private ID lastId;

    public ID getlastID(){
        return this.lastId;
    }
    public void setLastId(ID id){
        lastId=id;
    }

    public StudentXMLRepo(String file_name){
        this.file_name=file_name;
        loadData();
        lastId=(ID) "0";
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
                Node studentElement = children.item(i);
                if(studentElement instanceof Element){
                    Student student = createStudentFromElement((Element) studentElement);
                    super.save(student);
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
            Element root = document.createElement("students");
            document.appendChild(root);
            super.findAll().forEach(s->{
                Element e = createElementFromStudent(document,s);
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

    private Element createElementFromStudent(Document document, Student<ID> student) {
        Element element = document.createElement("student");
        element.setAttribute("id",student.getId().toString());

        Element firstName = document.createElement("firstName");
        firstName.setTextContent(student.getFirstName());
        element.appendChild(firstName);

        Element lastName = document.createElement("lastName");
        lastName.setTextContent(student.getLastName());
        element.appendChild(lastName);

        Element group = document.createElement("group");
        group.setTextContent(student.getGroup());
        element.appendChild(group);

        Element email = document.createElement("email");
        email.setTextContent(student.getEmail());
        element.appendChild(email);

        return element;
    }

    private Student createStudentFromElement(Element studentElement) {
        String id = studentElement.getAttribute("id");
        String firstName = studentElement.getElementsByTagName("firstName")
                .item(0)
                .getTextContent();
        String lastName = studentElement.getElementsByTagName("lastName")
                .item(0)
                .getTextContent();
        String group = studentElement.getElementsByTagName("group")
                .item(0)
                .getTextContent();
        String email = studentElement.getElementsByTagName("email")
                .item(0)
                .getTextContent();
        ID idstud = (ID) id;
        return new Student(idstud,firstName,lastName,group,email);
    }

    @Override
    public Student<ID> save(Student<ID> entity) {
        Student<ID> student = super.save(entity);
        if (student == null) {
            writeToFile();
        }
        return student;
    }
    @Override
    public Student<ID> delete(ID id) {
        Student<ID> student = super.delete(id);
        if (student != null) {
            writeToFile();
        }
        return student;
    }
    @Override
    public Student<ID> update(Student<ID> entity) {
        Student<ID> student = super.update(entity);
        if (student == null) {
            writeToFile();
        }
        return student;
    }
}
