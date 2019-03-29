package com.app.tabletopdiceroller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.tabletopdiceroller.Adapters.RollRecyclerAdapter;
import com.app.tabletopdiceroller.Objects.Roll;
import com.app.tabletopdiceroller.util.VerticalSpacingItemDecorator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PresetRollFragment extends Fragment implements RollRecyclerAdapter.OnRollListener {

    // Fragment instance defaults to null for singleton pattern
    private static PresetRollFragment fragmentInstance = null;

    /**
     * Singleton pattern
     * @return the instance of the fragment if there already is one, otherwise creates a new one and returns it
     */
    public static PresetRollFragment getFragment() {
        if (fragmentInstance == null) {
            fragmentInstance = new PresetRollFragment();
        }
        return fragmentInstance;
    }

    // UI components
    private RecyclerView recyclerView;

    // Variables
    private ArrayList<Roll> rolls = new ArrayList<>();
    private RollRecyclerAdapter rollRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preset_roll, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.recyclerViewPreset);
        initRecyclerView();
    }

    /**
     * Creates a new Roll object and adds it to the rolls list
     * @param numSides is the number of sides
     * @param numDice is the number of dice
     * @param rollName is the display name of the roll
     */
    public void createNewRoll(int numSides, int numDice, String rollName) {
        Roll roll = new Roll(numDice, numSides, rollName);
        rolls.add(0, roll);
        rollRecyclerAdapter.notifyDataSetChanged();
    }

    /**
     * Initializes the recycler view
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(itemDecorator);
        rollRecyclerAdapter = new RollRecyclerAdapter(rolls, this);
        recyclerView.setAdapter(rollRecyclerAdapter);
    }

    /**
     * Retrieves rolls from the database to fill the preset roll list
     * @param rolls is the list of rolls filling the preset rolls recycler view
     */
    public void retrieveRolls(List<Roll> rolls) {
        this.rolls.addAll(rolls);
        // Reverses the rolls list so it is in the proper order
        Collections.reverse(this.rolls);
        rollRecyclerAdapter.notifyDataSetChanged();
    }

    /**
     * Deletes a roll from the favorites roll list
     * @param roll is the roll being deleted
     */
    private void deleteRoll(Roll roll) {
        rolls.remove(roll);
        ((MainActivity)getActivity()).deleteRoll(roll);
        rollRecyclerAdapter.notifyDataSetChanged();
    }

    /**
     * Allows the user to delete favorites from the database by swiping to the left or right.
     * dragDirs is set to 0 because list items will not be moved around
     */
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            // Do nothing
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            // Delete rolls from the database on left or right swipe
            deleteRoll(rolls.get(viewHolder.getAdapterPosition()));
        }
    };

    /**
     * When a list item is clicked the information is exported to the Custom Roll Fragment where it will be ready to be used by the user.
     * @param position is the position of the roll in the recyclerView
     */
    @Override
    public void onItemClick(int position) {
        Roll roll = rolls.get(position);
        ((MainActivity)getActivity()).presetRollSelection(roll.getNumberOfSides(), roll.getNumberOfDice());
    }
}


