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

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    private List<Food> foodList;
    private Context context;
    private int[] selectedItems;
    private int totalCalorie = 0;
    private int bmrValue;

    private TextView totalCalorieTextView;
    private TextView moreCalorieTextView;

    public CustomAdapter(Context context, TextView totalCalorieTextView, TextView moreCalorieTextView, int bmrValue){
        this.context = context;
        this.totalCalorieTextView = totalCalorieTextView;
        this.moreCalorieTextView = moreCalorieTextView;
        this.bmrValue = bmrValue;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtName;
        TextView txtKcal;
        CardView foodCardView;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtName = mView.findViewById(R.id.food_name);
            txtKcal = mView.findViewById(R.id.food_kcal);
            foodCardView = mView.findViewById(R.id.food_card_view);
        }

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        int greyColor = holder.mView.getResources().getColor(R.color.bmrGrey);
        int darkBlueColor = holder.mView.getResources().getColor(R.color.bmrDarkBlue);

        String foodName = foodList.get(position).getName();
        String foodNutrient = Integer.toString(foodList.get(position).getKcal());

        holder.txtName.setText(foodName);
        holder.txtKcal.setText(foodNutrient);
        totalCalorieTextView.setText(Integer.toString(totalCalorie));
        moreCalorieTextView.setText(Integer.toString(bmrValue-totalCalorie));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItems[position] = selectedItems[position] == 1 ? 0 : 1;

                if (selectedItems[position] == 1) {
                    totalCalorie += foodList.get(position).getKcal();
                } else {
                    totalCalorie -= foodList.get(position).getKcal();
                }

                notifyDataSetChanged();
            }
        });

        if (selectedItems[position] == 1) {
            holder.foodCardView.setCardBackgroundColor(darkBlueColor);
        } else {
            holder.foodCardView.setCardBackgroundColor(greyColor);
        }
    }

    public void setAllFood(List<Food> foodList){
        this.foodList = foodList;
        this.selectedItems = new int[foodList.size()];
        for (int i = 0; i < foodList.size(); i++) {
            selectedItems[i] = 0;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (foodList != null) {
            return foodList.size();
        }
        return 0;
    }

    public List<Food> getSelectedFood() {
        List<Food> selectedFood = new ArrayList<>();
        for (int i = 0; i < selectedItems.length; i++) {
            if (selectedItems[i] == 1) {
                selectedFood.add(foodList.get(i));
            }
        }
        return selectedFood;
    }

    public int getTotalCalorie() {
        return this.totalCalorie;
    }

}