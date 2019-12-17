package controller;

import domain.Homework;
import domain.StudyYear;
import domain.validators.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.HomeworkService;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EditHomeworkController<ID> {

    @FXML
    public TextField IdField;

    @FXML
    public TextField startWeekField;

    @FXML
    public TextField deadlineWeekField;

    @FXML
    public TextField descriptionField;

    private HomeworkService<ID, Homework<ID>> homeworkService;
    Stage dialogStage;
    Homework hm;

    @FXML
    private void initialize(){}

    public void setService(HomeworkService service, Stage stage, Homework hm){
        this.homeworkService=service;
        this.dialogStage=stage;
        this.hm=hm;
        if(hm != null)
            setFields(hm);

        else {
            ID lastId = getLastID();
            int idcurentint = Integer.parseInt((String) lastId);
            idcurentint++;
            IdField.setText(String.valueOf(idcurentint));
            startWeekField.setText(String.valueOf(StudyYear.getSemesterWeek()));
        }

    }

    private void clearFields() {
        IdField.setText("");
        startWeekField.setText("");
        deadlineWeekField.setText("");
        descriptionField.setText("");
    }

    private void setFields(Homework hm) {
        IdField.setText((String) hm.getId());
        startWeekField.setText(Integer.toString(hm.getStartWeek()));
        deadlineWeekField.setText(Integer.toString(hm.getDeadlineWeek()));
        descriptionField.setText(hm.getDescription());
    }

    @FXML
    public void handleSave(){
        String id = IdField.getText();
        String  dweek = deadlineWeekField.getText();
        String desc = descriptionField.getText();
        Homework<String> h = new Homework<>(id, Integer.parseInt(dweek), desc);
        if(this.hm == null)
            saveHomework(h);
        else
            updateHomework(h);
    }

    private void updateHomework(Homework h) {
        try{
            Homework hm = this.homeworkService.update(h);
            if(hm!=null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Updating homework","The homework has been updated.");
        }catch (ValidationException e){
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }

    private void saveHomework(Homework h) {
        try {

            Homework hm = this.homeworkService.add(h);
            if (hm == null)
                dialogStage.close();
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Adding homework", "The homework has been added.");
            ID lastId = getLastID();
            homeworkService.setlastID(lastId);
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    private ID getLastID(){
        ID idrepo=homeworkService.getlastID();
        int idrepoint = Integer.parseInt((String) idrepo);
        Iterable<Homework<ID>> studs = homeworkService.getAll();
        List<Homework> studsList = StreamSupport.stream(studs.spliterator(), false)
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
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
