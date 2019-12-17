package utils.events;

import domain.Student;

public class StudentChangeEvent implements  Event {
    private ChangeEventType type;
    private Student st, oldst;

    public StudentChangeEvent(ChangeEventType type, Student st){
        this.type=type;
        this.st = st;
    }

    public StudentChangeEvent(ChangeEventType type, Student st, Student oldst){
        this.type=type;
        this.st = st;
        this.oldst=oldst;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Student getSt() {
        return st;
    }

    public Student getOldst() {
        return oldst;
    }
}
