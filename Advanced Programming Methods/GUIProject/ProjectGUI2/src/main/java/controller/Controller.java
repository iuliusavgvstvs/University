package controller;

import domain.Grade;
import domain.Homework;
import domain.Raport;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.GradeService;
import service.HomeworkService;
import service.RaportService;
import service.StudentService;

import utils.events.Event;
import utils.events.GradeChangeEvent;
import utils.events.HomeworkChangeEvent;
import utils.events.StudentChangeEvent;
import utils.observer.Observer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Controller implements Observer<Event> {

    StudentService studentService;
    HomeworkService homeworkService;
    GradeService gradeService;
    RaportService raportService;
    ObservableList<Student> model = FXCollections.observableArrayList();
    ObservableList<Homework> model2 = FXCollections.observableArrayList();
    ObservableList<Grade> model3 = FXCollections.observableArrayList();

    @FXML
    TableView<Student> tableView;
    @FXML
    TableView<Homework> tableView2;
    @FXML
    TableView<Grade> tableView3;

    @FXML
    TableColumn<Student,String> IdColumn;
    @FXML
    TableColumn<Student, String> firstNameColumn;
    @FXML
    TableColumn<Student, String> lastNameColumn;
    @FXML
    TableColumn<Student, String> groupColumn;
    @FXML
    TableColumn<Student, String> emailColumn;

    @FXML
    TableColumn<Homework,String> IdHomeworkColumn;
    @FXML
    TableColumn<Homework,Integer> startWeekColumn;
    @FXML
    TableColumn<Homework,Integer> deadlineWeekColumn;
    @FXML
    TableColumn<Homework,String> descriptionColumn;


    @FXML
    TableColumn<Grade,String> gradeId;
    @FXML
    TableColumn<Grade, String> gradeStudentId;
    @FXML
    TableColumn<Grade, String> gradeHomeworkId;
    @FXML
    TableColumn<Grade, String> gradeDate;
    @FXML
    TableColumn<Grade, String> gradeTeacher;
    @FXML
    TableColumn<Grade, String> gradeFeedback;
    @FXML
    TableColumn<Grade, String> gradeValue;



    @FXML
    TextField idStudentField;
    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField groupField;
    @FXML
    TextField emailFiedl;

    @FXML
    TextField idHomeworkField;
    @FXML
    TextField startWeekField;
    @FXML
    TextField deadlineWeekField;
    @FXML
    TextField descriptionField;

    @FXML
    RadioButton studentsBtn;
    @FXML
    RadioButton homeworksBtn;
    @FXML
    RadioButton gradesBtn;


    @FXML
    Button addBtn;
    @FXML
    Button updateBtn;
    @FXML
    Button removeBtn;


    @FXML
    Button gradeRaportBtn;
    @FXML
    Button hardestRaportBtn;
    @FXML
    Button examRaportBtn;
    @FXML
    Button hmsattimeRaportBtn;


    public void setStudentService(StudentService studService){
        studentService= studService;
        studentService.addObserver(this);
        initModel(studService.getAll());
    }

    public void setHomeworkService(HomeworkService homeService){
        homeworkService = homeService;
        homeworkService.addObserver(this);
        initModel2(homeService.getAll());
    }

    public void setGradeService(GradeService studService){
        gradeService= studService;
        gradeService.addObserver(this);
        initModel3(gradeService.getAll());
    }

    public void setRaportService(RaportService rapService){
        raportService=rapService;
    }

    @FXML
    public void initialize() {
        IdColumn.setCellValueFactory((new PropertyValueFactory<Student,String>("id")));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
        tableView.setItems(model);

        IdHomeworkColumn.setCellValueFactory(new PropertyValueFactory<Homework,String>("id"));
        startWeekColumn.setCellValueFactory(new PropertyValueFactory<Homework,Integer>("StartWeek"));
        deadlineWeekColumn.setCellValueFactory(new PropertyValueFactory<Homework,Integer>("DeadlineWeek"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Homework,String>("Description"));
        tableView2.setItems(model2);

        gradeId.setCellValueFactory(new PropertyValueFactory<Grade,String>("id"));
        gradeStudentId.setCellValueFactory(new PropertyValueFactory<Grade,String>("StudentId"));
        gradeHomeworkId.setCellValueFactory(new PropertyValueFactory<Grade, String>("HomeworkID"));
        gradeDate.setCellValueFactory(new PropertyValueFactory<Grade,String>("Date"));
        gradeValue.setCellValueFactory(new PropertyValueFactory<Grade,String>("Grade"));
        gradeTeacher.setCellValueFactory(new PropertyValueFactory<Grade,String>("Prof"));
        gradeFeedback.setCellValueFactory(new PropertyValueFactory<Grade,String>("Feedback"));
        tableView3.setItems(model3);
    }

    private void initModel(Iterable<Student> students) {
        List<Student> studentsList = StreamSupport.stream(students.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(studentsList);
    }
    private void initModel2(Iterable<Homework> homeworks) {
        List<Homework> homeworksList = StreamSupport.stream(homeworks.spliterator(), false)
                .collect(Collectors.toList());
        model2.setAll(homeworksList);
    }

    private void initModel3(Iterable<Grade> grades){
        List<Grade> gradeList = StreamSupport.stream(grades.spliterator(), false)
                .collect(Collectors.toList());
        model3.setAll(gradeList);
    }

    @FXML
    private void searchStudents() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Iterable<Student> Students = studentService.getAll();
        if(!idStudentField.getText().equals("")){
            Students = studentService.searchById(Students, idStudentField.getText());
        }
        if(!firstNameField.getText().equals("")){
            Method getfirstname = Student.class.getMethod("getFirstName");
            Students = studentService.searchByStringMethod(Students,firstNameField.getText(),getfirstname);
        }
        if(!lastNameField.getText().equals("")) {
            Method getlastname = Student.class.getMethod("getLastName");
            Students = studentService.searchByStringMethod(Students, lastNameField.getText(), getlastname);
        }
        if(!groupColumn.getText().equals("")) {
            Method getgroup = Student.class.getMethod("getGroup");
            Students = studentService.searchByStringMethod(Students, groupField.getText(), getgroup);
        }
        if(!emailFiedl.getText().equals("")) {
            Method getemail = Student.class.getMethod("getEmail");
            Students = studentService.searchByStringMethod(Students, emailFiedl.getText(), getemail);
        }
        initModel(Students);
    }

    @FXML
    private void searchHomeworks() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Iterable<Homework> Homeworks = homeworkService.getAll();
        if(!idHomeworkField.getText().equals("")) {
            Method getid = Homework.class.getMethod("getId");
            Homeworks = homeworkService.searchByStringMethod(Homeworks, idHomeworkField.getText(), getid);
        }
        if(!startWeekField.getText().equals("")) {
            Method getstartweek= Homework.class.getMethod("getStartWeek");
            Homeworks = homeworkService.searchByIntegerMethod(Homeworks, startWeekField.getText(), getstartweek);
        }
        if(!deadlineWeekField.getText().equals("")) {
            Method getdeadlineweek= Homework.class.getMethod("getDeadlineWeek");
            Homeworks = homeworkService.searchByIntegerMethod(Homeworks, deadlineWeekField.getText(), getdeadlineweek);
        }
        if(!descriptionField.getText().equals("")) {
            Method getdescription= Homework.class.getMethod("getDescription");
            Homeworks = homeworkService.searchByStringMethod(Homeworks, descriptionField.getText(), getdescription);
        }
        initModel2(Homeworks);
    }

    @FXML
    private void deselectModel() {
        tableView.getSelectionModel().clearSelection();
        tableView2.getSelectionModel().clearSelection();
    }


    @FXML
    public void handleAddStudent(ActionEvent ev) {
        if(studentsBtn.isSelected())
             showStudentEditDialog(null);
        if(homeworksBtn.isSelected())
            showHomeworkEditDialog(null);
        if(gradesBtn.isSelected()){
            showGradeEditDialog(null);
        }
    }

    @FXML
    public void handleDeleteStudent(ActionEvent actionEvent) {
        if(studentsBtn.isSelected()) {
            Student selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Student deleted = studentService.delete(selected.getId());
                if (null != deleted)
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "The student has been deleted.");
            } else MessageAlert.showErrorMessage(null, "There's no student selected.");
        }
        if(homeworksBtn.isSelected()){
            Homework selected2 = tableView2.getSelectionModel().getSelectedItem();
            if(selected2 != null){
                Homework deleted2 = homeworkService.delete(selected2.getId());
                if(deleted2 != null)
                    MessageAlert.showMessage(null,Alert.AlertType.INFORMATION,"Delete","The homework has been deleted.")
                            ;
            }else MessageAlert.showErrorMessage(null, "There's no homework selected.");
        }
    }

    @FXML
    public void handleUpdateStudent(ActionEvent ev) {
        if(studentsBtn.isSelected()) {
            Student selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showStudentEditDialog(selected);
            } else
                MessageAlert.showErrorMessage(null, "There's no student selected.");
        }
        if(homeworksBtn.isSelected()){
            Homework selected2 = tableView2.getSelectionModel().getSelectedItem();
            if(selected2 != null){
                showHomeworkEditDialog(selected2);
            }else
                MessageAlert.showErrorMessage(null,"There's no homework selected.");
        }
    }

    @FXML
    public void showGradeperStudent(ActionEvent ev) {
        for( Object rs : raportService.getAverage(0)){
            Raport r = (Raport) rs;
            System.out.println(r.getSt().getDetails());
            System.out.println(r.getAverage());
        }

    }

    @FXML
    private void selectButton() {
        studentsBtn.fire();
        enableBtns();
    }

    @FXML
    private void selectButton2() {
        homeworksBtn.fire();
        enableBtns();
    }

    @FXML
    private void selectButton3(){
        gradesBtn.fire();
        addBtn.setDisable(false);
        disableBtns();
    }
    @FXML
    public void showRaportDialog() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("showraport.fxml"));

            AnchorPane root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Average grades");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditRaportController editMessageViewController = loader.getController();
            editMessageViewController.setService(dialogStage, raportService.getAverage(0),false);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showRaportDialog2() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("showraport.fxml"));

            AnchorPane root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Hardest homeworks");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditRaportController editMessageViewController = loader.getController();
            editMessageViewController.setService(dialogStage, raportService.getAverageByHomeworks(),true);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showRaportDialog3() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("showraport.fxml"));

            AnchorPane root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Average grades");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditRaportController editMessageViewController = loader.getController();
            editMessageViewController.setService(dialogStage, raportService.getAverage(4),false);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showRaportDialog4() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("showraport.fxml"));

            AnchorPane root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Average grades");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditRaportController editMessageViewController = loader.getController();
            editMessageViewController.setService(dialogStage, raportService.getGradedAtTime(),false);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showStudentEditDialog(Student st) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addstudent.fxml"));

            AnchorPane root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit student");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditStudentController editMessageViewController = loader.getController();
            editMessageViewController.setService(studentService, dialogStage, st);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHomeworkEditDialog(Homework hm) {

        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addhomework.fxml"));

            AnchorPane root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit homework");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);


            EditHomeworkController editMessageViewController = loader.getController();
            editMessageViewController.setService(homeworkService, dialogStage, hm);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showGradeEditDialog(Grade st) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addgrade.fxml"));

            AnchorPane root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit grade");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditGradeController editMessageViewController = loader.getController();
            editMessageViewController.setService(gradeService,homeworkService, dialogStage, st, studentService.getAll(), homeworkService.getAll());

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Event event) {
        if(event instanceof StudentChangeEvent)
            initModel(studentService.getAll());
        else
        if(event instanceof  HomeworkChangeEvent)
            initModel2(homeworkService.getAll());
        else
            if(event instanceof GradeChangeEvent)
                initModel3(gradeService.getAll());
    }

    @FXML
    private void clearStudentSearchFields(){
        idStudentField.clear();
        firstNameField.clear();
        lastNameField.clear();
        groupField.clear();
        emailFiedl.clear();
        initModel(studentService.getAll());
    }

    @FXML
    private void clearHomeworkSearchFields(){
        idHomeworkField.clear();
        startWeekField.clear();
        deadlineWeekField.clear();
        descriptionField.clear();
        initModel2(homeworkService.getAll());
    }

    @FXML
    private void disableBtns(){
        removeBtn.setDisable(true);
        updateBtn.setDisable(true);
    }
    @FXML
    private void enableBtns(){
        addBtn.setDisable(false);
        removeBtn.setDisable(false);
        updateBtn.setDisable(false);
    }

}
