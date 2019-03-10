package com.app.tabletopdiceroller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

/**
 * This is the fragment used when the user is creating or rolling a custom roll
 */
public class CustomRollFragment extends Fragment implements View.OnClickListener{

    private static CustomRollFragment fragmentInstance = null;
    private Random rand = new Random();
    private int sides = -1;
    private int dice = -1;
    private int result = -1;
    private String sidesString, diceString;
    private Button resetBtn;
    private Button rollBtn;
    private Button favBtn;
    private EditText numSides;
    private EditText numDice;
    private static int s = -1;
    private static int d = -1;
    private TextView resultText;

    // Variable keeping track of last rolls result for the history fragment
    int lastResult;


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
        rollBtn = view.findViewById(R.id.roll_button);
        rollBtn.setOnClickListener(this);
        resetBtn = view.findViewById(R.id.reset_button);
        resetBtn.setOnClickListener(this);
        favBtn = view.findViewById(R.id.favorite_button);
        favBtn.setOnClickListener(this);
        // Gets the EditText and TextView boxes
        numSides = view.findViewById(R.id.num_sides_edittext);
        numDice = view.findViewById(R.id.num_dice_edittext);
        resultText = view.findViewById(R.id.result_textview);

        if (s >= 0 && d >= 0) {
            numSides.setText(Integer.toString(s));
            numDice.setText(Integer.toString(d));
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
        numSides.clearFocus();
        numDice.clearFocus();
        try {
            sides = Integer.parseInt(numSides.getText().toString());
            dice = Integer.parseInt(numDice.getText().toString());
            result = rollDice(dice, sides);
            lastResult = result;
            if (result > 99999) {
                resultText.setTextSize(85);
            } else {
                resultText.setTextSize(100);
            }
            resultText.setText(result + "");
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
        numSides.clearFocus();
        numDice.clearFocus();
        try {
            sidesString = numSides.getText().toString();
            diceString = numDice.getText().toString();
            ((MainActivity) getActivity()).addNewFavorite(sidesString, diceString);
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
                addNewFavorite();
                break;
            case R.id.reset_button:
                clearEditText(numSides);
                clearEditText(numDice);
                clearTextView(resultText);
                break;
            case R.id.roll_button:
                rollDice();
                addNewHistoryItem();
                break;
        }
    }
}


