package com.lab.calorie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lab.calorie.R;
import com.lab.calorie.adapter.FoodMenuJoinAdapter;
import com.lab.calorie.databinding.DetailMenuFragmentBinding;
import com.lab.calorie.model.Food;
import com.lab.calorie.model.Menu;
import com.lab.calorie.viewModel.FoodMenuJoinViewModel;

import java.util.List;

public class DetailMenuFragment extends Fragment {

    DetailMenuFragmentBinding binding;
    private FoodMenuJoinAdapter adapter;
    private RecyclerView recyclerView;

    private FoodMenuJoinViewModel mFoodMenuJoinViewModel;

    public static DetailMenuFragment newInstance() {
        return new DetailMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_menu_fragment, container, false);
        View view = binding.getRoot();

        Menu menu = (Menu) bundle.getSerializable("item_selected_key");

        binding.setMenu(menu);

        recyclerView = view.findViewById(R.id.foodMenuRecyclerView);
        adapter = new FoodMenuJoinAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        mFoodMenuJoinViewModel = new ViewModelProvider(getActivity()).get(FoodMenuJoinViewModel.class);

        mFoodMenuJoinViewModel.getFoodForMenu(menu.getId()).observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foodList) {
                adapter.setFood(foodList);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
