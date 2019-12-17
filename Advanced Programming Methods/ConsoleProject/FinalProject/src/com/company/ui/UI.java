package com.company.ui;

import com.company.entity.*;
import com.company.exceptions.ValidationException;
import com.company.service.GradeService;
import com.company.service.HomeworkService;
import com.company.service.StudentService;
import org.apache.commons.collections4.IterableUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class UI<ID> {

    private StudentService<ID, Student<ID>> studentService;
    private HomeworkService<ID, Homework<ID>> homeworkService;
    private GradeService<ID, Grade<ID>> gradeService;
    private Scanner scanner  = new Scanner(System.in);

    public UI(){
        this.studentService = new StudentService<>("Students.xml");
        this.homeworkService = new HomeworkService<>("Homeworks.xml");
        this.gradeService = new GradeService<>("Grades.xml");
    }

    private void printMenu() {
        System.out.println("\n1.Students menu\n2.Homeworks menu\n3.Filters menu\n4.Grade a student\n" +
                "5.Show grades\n6.Exit.");
    }
    private void printStudentMenu(){
        System.out.println("\n1.Add a new student\n2.Find a student\n3.Delete a student\n" +
                "4.Update a student\n5.Show all students\n6.Motivate a student\n7.Back.");
    }

    private void printHomeworkMenu(){
        System.out.println("\n1.Add a new homework\n2.Find a homework\n3.Delete a homework\n" +
                "4.Update a homework\n5.Show all homeworks\n6.Back.");
    }

    private void printFilterMenu(){
        System.out.println("\n1.Students of a group.\n2.The students with a given homework\n" +
                "3.The students with a given homework and a given teacher\n4.All the grades with a given homework in a given week.\n5.Back");
    }

    private <T extends Entity> void printIterable(Iterable<T> it) {
        for(T x: it)
            System.out.println(x.getDetails());
    }

    private static <T> Stream<T> getStreamFromIterable(Iterable<T> iterable)
    {

        // Convert the Iterable to Spliterator
        Spliterator<T>
                spliterator = iterable.spliterator();

        // Get a Sequential Stream from spliterator
        return StreamSupport.stream(spliterator, false);
    }

    public void run(){
        while(true){
            printMenu();
            System.out.println("Command nr: ");
            int command = scanner.nextInt();
            try{
                if(command==1) {
                    while (command==1) {
                        printStudentMenu();
                        System.out.println("Command nr: ");
                        int stcommand = scanner.nextInt();
                        try {
                            if (stcommand == 1){
                                addStudent();
                                System.out.println("Student added.");
                            }
                            else if(stcommand == 2){
                                findStudent();
                            }
                            else if(stcommand == 3){
                                deleteStudent();
                                System.out.println("Student deleted.");
                            }
                            else if(stcommand == 4){
                                updateStudent();
                                System.out.println("Student updated.");
                            }
                            else if(stcommand == 5){
                                printIterable(studentService.getAll());
                            }
                            else if(stcommand==6){
                                motivateWeek();
                                System.out.println("Student motivated.");
                            }
                            else if(stcommand == 7){
                                command=0;
                            }
                            else
                                System.out.println("Invalid command number.");
                        }catch(ValidationException | IllegalArgumentException err){
                            System.out.println(err.getMessage());
                        }
                    }
                }
                else if(command==2){
                    while (command==2) {
                        printHomeworkMenu();
                        System.out.println("Command nr: ");
                        int hwcommand = scanner.nextInt();
                        try {
                            if (hwcommand == 1){
                                addHomework();
                                System.out.println("Homework added.");
                            }
                            else if(hwcommand == 2){
                                findHomework();
                            }
                            else if(hwcommand == 3){
                                deleteHomework();
                                System.out.println("Homework deleted.");
                            }
                            else if(hwcommand == 4){
                                updateHomework();
                                System.out.println("Homework updated.");
                            }
                            else if(hwcommand == 5){
                                printIterable(homeworkService.getAll());
                            }
                            else if(hwcommand == 6){
                                command=0;
                            }
                            else
                                System.out.println("Invalid command number.");
                        }catch(ValidationException | IllegalArgumentException err){
                            System.out.println(err.getMessage());
                        }
                    }
                }
                else if(command==3){
                    while (command==3) {
                        printFilterMenu();
                        System.out.println("Command nr: ");
                        int fcommand = scanner.nextInt();
                            if (fcommand == 1){
                                filterGroup();
                            }
                            else if(fcommand == 2){
                                filterHomework();
                            }
                            else if(fcommand == 3){
                                filterHomeworkTeacher();
                            }
                            else if(fcommand == 4){
                                filterHomeworkWeek();
                            }
                            else if(fcommand == 5){
                                command=0;
                            }
                            else
                                System.out.println("Invalid command number.");
                    }

                }
                else if(command==4){
                    addGrade();
                    System.out.println("Student graded.");
                }
                else if(command==5){
                    printIterable(gradeService.getAll());
                }

                else if(command==6){
                    System.out.println("Program ended.");
                    return;
                }
                else{
                    System.out.println("Invalid command nr.");
                }
            }catch(IllegalArgumentException | ValidationException | ParserConfigurationException err){
                System.out.println(err.getMessage());
            }
         }
    }

    private void filterHomeworkWeek() {
        if(IterableUtils.size(gradeService.getAll())==0) {
            throw new IllegalArgumentException("There's no Grades.");
        }
        printIterable(gradeService.getAll());
        ID homeworkID;
        String week;
        System.out.println("Homework id: ");
        homeworkID = (ID) scanner.next();
        System.out.println("Week:");
        week = scanner.next();
        if (!week.matches("[0-9]+")) {
            throw new IllegalArgumentException("Invalid week.");
        }
        int iweek = Integer.parseInt(week); // converting String week to integer iweek
        Stream<Grade<ID>> result = gradeService.filterHomeworkWeek(homeworkID,iweek); // use the filter function defined in service
        result.forEach(x-> System.out.println(studentService.find(x.getStudentId()).getDetails()));// print the results
    }

    private void filterHomeworkTeacher() {
        if(IterableUtils.size(gradeService.getAll())==0) {
            throw new IllegalArgumentException("There's no Grades.");
        }
        printIterable(gradeService.getAll());
        ID homeworkID;
        String teacher;
        System.out.println("Homework id: ");
        homeworkID = (ID) scanner.next();
        System.out.println("Teacher:");
        teacher = scanner.next();
        Stream<Grade<ID>> result = gradeService.filterHomeworkTeacher(homeworkID, teacher); // use the filter function defined in service
        result.forEach(x-> System.out.println(studentService.find(x.getStudentId()).getDetails()));// print the results
    }

    private void filterHomework() {
        if(IterableUtils.size(gradeService.getAll())==0) {
            throw new IllegalArgumentException("There's no Grades.");
        }
        printIterable(gradeService.getAll());
        ID homeworkID;
        System.out.println("Homework id: ");
        homeworkID = (ID) scanner.next();
        Stream<Grade<ID>> result = gradeService.filterHomework(homeworkID); // use the filter function defined in service
        result.forEach(x-> System.out.println(studentService.find(x.getStudentId()).getDetails()));// print the results
    }

    private void filterGroup() {
        if(IterableUtils.size(studentService.getAll())==0) {  // if the students list is empty
            throw new IllegalArgumentException("There's no students."); // show an error message.
        }
        String group; // else
        System.out.println("Group: "); // ask user for input
        group = scanner.next();// reat the group
        if (!group.matches("[0-9]+")) {  // validate it
            throw new IllegalArgumentException("Invalid group.");
        }
        else { // if it's valid
            Stream<Student<ID>> result = studentService.filterGroup(group); // use the filter function defined in service
            result.forEach(x-> System.out.println(x.getDetails()));// print the results
        }
    }


    private void addGrade() throws ValidationException, ParserConfigurationException {
        if(IterableUtils.size(studentService.getAll())==0) {
            throw new IllegalArgumentException("There's no students.");
        }
        if(IterableUtils.size(homeworkService.getAll())==0) {
            throw new IllegalArgumentException("There's no homeworks.");
        }
        else {
            ID id, stid, hid;
            printIterable(studentService.getAll());
            System.out.println("Student id: ");
            stid = (ID) scanner.next();
            if (studentService.find(stid) == null)
                throw new IllegalArgumentException("The student with the id " + stid + " doesn't exist.");
            else {
                printIterable(homeworkService.getAll());
                System.out.println("Homework id: ");
                hid = (ID) scanner.next();
                if (homeworkService.find(hid) == null)
                    throw new IllegalArgumentException("The homwork with the id " + hid + " doesn't exist.");
                else {
                    String grade, prof, feedback;
                    Student<ID> stud= studentService.find(stid);
                    Homework<ID> h = homeworkService.find(hid);
                    int mgrade= Homework.getMaxGrade(StudyYear.getMotivationsBetween(h.getStartWeek(),h.getDeadlineWeek(),stud.getMotivations()));
                    System.out.println("The maximum grade for the homework is: " +mgrade);
                    System.out.println("Grade: ");
                    grade = scanner.next();
                    if (!grade.matches("[0-9]+")) {
                        throw new IllegalArgumentException("Invalid grade.");
                    } else {
                        int igrade = Integer.parseInt(grade);
                        if (igrade < 1 || igrade > mgrade)
                            throw new IllegalArgumentException("Incorrect grade.");
                        else {
                            System.out.println("Teacher: ");
                            prof = scanner.next();
                            System.out.println("Feedback: ");
                            feedback = scanner.next();
                            id = (ID) String.format("%s_%s", stid, hid);
                            gradeService.add(new Grade<ID>(id, stid, hid, Integer.parseInt(grade), prof, feedback));
                            String name=stud.getFirstName()+"_"+stud.getLastName()+".xml";
                            gradeService.writeJson(new Grade<ID>(id, stid, hid, Integer.parseInt(grade), prof, feedback),name);
                        }
                    }
                }
            }
        }
    }

    private void updateHomework() throws ValidationException {
        ID id;
        String descriere, deadlineWeek;
        System.out.println("ID: ");
        id = (ID)scanner.next();
        System.out.println("DeadlineWeek: ");
        deadlineWeek=scanner.next();
        if(!deadlineWeek.matches("[0-9]+")) {
            throw new ValidationException("Invalid week.");
        }
        else {
            System.out.println("Descriere: ");
            descriere = scanner.next();
            homeworkService.update(new Homework<ID>(id, Integer.parseInt(deadlineWeek), descriere));
        }
    }

    private void deleteHomework() {
        ID id;
        System.out.println("ID: ");
        id = (ID)scanner.next();
        homeworkService.delete(id);
    }

    private void findHomework() {
        ID id;
        System.out.println("ID: ");
        id = (ID)scanner.next();
        Homework<String> hw= (Homework<String>) homeworkService.find(id);
        if(hw!=null){
            System.out.println(hw.getDetails());
        }
        else {
            System.out.println("There isn't a homework with the id: "+id);
        }
    }

    private void addHomework() throws ValidationException {
        ID id;
        String descriere, deadlineWeek;
        System.out.println("ID: ");
        id = (ID)scanner.next();
        System.out.println("DeadlineWeek: ");
        deadlineWeek=scanner.next();
        if(!deadlineWeek.matches("[0-9]+")) {
            throw new ValidationException("Invalid week.");
        }
        else {
            System.out.println("Descriere: ");
            descriere = scanner.next();
            homeworkService.add(new Homework<ID>(id, Integer.parseInt(deadlineWeek), descriere));
        }
    }

    private void motivateWeek() throws ValidationException {
        ID id;
        String week;
        System.out.println("ID student: ");
        id = (ID)scanner.next();
        System.out.println("Week: ");
        week = scanner.next();
        if(!week.matches("[0-9]+")) {
            throw new ValidationException("Invalid week.");
        }
        else
        studentService.motivateWeek(Integer.parseInt(week), id);
    }


    private void updateStudent() throws ValidationException {
        ID id;
        String lastName, firstName, group, email;
        System.out.println("ID: ");
        id = (ID)scanner.next();
        System.out.println("Last name: ");
        lastName = scanner.next();
        System.out.println("First name: ");
        firstName = scanner.next();
        System.out.println("Group: ");
        group = scanner.next();
        System.out.println("Email: ");
        email = scanner.next();
        Student<ID> st = new Student<>(id, firstName, lastName, group, email);
        studentService.update(st);
    }

    private void deleteStudent() {
        ID id;
        System.out.println("ID: ");
        id = (ID)scanner.next();
        studentService.delete(id);
    }

    private void findStudent() {
        ID id;
        System.out.println("ID: ");
        id = (ID)scanner.next();
        Student<String> st= (Student<String>) studentService.find(id);
        if(st!=null){
            System.out.println(st.getDetails());
        }
        else
            System.out.println("There isn't a student with the id: "+id);
    }

    private void addStudent() throws ValidationException {
        ID id;
        String lastName, firstName, group, email;
        System.out.println("ID: ");
        id = (ID)scanner.next();
        System.out.println("First name: ");
        lastName = scanner.next();
        System.out.println("Last name: ");
        firstName = scanner.next();
        System.out.println("Group: ");
        group = scanner.next();
        System.out.println("Email: ");
        email = scanner.next();
        Student<ID> st = new Student<>(id, firstName, lastName, group, email);
        studentService.add(st);
    }

}
