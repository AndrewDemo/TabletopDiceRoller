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

    private static PresetRollFragment fragmentInstance = null;

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

    public void createNewRoll(int numSides, int numDice, String rollName) {
        Roll roll = new Roll(numDice, numSides, rollName);
        rolls.add(0, roll);
        rollRecyclerAdapter.notifyDataSetChanged();
    }

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
     * Retreives rolls from the database to fill the preset roll list
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
            deleteRoll(rolls.get(viewHolder.getAdapterPosition()));
        }
    };

    /**
     * Determines what happens when a list item is clicked
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Roll roll = rolls.get(position);
        // Chose what pressing a button does here
        ((MainActivity)getActivity()).presetRollSelection(roll.getNumberOfSides(), roll.getNumberOfDice());
    }
}


