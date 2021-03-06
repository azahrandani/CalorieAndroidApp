package com.lab.calorie.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.calorie.R;
import com.lab.calorie.adapter.SelectedFoodAdapter;
import com.lab.calorie.model.Food;

import java.util.ArrayList;
import java.util.List;

public class SelectedFoodFragment extends Fragment {

    private SelectedFoodAdapter adapter;
    private RecyclerView recyclerView;
    private List<Food> selectedFood;

    public static SelectedFoodFragment newInstance() {
        return new SelectedFoodFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        View view = inflater.inflate(R.layout.selected_food_fragment, container, false);

        selectedFood = new ArrayList<>();

        for (String key : bundle.keySet()) {
            selectedFood.add((Food) bundle.getSerializable(key));
        }

        recyclerView = view.findViewById(R.id.selectedFoodRecyclerView);
        adapter = new SelectedFoodAdapter(getContext(), selectedFood);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
