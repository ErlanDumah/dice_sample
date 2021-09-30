package com.erlandumah.example.dicesample;

public class NumberOfRollsBelowMinimumException
extends Exception {
    public NumberOfRollsBelowMinimumException(int minimum) {
        super("Number of rolls for a diceroll need to be at least " + minimum + ".");
    }
}
