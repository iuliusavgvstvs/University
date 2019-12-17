package controller;

import domain.Student;
import domain.validators.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.StudentService;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EditStudentController<ID> {
    @FXML
    public TextField Id;
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField group;
    @FXML
    public TextField email;

    private StudentService<ID, Student<ID>> studentService;
    Stage dialogStage;
    Student st;




    @FXML
    private void initialize(){
    }

    public void setService(StudentService serv, Stage stage, Student st){
        this.studentService = serv;
        this.dialogStage=stage;
        this.st=st;
        if(st != null)
            setFields(st);
        else {
            ID lastId = getLastID();
            int idcurentint = Integer.parseInt((String) lastId);
            idcurentint++;
            Id.setText(String.valueOf(idcurentint));
        }
    }


    @FXML
    public void handleSave(){
        String id=Id.getText();
        String fname=firstName.getText();
        String lname=lastName.getText();
        String gr=group.getText();
        String em=email.getText();
        Student m=new Student(id, fname, lname, gr, em);
        if (null == this.st)
            saveStudent(m);
        else
            updateStudent(m);

    }

    private ID getLastID(){
        ID idrepo=studentService.getlastID();
        int idrepoint = Integer.parseInt((String) idrepo);
        Iterable<Student<ID>> studs = studentService.getAll();
        List<Student> studsList = StreamSupport.stream(studs.spliterator(), false)
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
    private void saveStudent(Student st)
    {
        try {
            Student r= this.studentService.add(st);
            if (r==null)
                dialogStage.close();
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Adding student","The student has been added.");
            ID lastId=getLastID();
           // int idcurentint = Integer.parseInt((String) lastId);
           // idcurentint++;
           // lastId = (ID) Integer.toString(idcurentint);
            studentService.setlastID(lastId);
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
    }

    private void updateStudent(Student st) {
        try {
            Student r= this.studentService.update(st);
            if (r!=null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Updating student", "The student has been updated.");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }

    private void setFields(Student st) {
        Id.setText((String) st.getId());
        firstName.setText(st.getFirstName());
        lastName.setText(st.getLastName());
        group.setText(st.getGroup());
        email.setText(st.getEmail());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }

}
