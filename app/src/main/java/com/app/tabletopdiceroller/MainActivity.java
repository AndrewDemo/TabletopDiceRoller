package com.app.tabletopdiceroller;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.app.tabletopdiceroller.Objects.Roll;
import com.app.tabletopdiceroller.room.RollRepository;
import java.util.List;

/**
 * This is the main activity for the TableTopDiceRoller application
 */
public class MainActivity extends AppCompatActivity {

    private RollRepository rollRepository;
    private int stopper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        stopper = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Creates the PresetRollFragment so that it can be used on startup
        PresetRollFragment createFragment = PresetRollFragment.getFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, createFragment).commit();

        // Creates and sets the CustomRollFragment to the beginning view
        CustomRollFragment startFragment = CustomRollFragment.getFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, startFragment).commit();

        // Instantiates repository
        rollRepository = new RollRepository(this);
        retrieveRolls();
    }

    /**
     * Navigates between fragments when bottom navigation buttons are pressed
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch(item.getItemId()) {
                case R.id.nav_custom:
                    selectedFragment = CustomRollFragment.getFragment();
                    break;
                case R.id.nav_preset:
                    selectedFragment = PresetRollFragment.getFragment();
                    break;
                case R.id.nav_history:
                    selectedFragment = HistoryFragment.getFragment();
                    break;
                case R.id.nav_settings:
                    selectedFragment = SettingsFragment.getFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    //-----########## AddNewFavoriteFragment METHODS ##########-----

    /**
     * Moves from the custom roll fragment to the create new favorite fragment
     * @param numSides is the number of sides chosen when the favorite button was hit
     * @param numDice is the number of dice chosen when the favorite button was hit
     */
    public void addNewFavorite(String numSides, String numDice) {
        AddNewFavoriteFragment.setSides(numSides);
        AddNewFavoriteFragment.setDice(numDice);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, AddNewFavoriteFragment.getFragment()).commit();
    }

    //-----########## CustomRollFragment METHODS ##########-----

    /**
     * Returns user back to the custom roll page when the back button is hit
     */
    public void cancelNewFavorite() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CustomRollFragment.getFragment()).commit();
    }

    //-----########## HistoryFragment METHODS ##########-----

    /**
     * Calls into the HistoryFragment for the method of the same name
     * @param result is the result of the last roll
     * @param sides is the number of sides on the dice of the last roll
     * @param dice is the number of dice rolled in the last roll
     */
    public void addNewHistory(int result, int sides, int dice) {
        HistoryFragment histFrag = HistoryFragment.getFragment();
        histFrag.addNewHistory(result, sides, dice);
    }

    //-----########## PresetRollFragment METHODS ##########-----

    /**
     * Called when a preset roll is selected from the list of preset rolls.
     * This method brings the user back to the 'CustomRoll' screen with their preset rolls
     * information already filled in.
     * @param numSides is the number of sides on each die in the preset roll
     * @param numDice is the number of dice rolled in the preset roll
     */
    public void presetRollSelection(int numSides, int numDice) {
        CustomRollFragment instance = CustomRollFragment.getFragment();
        instance.fromPresets(numSides, numDice);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CustomRollFragment.getFragment()).commit();
    }

    /**
     * Called when the user presses 'create' when creating a new favorite roll.
     * This method creates a new roll in the 'PresetRoll' Fragment and changes the screen to the 'PresetRoll' screen to show the user their new roll has been added
     * @param sides is the number of sides the roll is using
     * @param dice is the number of dice the roll is using
     * @param rollName is the name of the new favorite roll
     */
    public void confirmNewRoll(int sides, int dice, String rollName) {
        PresetRollFragment instance = PresetRollFragment.getFragment();
        instance.createNewRoll(sides, dice, rollName);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, instance).commit();
    }

    //-----########## DATABASE METHODS ##########-----

    /**
     * Calls to the preset rolls fragment to update it with rolls from the database
     */
    private void retrieveRolls() {
        rollRepository.retrieveRollTask().observe(this, new Observer<List<Roll>>() {
            @Override
            public void onChanged(@Nullable List<Roll> rolls) {
                if (stopper == 0) {
                    PresetRollFragment preRollFrag = PresetRollFragment.getFragment();
                    preRollFrag.retrieveRolls(rolls);
                    stopper++;
                }
            }
        });
    }

    /**
     * Deletes a roll from the database
     * @param roll is the roll being deleted from the database
     */
    public void deleteRoll(Roll roll) {
        rollRepository.deleteRoll(roll);
    }

    /**
     * Inserts a new roll into the repository
     * @param roll is the roll being inserted into to the repository
     */
    public void insertRollToDatabase(Roll roll) {
        rollRepository.insertRollTask(roll);
    }
}
