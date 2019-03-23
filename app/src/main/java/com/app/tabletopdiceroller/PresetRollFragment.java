package com.app.tabletopdiceroller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.tabletopdiceroller.Adapters.RollRecyclerAdapter;
import com.app.tabletopdiceroller.Objects.Roll;
import com.app.tabletopdiceroller.room.RollRepository;
import com.app.tabletopdiceroller.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
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
        recyclerView.addItemDecoration(itemDecorator);
        rollRecyclerAdapter = new RollRecyclerAdapter(rolls, this);
        recyclerView.setAdapter(rollRecyclerAdapter);
    }

    public void retrieveRolls(List<Roll> rolls) {
//        if(rolls.size() > 0) {
//            rolls.clear();
//        }
//        if (rolls != null) {
//            this.rolls.addAll(rolls);
//        }
//        for (Roll r : rolls) {
//            rolls.add(0, r);
//            rollRecyclerAdapter.notifyDataSetChanged();
//        }
        this.rolls.addAll(rolls);
        rollRecyclerAdapter.notifyDataSetChanged();
    }

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


