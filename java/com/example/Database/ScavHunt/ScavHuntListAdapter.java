package com.example.Database.ScavHunt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.josh.scavvy.R;

import java.util.List;

public class ScavHuntListAdapter extends RecyclerView.Adapter<ScavHuntListAdapter.ScavHuntViewHolder> {

    class ScavHuntViewHolder extends RecyclerView.ViewHolder {
        private final TextView scavHuntItemView;

        private ScavHuntViewHolder(View itemView) {
            super(itemView);
            scavHuntItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<ScavHunt> scavHunts; // Cached copy of words

    public ScavHuntListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ScavHuntViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ScavHuntViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScavHuntViewHolder holder, int position) {
        if (scavHunts != null) {
            ScavHunt current = scavHunts.get(position);
            holder.scavHuntItemView.setText(current.getName());
        } else {
            // Covers the case of data not being ready yet.
            holder.scavHuntItemView.setText("No Word");
        }
    }

    public void setScavHunts(List<ScavHunt> words){
        scavHunts = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (scavHunts != null)
            return scavHunts.size();
        else return 0;
    }
}