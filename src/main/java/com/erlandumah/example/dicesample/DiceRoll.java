package com.erlandumah.example.dicesample;

import java.util.HashMap;
import java.util.Random;

public class DiceRoll {
    public static final int MinSides = 4;
    public static final int MinRolls = 1;
    public static final int MinDice = 1;

    // Number of sides to a die, stored as a double to ease our computation
    private final double nSides;
    // Number of rolls to be made
    private final int nRolls;
    // Number of dice thrown
    private final int nDice;

    // Constructor is private to prevent its usage from outside
    // We want to make it as hard as possible to create an object with an error in it such as nRolls=-5
    // In order to do that a user of this code needs to use DiceRoll.create(...) and catch possible exceptions from it
    private DiceRoll(int nSides, int nRolls, int nDice) {
        this.nSides = nSides;
        this.nRolls = nRolls;
        this.nDice = nDice;
    }

    public static DiceRoll create(int nSides, int nRolls, int nDice)
            throws NumberOfSidesBelowMinimumException, NumberOfRollsBelowMinimumException, NumberOfDiceBelowMinimumException {
        if(nSides < MinSides) {
            throw new NumberOfSidesBelowMinimumException(MinSides);
        }
        if(nRolls < MinRolls) {
            throw new NumberOfRollsBelowMinimumException(MinRolls);
        }
        if(nDice < MinDice) {
            throw new NumberOfDiceBelowMinimumException(MinDice);
        }
        return new DiceRoll(nSides, nRolls, nDice);
    }

    public HashMap<Long, Integer> execute() {
        Random random = new Random();
        HashMap<Long, Integer> dicerollValueToNumberOccured = new HashMap<Long, Integer>();
        for(int i = 0; i < nRolls; i++) {
            long diceroll = 0;

            for(int j = 0; j < nDice; j++) {
                //               [0.0, 1.0)           * [1, ]
                int roll = (int) (random.nextDouble() * this.nSides);
                // roll is in [0, nSides - 1]
                roll = roll + 1;
                // roll is in [1, nSides] evenly distributed
                diceroll = diceroll + roll;
            }

            Integer previousValue = dicerollValueToNumberOccured.get(diceroll);
            if(previousValue == null) {
                dicerollValueToNumberOccured.put(diceroll, 1);
            } else {
                dicerollValueToNumberOccured.put(diceroll, previousValue + 1);
            }
        }
        return dicerollValueToNumberOccured;
    }
}
