package domain;

public class Proba extends Entity {

    private int CopilID;
    private int distanta;
    public Proba(int id, int CopilID, int distanta) {
        super(id);
        this.CopilID=CopilID;
        this.distanta = distanta;
    }

    public int getCopilID() {
        return CopilID;
    }

    public int getDistanta() {
        return distanta;
    }
}
