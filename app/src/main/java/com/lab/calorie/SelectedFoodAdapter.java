package com.lab.calorie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectedFoodAdapter extends RecyclerView.Adapter<SelectedFoodAdapter.SelectedFoodViewHolder> {

    private List<Food> foodList;
    private Context context;

    public SelectedFoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    class SelectedFoodViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        TextView txtName;
        TextView txtKcal;
        CardView foodCardView;

        public SelectedFoodViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtName = mView.findViewById(R.id.food_name);
            txtKcal = mView.findViewById(R.id.food_kcal);
            foodCardView = mView.findViewById(R.id.food_card_view);
        }
    }

    @Override
    public SelectedFoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new SelectedFoodAdapter.SelectedFoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectedFoodViewHolder holder, int position) {
        int greyColor = holder.mView.getResources().getColor(R.color.bmrGrey);

        String foodName = foodList.get(position).getName();
        String foodNutrient = Integer.toString(foodList.get(position).getfoodNutrientList().get(0).getValue());

        holder.txtName.setText(foodName);
        holder.txtKcal.setText(foodNutrient);
        holder.foodCardView.setCardBackgroundColor(greyColor);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

}
