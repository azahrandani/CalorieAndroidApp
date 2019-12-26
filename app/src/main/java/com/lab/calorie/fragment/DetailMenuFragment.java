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
import com.lab.calorie.model.FoodMenuJoin;
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

        final Menu menu = (Menu) bundle.getSerializable("item_selected_key");
        int menuIdBundle = -1;
        if (bundle.getSerializable("menu_id") != null) {
            menuIdBundle = (int) bundle.getSerializable("menu_id");
        }

        binding.setMenu(menu);

        recyclerView = view.findViewById(R.id.foodMenuRecyclerView);
        adapter = new FoodMenuJoinAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        mFoodMenuJoinViewModel = new ViewModelProvider(getActivity()).get(FoodMenuJoinViewModel.class);

        final int menuId;
        if (menuIdBundle < 0) {
            menuId = menu.getId();
        } else {
            menuId = menuIdBundle;
        }

        mFoodMenuJoinViewModel.getFoodForMenu(menuId).observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foodList) {
                adapter.setFood(foodList);
                System.out.println("###adapter.setFood di DetailMenuFragment");
                System.out.println("###menu nya adalah untuk tanggal " + menu.getCalendarInNames());
                System.out.println("###bmr val nya " + menu.getBmrValue());
                System.out.println("###id dari menu nya adalah " + menuId);
                System.out.println("###FoodList size " + foodList.size());
            }
        });

        mFoodMenuJoinViewModel.getAllFoodMenuJoin().observe(this, new Observer<List<FoodMenuJoin>>() {
            @Override
            public void onChanged(List<FoodMenuJoin> foodMenuJoins) {
                System.out.println("###FoodMenuJoinList di DetailFragment size " + foodMenuJoins.size());
                for (FoodMenuJoin foodMenuJoin : foodMenuJoins) {
                    System.out.println(foodMenuJoin.getFoodId() + " " + foodMenuJoin.getMenuId());
                }
            }
        });

        System.out.println("di onCreateView nya DetailMenuFragment");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
