package dto;

import java.io.Serializable;


public class TableEntityDTO implements Serializable {
    private int id;
    private CopilDTO copil;
    private ProbaDTO p1, p2;

    public TableEntityDTO(int id, CopilDTO c, ProbaDTO p1, ProbaDTO p2) {
        this.id = id;
        this.copil = c;
        this.p1 = p1;
        this.p2 = p2;
    }

    public int getId() {
        return id;
    }

    public CopilDTO getCopil() {
        return copil;
    }

    public ProbaDTO getP1() {
        return p1;
    }

    public ProbaDTO getP2() {
        return p2;
    }
}
