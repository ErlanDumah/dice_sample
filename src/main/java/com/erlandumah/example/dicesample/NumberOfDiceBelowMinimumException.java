package com.erlandumah.example.dicesample;

public class NumberOfDiceBelowMinimumException
        extends Exception {
    public NumberOfDiceBelowMinimumException(int minimum) {
        super("Number of dice for a diceroll need to be at least " + minimum + ".");
    }
}
