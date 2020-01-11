package main;

import controller.Controller;
import domain.Grade;
import domain.Homework;
import domain.Raport;
import domain.Student;
import domain.validators.ValidationException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.GradeXMLRepo;
import repository.HomeworkXMLRepo;
import repository.StudentXMLRepo;
import service.GradeService;
import service.HomeworkService;
import service.RaportService;
import service.StudentService;

import java.io.IOException;


public class MainApp extends Application {


    StudentXMLRepo<String, Student> repo;
    HomeworkXMLRepo<String, Homework> repo2;
    GradeXMLRepo<String, Grade> repo3;
    StudentService<String, Student<String>> service;
    HomeworkService<String, Homework<String>> service2;
    GradeService<String, Grade<String>> service3;
    RaportService<String, Raport<String>>  service4;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ValidationException {
        repo = new StudentXMLRepo<>("Students.xml");
        repo2 = new HomeworkXMLRepo<>("Homeworks.xml");
        repo3 = new GradeXMLRepo<>("Grades.xml");
        service = new StudentService<>(repo);
        service2 = new HomeworkService<>(repo2);
        service3 = new GradeService<>(repo3);
        service4 = new RaportService<>(service, service3,service2);


        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            //root = FXMLLoader.load(getClass().getResource("main.fxml"));
            root = loader.load();
            Controller messageTaskController = loader.getController();
            messageTaskController.setStudentService(service);
            messageTaskController.setHomeworkService(service2);
            messageTaskController.setGradeService(service3);
            messageTaskController.setRaportService(service4);
        }catch (IOException e){
            throw new RuntimeException((e));
        }
        Scene scene = new Scene(root);
        primaryStage.setTitle("Grade Manager v1.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
