package model;

import java.io.Serializable;

public class TableEntity implements Serializable {
    private int id;
    private Copil copil;
    private Proba p1, p2;

    public TableEntity(int id, Copil c, Proba p1, Proba p2) {
        this.id = id;
        this.copil = c;
        this.p1 = p1;
        this.p2 = p2;
    }


    public TableEntity(int id, Copil c, Proba p1) {
        this.id = id;
        this.copil = c;
        this.p1 = p1;
    }

    public int getId() {
        return id;
    }

    public String getCopilFname() {
        return copil.getFirstName();
    }

    public String getCopilLname() {
        return copil.getLastName();
    }

    public int getCopilAge() {
        return copil.getAge();
    }

    public Proba getP1() {
        return p1;
    }

    public Proba getP2() {
        return p2;
    }

    public String getDistances() {
        if (p2 == null)
            return p1.getDistanta() + "m";
        else
            return p1.getDistanta() + "m, " + p2.getDistanta() + "m";
    }

    public Copil getCopil() {
        return copil;
    }
}
