package com.company.repository;

import com.company.entity.Entity;
import com.company.entity.Homework;
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

public class HomeworkXMLRepo<ID, E extends Entity> extends GeneralRepository<ID, Homework<ID>> {
    private String file_name;

    public HomeworkXMLRepo(String file_name){
        this.file_name=file_name;
        loadData();
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
                Node HomeworkElement = children.item(i);
                if(HomeworkElement instanceof Element){
                    Homework homework = createHomeworkFromElement((Element) HomeworkElement);
                    super.save(homework);
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
            Element root = document.createElement("Homeworks");
            document.appendChild(root);
            super.findAll().forEach(s->{
                Element e = createElementFromHomework(document,s);
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

    private Element createElementFromHomework(Document document, Homework<ID> homework) {
        Element element = document.createElement("Homework");
        element.setAttribute("id",homework.getId().toString());

        Element startWeek = document.createElement("startWeek");
        startWeek.setTextContent(Integer.toString(homework.getStartWeek()));
        element.appendChild(startWeek);

        Element deadlineWeek = document.createElement("deadlineWeek");
        deadlineWeek.setTextContent(Integer.toString(homework.getDeadlineWeek()));
        element.appendChild(deadlineWeek);

        Element description = document.createElement("description");
        description.setTextContent(homework.getDescription());
        element.appendChild(description);



        return element;
    }

    private Homework createHomeworkFromElement(Element HomeworkElement) {
        String id = HomeworkElement.getAttribute("id");
        String startWeek = HomeworkElement.getElementsByTagName("startWeek")
                .item(0)
                .getTextContent();
        String deadlineWeek = HomeworkElement.getElementsByTagName("deadlineWeek")
                .item(0)
                .getTextContent();
        String description = HomeworkElement.getElementsByTagName("description")
                .item(0)
                .getTextContent();
        ID idhm = (ID) id;
        Homework st = new Homework(idhm,Integer.parseInt(startWeek),Integer.parseInt(deadlineWeek),description);
        return st;
    }

    @Override
    public Homework<ID> save(Homework<ID> entity) {
        Homework<ID> homework = super.save(entity);
        if (homework == null) {
            writeToFile();
        }
        return homework;
    }
    @Override
    public Homework<ID> delete(ID id) {
        Homework<ID> homework = super.delete(id);
        if (homework != null) {
            writeToFile();
        }
        return homework;
    }
    @Override
    public Homework<ID> update(Homework<ID> entity) {
        Homework<ID> homework = super.update(entity);
        if (homework == null) {
            writeToFile();
        }
        return homework;
    }
}
