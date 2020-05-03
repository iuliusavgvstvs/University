package dto;

import java.io.Serializable;

public class ProbaDTO implements Serializable {
    private int id;
    private int CopilID;
    private int distanta;

    public ProbaDTO(int id, int CopilID, int distanta) {
        this.id = id;
        this.CopilID = CopilID;
        this.distanta = distanta;
    }

    public int getId() {
        return id;
    }

    public int getCopilID() {
        return CopilID;
    }

    public int getDistanta() {
        return distanta;
    }
}
