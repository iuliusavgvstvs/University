package Domain;


import java.io.Serializable;

public class GradeEntry implements Serializable {

    private static final long serialVersionUID = 1190476516911661470L;
    private int id, grade;
    private String studentName, courseName;

    public GradeEntry(int id, int grade, String studentName, String courseName) {
        this.id = id;
        this.grade = grade;
        this.studentName = studentName;
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

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
}
