package com.erlandumah.example.dicesample;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DiceRollTotalID
implements Serializable {
    private int nDices;
    private int nSides;

    public DiceRollTotalID() {
        this.nSides = 0;
        this.nDices = 0;
    }

    public DiceRollTotalID(int nDices, int nSides) {
        this.nDices = nDices;
        this.nSides = nSides;
    }

    public int getnDices() {
        return nDices;
    }

    public int getnSides() {
        return nSides;
    }
}
