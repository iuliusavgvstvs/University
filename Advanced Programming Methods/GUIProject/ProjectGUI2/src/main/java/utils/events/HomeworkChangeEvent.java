package utils.events;

import domain.Homework;


public class HomeworkChangeEvent implements  Event {
    private ChangeEventType type;
    private Homework st, oldst;

    public HomeworkChangeEvent(ChangeEventType type, Homework st){
        this.type=type;
        this.st = st;
    }

    public HomeworkChangeEvent(ChangeEventType type, Homework st, Homework oldst){
        this.type=type;
        this.st = st;
        this.oldst=oldst;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Homework getSt() {
        return st;
    }

    public Homework getOldst() {
        return oldst;
    }
}
