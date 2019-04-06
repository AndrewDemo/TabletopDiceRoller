package com.app.tabletopdiceroller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

/**
 * This fragment is responsible for managing user operations in the Custom Roll screen
 * This class will simulate dice rolls, allow the user to add new favorite rolls, and
 * allow the user to import information from the Preset Roll Fragment
 */
public class CustomRollFragment extends Fragment implements View.OnClickListener{

    // Instance of this fragment defaults to null for singleton pattern
    private static CustomRollFragment fragmentInstance = null;

    // Random used for generating the random dice results
    private Random rand = new Random();

    // Responsible for tracking sides and dice values
    private int sides = -1;
    private int dice = -1;
    private static int s = -1;
    private static int d = -1;

    // Creates class variables to represent the number of dice, number of sides, and result text fields
    private EditText numSidesEditText;
    private EditText numDiceEditText;
    private TextView resultText;

    // Variable keeping track of last rolls result for the history fragment
    int lastResult;

    // Responsible for tracking the values passed in from the preset rolls fragment
    private String presetNumSides;
    private String presetNumDice;


    /**
     * Creates a new instance if the current instance is null, otherwise returns the instance
     * @return the CustomRollFragment instance
     */
    public static CustomRollFragment getFragment() {
        if (fragmentInstance == null) {
            fragmentInstance = new CustomRollFragment();
        }
        return fragmentInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_roll, container, false);

        // Gets buttons and connects them to an OnClickListener
        view.findViewById(R.id.roll_button).setOnClickListener(this);
        view.findViewById(R.id.reset_button).setOnClickListener(this);
        view.findViewById(R.id.favorite_button).setOnClickListener(this);

        // Gets the EditText and TextView boxes
        numSidesEditText = view.findViewById(R.id.num_sides_edittext);
        numDiceEditText = view.findViewById(R.id.num_dice_edittext);
        resultText = view.findViewById(R.id.result_textview);

        if (s >= 0 && d >= 0) {
            numSidesEditText.setText(Integer.toString(s));
            numDiceEditText.setText(Integer.toString(d));
        }

        return view;
    }

    /**
     * Clears the text of the passed in EditText item
     * @param t is the item that is having its text cleared
     */
    private void clearEditText(EditText t) {
        t.setText("");
        sides = -1;
        dice = -1;
    }

    /**
     * Clears the text of the passed in TextView item
     * @param t is the item that is having its text cleared
     */
    private void clearTextView(TextView t) {
        t.setText("");
    }

    /**
     * Clears focus from both editText boxes and passes the numbers from the EditText boxes to the rollDice() method
     */
    private void rollDice() {
        numSidesEditText.clearFocus();
        numDiceEditText.clearFocus();
        int result;
        try {
            sides = Integer.parseInt(numSidesEditText.getText().toString());
            dice = Integer.parseInt(numDiceEditText.getText().toString());
            result = rollDice(dice, sides);
            lastResult = result;
            // Alters the display size of the result text for large numbers to prevent it from going off of the screen
            if (result > 99999) {
                resultText.setTextSize(85);
            } else {
                resultText.setTextSize(100);
            }
            resultText.setText(result + "");

            // Updates the result content description for talkback users
            CharSequence cs = Integer.toString(result);
            resultText.setContentDescription(cs);

        } catch (Exception e) {
            // Catches the event where user enters non-numbers into input
        }
    }

    /**
     * Rolls the designated number of dice and returns the total value
     * @param numDice is the number of dice being rolled
     * @param numSides is the number of sides the dice have
     * @return the sum of all dice rolls
     */
    private int rollDice(int numDice, int numSides) {
        int sum = 0;
        if (numSides == 0) return sum;
        for (int i = 0; i < numDice; i++) {
            sum += rand.nextInt(numSides) + 1;
        }
        return sum;
    }

    /**
     * When the favorite button is selected gathers the information from the editTexts and passes it into a Main Activity method
     */
    private void addNewFavorite() {
        numSidesEditText.clearFocus();
        numDiceEditText.clearFocus();
        try {
            ((MainActivity) getActivity()).addNewFavorite(numSidesEditText.getText().toString(), numDiceEditText.getText().toString());
            } catch (Exception e) {
            // Catches exceptions with user input
        }
    }

    /**
     * Changes to Custom Roll fragment from preset rolls and changes the input sides and dice
     * @param numSides is the number of sides of the preset roll
     * @param numDice is the number of dice of the preset roll
     */
    public void fromPresets(int numSides, int numDice) {
        presetNumDice = Integer.toString(numDice);
        presetNumSides = Integer.toString(numSides);
        numSidesEditText.setText(Integer.toString(numSides));
        numDiceEditText.setText(Integer.toString(numDice));
        s = numSides;
        d = numDice;
    }

    /**
     * This method is responsible for adding the newest roll to the history fragments list
     */
    private void addNewHistoryItem() {
         try {
             if (dice != -1 && sides != -1) {
                 ((MainActivity) getActivity()).addNewHistory(lastResult, sides, dice);
             }
         } catch (Exception e) {
             // Catches instance when there is no result, sides, or dice values available
         }
    }

    /**
     * OnClickListener
     * @param v is the fragments view
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.favorite_button:
                //Moves to the add new favorite screen
                addNewFavorite();
                break;
            case R.id.reset_button:
                // Resets all text views
                clearEditText(numSidesEditText);
                clearEditText(numDiceEditText);
                clearTextView(resultText);
                break;
            case R.id.roll_button:
                // Simulates a dice roll
                rollDice();
                addNewHistoryItem();
                break;
        }
    }

    /**
     * Ensures the proper values for the number of dice and number of sides edit text fields are displayed when returning to the fragment
     */
    @Override
    public void onResume() {
        // If presetNumSides and presetNumDice are not null then replace the current values with these
        if (presetNumSides != null && presetNumDice != null) {
            numDiceEditText.setText(presetNumDice);
            numSidesEditText.setText(presetNumSides);
            // Once the values have been replaced reset the values to null
            presetNumDice = null;
            presetNumSides = null;
        }
        super.onResume();

    }

}


