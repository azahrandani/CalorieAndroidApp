package com.lab.calorie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lab.calorie.R;
import com.lab.calorie.model.Food;

import java.util.List;

public class FoodMenuJoinAdapter extends RecyclerView.Adapter<FoodMenuJoinAdapter.FoodMenuJoinViewHolder> {

    private List<Food> foodList;
    private Context context;

    public FoodMenuJoinAdapter(Context context) {
        this.context = context;
    }

    class FoodMenuJoinViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        TextView txtName;
        TextView txtKcal;
        CardView foodCardView;

        public FoodMenuJoinViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtName = mView.findViewById(R.id.food_name);
            txtKcal = mView.findViewById(R.id.food_kcal);
            foodCardView = mView.findViewById(R.id.food_card_view);
        }
    }

    @Override
    public FoodMenuJoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new FoodMenuJoinAdapter.FoodMenuJoinViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodMenuJoinViewHolder holder, int position) {
        int greyColor = holder.mView.getResources().getColor(R.color.bmrGrey);

        String foodName = foodList.get(position).getName();
        String foodNutrient = Integer.toString(foodList.get(position).getKcal());

        holder.txtName.setText(foodName);
        holder.txtKcal.setText(foodNutrient);
        holder.foodCardView.setCardBackgroundColor(greyColor);
    }

    public void setFood(List<Food> foodList){
        this.foodList = foodList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (foodList != null) {
            return foodList.size();
        }
        return 0;
    }

}
