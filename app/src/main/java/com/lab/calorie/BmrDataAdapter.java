package com.lab.calorie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class BmrDataAdapter extends RecyclerView.Adapter<BmrDataAdapter.BmrDataViewHolder> {

    class BmrDataViewHolder extends RecyclerView.ViewHolder {
        private final TextView genderItemView;
        private final TextView ageItemView;
        private final TextView heightItemView;
        private final TextView weightItemView;

        private BmrDataViewHolder(View itemView) {
            super(itemView);
            genderItemView = itemView.findViewById(R.id.textViewGender);
            ageItemView = itemView.findViewById(R.id.textViewAge);
            heightItemView = itemView.findViewById(R.id.textViewHeight);
            weightItemView = itemView.findViewById(R.id.textViewWeight);
        }
    }

    private final LayoutInflater mInflater;
    private Bmr mBmr;

    BmrDataAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BmrDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_bmr_data, parent, false);
        return new BmrDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BmrDataViewHolder holder, int position) {
        if (mBmr != null) {
            Bmr current = mBmr;
            String gender = current.getIsMale() ? "Male" : "Female";
            String age = Integer.toString(current.getAge());
            String height = Integer.toString(current.getHeight());
            String weight = Integer.toString(current.getWeight());

            holder.genderItemView.setText(gender);
            holder.ageItemView.setText(age);
            holder.heightItemView.setText(height + " cm");
            holder.weightItemView.setText(weight + " kg");
        } else {
            holder.genderItemView.setText("Belum ada input BMR apapun");
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
