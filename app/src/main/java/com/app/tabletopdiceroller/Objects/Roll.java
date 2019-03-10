package com.app.tabletopdiceroller.Objects;

/**
 * This Object is representative of a dice roll.
 * A dice roll can be composed of several dice but the dice must have the same number of sides
 */
public class Roll {

    private int numberOfDice;
    private int numberOfSides;
    private String displayString;
    private String rollName;

    /**
     * This is the public constructor for the Roll object
     * @param numberOfDice is the number of dice being rolled
     * @param numberOfSides is the number of sides the dice have
     */
    public Roll(int numberOfDice, int numberOfSides, String rollName) {
        this.numberOfDice = numberOfDice;
        this.numberOfSides = numberOfSides;
        this.rollName = rollName;
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
    public String getRollName() {
        return rollName;
    }
}
