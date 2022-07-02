package View;


import Domain.Course;
import Domain.GradeEntry;
import Domain.Student;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private  String studentName, courseName;
    private  int studentId, courseId, gradeId, grade;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getGrade() {
        return grade;
    }

    public  void setGrade(int grade) {
        this.grade = grade;
    }


    final static String menu = "1. Add student   " +
            "2. Remove student   "+
            "3. Modify student\n" +
            "4. Add course   " +
            "5. Remove course   " +
            "6. Modify course\n" +
            "7. Add grade   " +
            "8. Modify grade\n"+
            "9. Print students   "+
            "10. Print courses   "+
            "11. Print catalog\n" +
            "0. Exit\n";

    public void printMenu() {
        System.out.println(menu);
    }
    public void printCatalog(List<GradeEntry> catalog){
        System.out.println("Id     Student     Course     Grade");
        for(GradeEntry grade: catalog){
            System.out.println(grade.getId()+"     "+grade.getStudentName()+"     "+grade.getCourseName()+"     "+grade.getGrade());
        }
        System.out.println();
    }
    public void printStudents(List<Student> students){
        System.out.println("Id     Student name");
        for(Student st: students){
            System.out.println(st.getId() + "     " + st.getName());
        }
        System.out.println();
    }
    public void printCourses(List<Course> courses){
        System.out.println("Id     Course name");
        for(Course co: courses){
            System.out.println(co.getId() + "     " + co.getName());
        }
        System.out.println();
    }
    public int readCommand(){
        Scanner console = new Scanner(System.in);
        System.out.println("Command nr >> ");
        return console.nextInt();
    }
    public void setFields(int command) {
        Scanner scanner = new Scanner(System.in);
        switch(command) {
            case 1:
                System.out.println("Enter student name: ");
                this.setStudentName(scanner.nextLine());
                break;
            case 2:
                System.out.println("Enter student id ");
                this.setStudentId(Integer.parseInt(scanner.nextLine()));
                break;
            case 3:
                System.out.println("Enter student id: ");
                this.setStudentId(Integer.parseInt(scanner.nextLine()));
                System.out.println("Enter student name: ");
                this.setStudentName(scanner.nextLine());
                break;
            case 4:
                System.out.println("Enter course name: ");
                this.setCourseName(scanner.nextLine());
                break;
            case 5:
                System.out.println("Enter course id ");
                this.setCourseId(Integer.parseInt(scanner.nextLine()));
                break;
            case 6:
                System.out.println("Enter course id: ");
                this.setCourseId(Integer.parseInt(scanner.nextLine()));
                System.out.println("Enter course name: ");
                this.setCourseName(scanner.nextLine());
                break;
            case 7:
                System.out.println("Enter student id: ");
                this.setStudentId(Integer.parseInt(scanner.nextLine()));
                System.out.println("Enter course id: ");
                this.setCourseId(Integer.parseInt(scanner.nextLine()));
                System.out.println("Enter grade: ");
                this.setGrade(Integer.parseInt(scanner.nextLine()));
                break;
            case 8:
                System.out.println("Enter grade id: ");
                this.setGradeId(Integer.parseInt(scanner.nextLine()));
                System.out.println("Enter grade: ");
                this.setGrade(Integer.parseInt(scanner.nextLine()));
                break;
        }
    }

}
