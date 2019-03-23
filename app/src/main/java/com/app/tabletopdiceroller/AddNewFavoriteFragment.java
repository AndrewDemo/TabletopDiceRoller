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

import com.app.tabletopdiceroller.Objects.Roll;
import com.app.tabletopdiceroller.room.RollRepository;

/**
 * This fragment is the screen displayed when creating a new favorite roll and adding it to the preset roll list
 */
public class AddNewFavoriteFragment extends Fragment implements View.OnClickListener {

    private static AddNewFavoriteFragment fragmentInstance = null;
    private Button backBtn;
    private Button createBtn;
    private TextView numSides;
    private TextView numDice;
    private EditText rollNameText;
    private static String sides = "N/A";
    private static String dice = "N/A";
    private int sidesInt = 0;
    private int diceInt = 0;

    /**
     * Creates a new fragment if the current one is null, otherwise returns the current fragment.
     * @return The current AddNewFavoriteFragment instance
     */
    public static AddNewFavoriteFragment getFragment() {
        if (fragmentInstance == null) {
            fragmentInstance = new AddNewFavoriteFragment();
        }
        return fragmentInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_fav, container, false);
        backBtn = view.findViewById(R.id.back_button);
        backBtn.setOnClickListener(this);
        createBtn = view.findViewById(R.id.confirm_button);
        createBtn.setOnClickListener(this);
        numSides = view.findViewById(R.id.favDisplayNumSides);
        numDice = view.findViewById(R.id.favDisplayNumDice);
        rollNameText = view.findViewById(R.id.rollNameText);
        numSides.setText(sides);
        numDice.setText(dice);

        return view;
    }


    /**
     * OnClickListener
     * @param v is the view for this fragment
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                ((MainActivity)getActivity()).cancelNewFavorite();
                break;
            case R.id.confirm_button:
                try {
                    rollNameText.clearFocus();
                    sidesInt = Integer.parseInt(sides);
                    diceInt = Integer.parseInt(dice);
                    String rollName = rollNameText.getText().toString();
                    Roll roll = new Roll(diceInt, sidesInt, rollName);
                    ((MainActivity)getActivity()).insertRollToDatabase(roll);
                    ((MainActivity)getActivity()).confirmNewRoll(sidesInt, diceInt, rollName);
                } catch (Exception e) {
                    // Catches exception with user input
                }
                break;
        }
    }

    /**
     * Sets the 'sides' variable, which states how many sides there are per dice
     * @param s is the updated number of sides
     */
    public static void setSides(String s) {
        sides = s;
    }

    /**
     * Sets the 'dice' variable, which states how many dice are rolled in this roll
     * @param d is the updated number of dice
     */
    public static void setDice(String d) {
        dice = d;
    }
}
