package utils.events;


import domain.Grade;

public class GradeChangeEvent implements  Event {
    private ChangeEventType type;
    private Grade st, oldst;

    public GradeChangeEvent(ChangeEventType type, Grade st){
        this.type=type;
        this.st = st;
    }

    public GradeChangeEvent(ChangeEventType type, Grade st, Grade oldst){
        this.type=type;
        this.st = st;
        this.oldst=oldst;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Grade getSt() {
        return st;
    }

    public Grade getOldst() {
        return oldst;
    }
}
