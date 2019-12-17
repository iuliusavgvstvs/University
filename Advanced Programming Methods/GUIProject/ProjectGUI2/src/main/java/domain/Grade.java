package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Grade<ID> extends Entity<ID> {

    private ID studentId, homeworkID;
    private int grade, week;
    private String prof, feedback, dateForm="yyyy-MM-dd HH:mm";
    private String date;
    private DateTimeFormatter form = DateTimeFormatter.ofPattern(dateForm);

    public Grade(ID id, ID studentId, ID homeworkId, int grade, String prof, String feedback) {
        super(id);
        this.studentId = studentId;
        this.homeworkID=homeworkId;
        this.grade=grade;
        this.prof=prof;
        this.feedback=feedback;
        this.date=form.format(LocalDateTime.now());
        this.week=StudyYear.getSemesterWeek();
    }
    public Grade(ID id, ID studentId, ID homeworkId, int grade, String date, String prof, String feedback) {
        super(id);
        this.studentId = studentId;
        this.homeworkID=homeworkId;
        this.grade=grade;
        this.prof=prof;
        this.feedback=feedback;
        this.date=date;
        this.week=StudyYear.getSemesterWeek();
    }

    public ID getStudentId() {
        return studentId;
    }

    public String getDate(){
        return this.date;
    }

    public void setStudentId(ID studentId) {
        this.studentId = studentId;
    }

    public ID getHomeworkID() {
        return homeworkID;
    }

    public void setHomeworkID(ID homeworkID) {
        this.homeworkID = homeworkID;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDetails(){
        return "ID: " + this.getId() + "  Student id: "+ this.studentId+"  Homework id: "+this.homeworkID+"  Grade: "+this.grade+"  Date: "+this.date+"  Teacher: "+this.prof + "  Feedback: "+this.feedback;
    }

    public int getWeek(){
        return this.week;
    }


}
