package com.app.tabletopdiceroller.Objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * This Object is representative of a dice roll.
 * A dice roll can be composed of several dice but the dice must have the same number of sides
 */
@Entity(tableName = "favorites")
public class Roll {

    // Creates a primary key 'roll_ID'
    @PrimaryKey(autoGenerate = true)
    private int roll_ID;

    @ColumnInfo(name = "number of dice")
    private int numberOfDice;

    @ColumnInfo(name = "number of sides")
    private int numberOfSides;

    private String displayString;

    @ColumnInfo(name = "name")
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

    public int getRoll_ID() {
        return roll_ID;
    }

    public void setRoll_ID(int roll_ID) {
        this.roll_ID = roll_ID;
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
