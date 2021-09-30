package com.erlandumah.example.dicesample;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

public class DiceRollTotalPercentage {
    private int nDice;
    private int nSides;
    private int totalSimulations;
    private long totalRolls;
    private Map<Long, Double> individualRollPercentages;

    private DiceRollTotalPercentage(int nDice, int nSides, int totalSimulations, long totalRolls, Map<Long, Double> individualRollPercentages) {
        this.nDice = nDice;
        this.nSides = nSides;
        this.totalSimulations = 0;
        this.totalRolls = 0;
        this.individualRollPercentages = individualRollPercentages;
    }

    public static DiceRollTotalPercentage createFrom(DiceRollTotal diceRollTotal) {
        double totalRollsDouble = (double)diceRollTotal.getTotalRolls();
        Map<Long, Double> individualRollPercentages = new HashMap<Long, Double>();
        for(Map.Entry<Long, Integer> rollEntry: diceRollTotal.getIndividualRolls().entrySet()) {
            // we're cheekily assuming here that we programmed everything brilliantly enough that a total rolls entry
            // will never be 0.
            // We're multiplying by 100 to give out a range of [0.0, 100.0] as opposed to [0.0, 1.0]
            double percentage = 100.0d * ((double)rollEntry.getValue() / totalRollsDouble);
            individualRollPercentages.put(rollEntry.getKey(), percentage);
        }
        return new DiceRollTotalPercentage(diceRollTotal.getId().getnDices(), diceRollTotal.getId().getnSides(), diceRollTotal.getTotalSimulations(), diceRollTotal.getTotalRolls(), individualRollPercentages);
    }

    public int getnDice() {
        return nDice;
    }

    public int getnSides() {
        return nSides;
    }

    public int getTotalSimulations() {
        return totalSimulations;
    }

    public long getTotalRolls() {
        return totalRolls;
    }

    public Map<Long, Double> getIndividualRollPercentages() {
        return individualRollPercentages;
    }
}
