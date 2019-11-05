package com.lab.calorie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class BmrValueAdapter extends RecyclerView.Adapter<BmrValueAdapter.BmrValueViewHolder> {

    class BmrValueViewHolder extends RecyclerView.ViewHolder {
        private final TextView bmrValueItemView;

        private BmrValueViewHolder(View itemView) {
            super(itemView);
            bmrValueItemView = itemView.findViewById(R.id.textViewBmrValue);
        }
    }

    private final LayoutInflater mInflater;
    private Bmr mBmr;

    BmrValueAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BmrValueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_bmr_value, parent, false);
        return new BmrValueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BmrValueViewHolder holder, int position) {
        if (mBmr != null) {
            Bmr current = mBmr;
            holder.bmrValueItemView.setText(Double.toString(current.getValue()) + " cal/day");
        } else {
            holder.bmrValueItemView.setText("Belum ada input BMR apapun");
        }
    }

    void setBmr(Bmr bmr) {
        mBmr = bmr;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mBmr != null) {
            return 1;
        }
        return 0;
    }
}
