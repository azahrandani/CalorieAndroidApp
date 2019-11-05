package com.lab.calorie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    private List<Food> dataList;
    private Context context;
    private int[] selectedItems;
    private int totalCalorie = 0;
    private int bmrValue;

    private TextView totalCalorieTextView;
    private TextView moreCalorieTextView;

    public CustomAdapter(Context context,List<Food> dataList, TextView totalCalorieTextView, TextView moreCalorieTextView, int bmrValue){
        this.context = context;
        this.dataList = dataList;
        this.selectedItems = new int[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            selectedItems[i] = 0;
        }
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

        String foodName = dataList.get(position).getName();
        String foodNutrient = Integer.toString(dataList.get(position).getfoodNutrientList().get(0).getValue());

        holder.txtName.setText(foodName);
        holder.txtKcal.setText(foodNutrient);
        totalCalorieTextView.setText(Integer.toString(totalCalorie));
        moreCalorieTextView.setText(Integer.toString(bmrValue-totalCalorie));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("position yang diklik adalah " + position);
                selectedItems[position] = selectedItems[position] == 1 ? 0 : 1;

                if (selectedItems[position] == 1) {
                    totalCalorie += dataList.get(position).getfoodNutrientList().get(0).getValue();
                } else {
                    totalCalorie -= dataList.get(position).getfoodNutrientList().get(0).getValue();
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

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<Food> getSelectedFood() {
        List<Food> selectedFood = new ArrayList<>();
        for (int i = 0; i < selectedItems.length; i++) {
            if (selectedItems[i] == 1) {
                selectedFood.add(dataList.get(i));
            }
        }
        return selectedFood;
    }

}