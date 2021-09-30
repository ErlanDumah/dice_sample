package com.erlandumah.example.dicesample;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class DiceRollTotal {
    @EmbeddedId
    private DiceRollTotalID id;

    private int totalSimulations;
    private long totalRolls;

    @ElementCollection
    //@CollectionTable(name = "diceroll_individualrolls_mapping",
    //        joinColumns = {@JoinColumn(name = "diceroll_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "result")
    @Column(name = "occurences")
    private Map<Long, Integer> individualRolls;

    public DiceRollTotal() {
        this.id = new DiceRollTotalID();
        this.totalRolls = 0;
        this.totalSimulations = 0;
        this.individualRolls = new HashMap<Long, Integer>();
    }

    public DiceRollTotal(DiceRollTotalID id, long totalRolls, int totalSimulations, HashMap<Long, Integer> individualRolls) {
        this.id = id;
        this.totalRolls = totalRolls;
        this.totalSimulations = totalSimulations;
        this.individualRolls = individualRolls;
    }

    public DiceRollTotalID getId() {
        return id;
    }

    public void setId(DiceRollTotalID id) {
        this.id = id;
    }

    public long getTotalRolls() {
        return totalRolls;
    }

    public void setTotalRolls(long total) {
        this.totalRolls = total;
    }

    public Map<Long, Integer> getIndividualRolls() {
        return individualRolls;
    }

    public void setIndividualRolls(Map<Long, Integer> individualRolls) {
        this.individualRolls = individualRolls;
    }

    public int getTotalSimulations() {
        return totalSimulations;
    }

    public void setTotalSimulations(int totalSimulations) {
        this.totalSimulations = totalSimulations;
    }
}
