package JRMP;

import View.ConsoleMenu;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class JRMPClient {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        JRMPInterface look_up = (JRMPInterface) Naming.lookup("//localhost/Catalog");
        boolean isRunning = true;
        ConsoleMenu consoleMenu = new ConsoleMenu();
        while (isRunning) {
            consoleMenu.printMenu();
            int command = consoleMenu.readCommand();
            consoleMenu.setFields(command);
            switch (command) {
                case 0:
                    System.out.println("Bye");
                    isRunning = false;
                    break;
                case 1:
                    look_up.addStudent(consoleMenu.getStudentName());
                    System.out.println("Student Added");
                    break;
                case 2:
                    look_up.deleteStudent(consoleMenu.getStudentId());
                    System.out.println("Student deleted");
                    break;
                case 3:
                    look_up.updateStudent(consoleMenu.getStudentId(), consoleMenu.getStudentName());
                    System.out.println("Student updated");
                    break;
                case 4:
                    look_up.addCourse(consoleMenu.getCourseName());
                    System.out.println("Course Added");
                    break;
                case 5:
                    look_up.deleteCourse(consoleMenu.getCourseId());
                    System.out.println("Course deleted");
                    break;
                case 6:
                    look_up.updateCourse(consoleMenu.getCourseId(), consoleMenu.getCourseName());
                    System.out.println("Course updated");
                    break;
                case 7:
                    look_up.addGrade(consoleMenu.getStudentId(), consoleMenu.getCourseId(), consoleMenu.getGrade());
                    System.out.println("Grade added");
                    break;
                case 8:
                    look_up.updateGrade(consoleMenu.getGradeId(), consoleMenu.getGrade());
                    System.out.println("Grade updated");
                    break;
                case 9:
                    consoleMenu.printStudents(look_up.getStudents());
                    break;
                case 10:
                    consoleMenu.printCourses(look_up.getCourses());
                    break;
                case 11:
                    consoleMenu.printCatalog(look_up.getCatalog());
                    break;
            }

        }
    }
}
