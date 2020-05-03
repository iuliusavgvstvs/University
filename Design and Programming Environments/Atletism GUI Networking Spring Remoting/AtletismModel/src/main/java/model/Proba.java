package model;

import java.io.Serializable;
import java.util.Objects;

public class Proba extends Entity implements Comparable<Proba>, Serializable {

    private int CopilID;
    private int distanta;

    public Proba(int id, int CopilID, int distanta) {
        super(id);
        this.CopilID = CopilID;
        this.distanta = distanta;
    }

    public int getCopilID() {
        return CopilID;
    }

    public void setCopilID(int copilID) {
        CopilID = copilID;
    }

    public int getDistanta() {
        return distanta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proba proba = (Proba) o;
        return this.getId() == proba.getId() &&
                CopilID == proba.CopilID &&
                distanta == proba.distanta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(CopilID, distanta);
    }

    @Override
    public String toString() {
        return this.getId() +
                " CopilID= " + CopilID +
                ", distanta= " + distanta;
    }

    @Override
    public int compareTo(Proba o) {
        return 1;
    }
}
