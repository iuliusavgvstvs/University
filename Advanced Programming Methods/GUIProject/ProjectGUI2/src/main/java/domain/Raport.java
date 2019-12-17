package domain;

public class Raport<ID> extends Entity<ID> {

    private  Student<ID> st;
    private double average;

    public Raport(Student<ID> st, double avg) {
        super(st.getId());
        this.st=st;
        this.average=avg;
    }

    public Student<ID> getSt() {
        return st;
    }

    public double getAverage() {
        return average;
    }
}
