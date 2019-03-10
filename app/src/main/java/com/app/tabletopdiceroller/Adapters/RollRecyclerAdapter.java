package com.app.tabletopdiceroller.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tabletopdiceroller.Objects.Roll;
import com.app.tabletopdiceroller.R;

import java.util.ArrayList;

/**
 * This class is the adapter for the preset roll recycler view
 */
public class RollRecyclerAdapter extends RecyclerView.Adapter<RollRecyclerAdapter.ViewHolder> {

    // ArrayList representing the preset roll objects in the recycler View
    private ArrayList<Roll> rolls;

    private OnRollListener mOnRollListener;

    /**
     * Public constructor setting ArrayList of rolls
     * @param rolls is a list of roll objects
     */
    public RollRecyclerAdapter(ArrayList<Roll> rolls, OnRollListener onRollListener) {
        this.rolls = rolls;
        this.mOnRollListener = onRollListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_roll_list_item, viewGroup, false);
        return new ViewHolder(view, mOnRollListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.diceDisplay.setText(rolls.get(i).getDisplayString());
        viewHolder.rollTitle.setText(rolls.get(i).getRollName());
    }


    /**
     * Returns the size of the list
     * @return size of rolls arrayList
     */
    @Override
    public int getItemCount() {
        return rolls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView rollTitle, diceDisplay;
        OnRollListener onRollListener;
        public ViewHolder(@NonNull View itemView, OnRollListener onRollListener) {
            super(itemView);
            rollTitle = itemView.findViewById(R.id.roll_title);
            diceDisplay = itemView.findViewById(R.id.dice_display);
            this.onRollListener = onRollListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onRollListener.onItemClick(getAdapterPosition());
        }
    }

    /**
     * OnClickListener for this adapter class
     */
    public interface OnRollListener {
        void onItemClick(int position);
    }
}
