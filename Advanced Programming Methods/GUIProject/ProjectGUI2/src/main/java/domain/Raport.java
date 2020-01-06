package domain;

public class Raport<ID> extends Entity<ID> {

    private  Student<ID> st;
    private Homework<ID> hm;
    private double average;

    public Raport(Student<ID> st, double avg) {
        super(st.getId());
        this.st=st;
        this.average=avg;
    }

    public Raport(Homework<ID> hm, double avg){
        super(hm.getId());
        this.hm= hm;
        this.average = avg;
    }


    public Student<ID> getSt() {
        return st;
    }

    public Homework<ID> getHm(){
        return hm;
    }

    public double getAverage() {
        return average;
    }

    public String getFirstName() {
        return st.getFirstName();
    }

    public String getLastName() {
        return st.getLastName();
    }

    public String getGroup() {
        return st.getGroup();
    }

    public int getStartWeek(){
        return hm.getStartWeek();
    }

    public int getDeadlineWeek(){
        return hm.getDeadlineWeek();
    }

    public String getDescription(){
        return hm.getDescription();
    }
}
