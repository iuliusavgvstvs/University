package IIOP;

import View.ConsoleMenu;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;
import java.util.Properties;

public class IIOPClient {

    public static void main(String[] args) throws RemoteException, NamingException {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
        props.setProperty("java.naming.provider.url", "iiop://localhost/Catalog");
        Context ctx = new InitialContext(props);
        Object ref = ctx.lookup("Catalog");
        IIOPInterface look_up = (IIOPInterface) PortableRemoteObject.narrow(ref, IIOPInterface.class);
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

