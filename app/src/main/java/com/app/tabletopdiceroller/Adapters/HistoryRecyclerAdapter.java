package com.app.tabletopdiceroller.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.app.tabletopdiceroller.Objects.PastRoll;
import com.app.tabletopdiceroller.R;
import java.util.ArrayList;

/**
 * Serves as the recycler adapter for the history fragment
 */
public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {

    // List containing past rolls
    private ArrayList<PastRoll> rolls;

    private OnHistoryListener onHistoryListener;

    /**
     * Public constructor setting ArrayList of past rolls
     * @param rolls is a list of past roll objects
     */
    public HistoryRecyclerAdapter(ArrayList<PastRoll> rolls, OnHistoryListener onHistoryListener) {
        this.rolls = rolls;
        this.onHistoryListener = onHistoryListener;
    }

    @NonNull
    @Override
    public HistoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_history_roll_list_item, viewGroup, false);
        return new ViewHolder(view, onHistoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.diceDisplay.setText(rolls.get(i).getDisplayString());
        viewHolder.rollResult.setText(rolls.get(i).getResult());
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
        private TextView rollResult, diceDisplay;
        OnHistoryListener onHistoryListener;
        public ViewHolder(@NonNull View itemView, OnHistoryListener onHistoryListener) {
            super(itemView);
            rollResult = itemView.findViewById(R.id.roll_result);
            diceDisplay = itemView.findViewById(R.id.dice_used);
            this.onHistoryListener = onHistoryListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onHistoryListener.onItemClick(getAdapterPosition());
        }
    }

    /**
     * OnClickListener for History Objects
     */
    public interface OnHistoryListener {
        void onItemClick(int position);
    }
}
