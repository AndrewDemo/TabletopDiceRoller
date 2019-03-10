package com.app.tabletopdiceroller;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.tabletopdiceroller.Adapters.HistoryRecyclerAdapter;
import com.app.tabletopdiceroller.Objects.PastRoll;
import com.app.tabletopdiceroller.util.VerticalSpacingItemDecorator;
import java.util.ArrayList;

public class HistoryFragment extends Fragment implements HistoryRecyclerAdapter.OnHistoryListener {

    private static HistoryFragment fragmentInstance = null;

    public static HistoryFragment getFragment() {
        if (fragmentInstance == null) {
            fragmentInstance = new HistoryFragment();
        }
        return fragmentInstance;
    }

    // UI components
    private RecyclerView recyclerView;

    // Variables
    private ArrayList<PastRoll> rolls = new ArrayList<>();
    private HistoryRecyclerAdapter historyRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.recyclerViewHistory);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);
        historyRecyclerAdapter = new HistoryRecyclerAdapter(rolls, this);
        recyclerView.setAdapter(historyRecyclerAdapter);
    }

    /**
     * Adds the newest roll to the HistoryFragment when called
     * @param result is the result of the last roll
     * @param sides is the number of sides on the dice in the last roll
     * @param dice is the number of dice rolled in the last roll
     */
    public void addNewHistory(int result, int sides, int dice) {
        PastRoll roll = new PastRoll(sides, dice, "Result: " + Integer.toString(result));
        rolls.add(0, roll);
        historyRecyclerAdapter.notifyDataSetChanged();
    }

    /**
     * Determines what happens when a list item is clicked
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        // Do nothing when an item from history is selected
    }
}


