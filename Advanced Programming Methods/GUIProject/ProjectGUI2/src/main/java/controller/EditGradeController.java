package controller;
import domain.Grade;
import domain.Homework;
import domain.Student;
import domain.StudyYear;
import domain.validators.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import service.GradeService;
import service.HomeworkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EditGradeController<ID> {

    private GradeService<ID, Grade<ID>> gradeService;
    private HomeworkService<ID, Homework<ID>> homeworkService;
    Stage dialogStage;
    Grade<ID> grade;
    Iterable<Homework> hms;


    @FXML
    ComboBox studs;
    @FXML
    ComboBox homeworks;
    @FXML
    ComboBox grades;

    @FXML
    TextField prof;
    @FXML
    TextField feedback;


    @FXML
    private void initialize(){}

    public void setService(GradeService<ID, Grade<ID>> service,HomeworkService<ID, Homework<ID>> homeservice, Stage stage, Grade<ID> grade, Iterable<Student> sts, Iterable<Homework> hs){
        this.gradeService=service;
        this.homeworkService=homeservice;
        this.dialogStage=stage;
        this.grade=grade;
        this.hms=hs;
        if(getEligibleStudents(sts).size()>0){
            studs.getItems().addAll(getstuds(getEligibleStudents(sts).values()));
            studs.setValue(getstuds(sts).get(0));
            refreshHomeworks();
            setGrade();
        }
        else{
            MessageAlert.showErrorMessage(null,"Add a new student/homework");
            handleCancel();
        }

    }
    @FXML
    public void handleSave(){
        ID lastId = getLastID();
        int idcurentint = Integer.parseInt((String) lastId);
        idcurentint++;
        Grade g = new Grade(Integer.toString(idcurentint), getId(studs),getId(homeworks),Integer.parseInt((String) grades.getValue()),prof.getText(),feedback.getText());
        saveGrade(g);
    }

    private void saveGrade(Grade h) {
        try {

            Grade hm = this.gradeService.add(h);
            if (hm == null)
                dialogStage.close();
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Adding grade", "The grade has been added.");
            ID lastId = getLastID();
            homeworkService.setlastID(lastId);
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }

    private ArrayList getstuds(Iterable<Student> all){
        ArrayList list = new ArrayList();
        for(Student st:all){
            String s = st.getId()+". "+st.getFirstName()+" "+st.getLastName();
            list.add(s);
        }
        return list;
    }

    private ArrayList gethomeworks(Iterable<Homework> all){
        ArrayList list = new ArrayList();
        for(Homework st:all){
            String s = st.getId()+". Start:"+st.getStartWeek()+" " + " End:"+st.getDeadlineWeek()+" "+st.getDescription();
            list.add(s);
        }
        return list;
    }

    private String getId(ComboBox btn){
        String id = (String) btn.getValue();
        return  id.split("\\.")[0];
    }

    @FXML
    private void refreshHomeworks(){
        String id = getId(studs);
        Iterable<Homework> hms2= hms;
        homeworks.getItems().setAll(gethomeworks(getHomeworks(id,hms2).values()));
        homeworks.setValue(gethomeworks(getHomeworks(id,hms2).values()).get(getCloserDeadline(getHomeworks(id,hms2).values())));
        setGrade();

    }
    @FXML
    private void refreshFeedback(){
        if(grades.getValue().equals("10"))
            feedback.setText("Studentul a predat la timp laboratorul");
        else if(Integer.parseInt(String.valueOf(grades.getValue()))==1){
            feedback.setText("Studentul a intarziat mai mult de 2 ori");
        }
        else{
            int nr_intarzieri = 10- Integer.parseInt(String.valueOf(grades.getValue()));
            feedback.setText("Studentul a intarziat de "+nr_intarzieri+" ori");
        }
    }

    private int getCloserDeadline(Iterable<Homework> homeworkslist){
        int poz=0, i=0;
        int min=10;
        for(Homework h: homeworkslist){
            if(h.getDeadlineWeek()-StudyYear.getSemesterWeek()< min){
                min=h.getDeadlineWeek()-h.getStartWeek();
                poz=i;
            }
            i++;
        }
        return poz;
    }

    @FXML
    private void setGrade(){
        ArrayList list = new ArrayList();
        if(!homeworks.getSelectionModel().isEmpty())
        if(!getId(homeworks).equals("")) {
            Homework hm = homeworkService.find((ID) getId(homeworks));
            int grade = hm.getMaxGrade(0);
            for (int i = grade; i >= 1; i--)
                list.add(Integer.toString(i));
            grades.getItems().setAll(list);
            grades.setValue(list.get(0));
        }
        refreshFeedback();
        }


    private HashMap<Integer, Homework> getHomeworks(String idStudent, Iterable<Homework> homeworks){
        HashMap<Integer, Homework> hms2= new HashMap<>();
        for(Homework h: homeworks)
            hms2.put(Integer.parseInt((String)h.getId()),h);
        for(Grade gr:gradeService.getAll()){
            if(gr.getStudentId().equals(idStudent)){
                for(Homework h: homeworks){
                    if(h.getId().equals(gr.getHomeworkID()))
                        hms2.remove(Integer.parseInt((String)h.getId()));
                }
            }

        }
        return hms2;
    }

    private ID getLastID(){
        ID idrepo=gradeService.getlastID();
        int idrepoint = Integer.parseInt((String) idrepo);
        Iterable<Grade<ID>> studs =gradeService.getAll();
        List<Grade> studsList = StreamSupport.stream(studs.spliterator(), false)
                .collect(Collectors.toList());
        if(!studsList.isEmpty()){
            ID idcurent = (ID) studsList.get(studsList.size()-1).getId();
            int idcurentint = Integer.parseInt((String) idcurent);
            if(idcurentint>idrepoint)
                return idcurent;
            return idrepo;
        }
        else
            return idrepo;
    }
    private HashMap<Integer,Student> getEligibleStudents(Iterable<Student> sts) {
        HashMap<Integer, Student> list = new HashMap<>();
        for (Student s : sts) {
            list.put(Integer.parseInt((String) s.getId()), s);
        }
        for (Student s : sts) {
            if (getHomeworks((String) s.getId(), hms).size() == 0)
                list.remove(Integer.parseInt((String) s.getId()));
        }
        return list;
    }
}
