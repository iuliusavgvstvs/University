package com.company.entity;

public class Homework<ID> extends Entity<ID> {

    private static int startWeek, deadlineWeek;
    private String description;

    public Homework(ID id, int deadlineWeek, String description) {
        super(id);
        this.startWeek = StudyYear.getSemesterWeek();
        this.deadlineWeek= deadlineWeek;
        this.description= description;
    }
    public Homework(ID id, int startWeek, int deadlineWeek, String description) {
        super(id);
        this.startWeek = StudyYear.getSemesterWeek();
        this.startWeek = startWeek;
        this.deadlineWeek= deadlineWeek;
        this.description= description;
    }

    public static int getMaxGrade(int motivations){
        int week = StudyYear.getSemesterWeek();
        int delay= week - deadlineWeek - motivations;
        if(delay>2)
            return 1;
        if(delay<=0)
            return 10;
        return 10-delay;
    }


    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getDeadlineWeek() {
        return deadlineWeek;
    }

    public void setDeadlineWeek(int deadlineWeek) {
        this.deadlineWeek = deadlineWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDetails() {
        return "Id: " + this.getId()+ "  Start week: "+this.getStartWeek()+"  Deadline week: "+this.getDeadlineWeek()+ "  Description: "+this.getDescription();
    }
}
