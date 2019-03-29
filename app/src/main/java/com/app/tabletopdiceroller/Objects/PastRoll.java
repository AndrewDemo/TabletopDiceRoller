package com.app.tabletopdiceroller.Objects;

/**
 * This class represents a previously rolled roll
 * This object is for use in the history list
 */
public class PastRoll {

    private int numberOfDice;
    private int numberOfSides;
    private String displayString;
    private String result;

    /**
     * This is the public constructor for the Roll object
     * @param numberOfDice is the number of dice being rolled
     * @param numberOfSides is the number of sides the dice have
     * @param result is the result of the dice roll
     */
    public PastRoll(int numberOfSides, int numberOfDice, String result) {
        this.numberOfDice = numberOfDice;
        this.numberOfSides = numberOfSides;
        this.result = result;
        displayString = "d" + numberOfSides + "x" + numberOfDice;
    }

    /**
     * Returns the number of dice in this roll
     * @return numberOfDice class variable
     */
    public int getNumberOfDice() {
        return numberOfDice;
    }

    /**
     * Returns the number of sides the dice have
     * @return numberOfSides class variable
     */
    public int getNumberOfSides() {
        return numberOfSides;
    }

    /**
     * Returns the displayString for use in the recyclerView
     * @return displayString class variable
     */
    public String getDisplayString() {
        return displayString;
    }

    /**
     * Returns the name assigned to this roll
     * @return rollName class variable
     */
    public String getResult() {
        return result;
    }
}

