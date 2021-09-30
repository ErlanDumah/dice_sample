package com.erlandumah.example.dicesample;

public class NumberOfSidesBelowMinimumException
        extends Exception {
    public NumberOfSidesBelowMinimumException(int minimum) {
        super("Number of sides for a diceroll need to be at least " + minimum + ".");
    }
}
