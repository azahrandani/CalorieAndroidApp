package com.lab.calorie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListMenuFragment extends Fragment {

    private ListMenuAdapter adapter;
    private RecyclerView recyclerView;
    private List<Menu> listMenu;
    private MenuViewModel mMenuViewModel;

    public static ListMenuFragment newInstance() {
        return new ListMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Bundle bundle = this.getArguments();
        View view = inflater.inflate(R.layout.list_menu_fragment, container, false);

        mMenuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        listMenu = new ArrayList<>();

        // for (String key : bundle.keySet()) {
        //     selectedFood.add((Food) bundle.getSerializable(key));
        // }

        recyclerView = view.findViewById(R.id.listMenuRecyclerView);
        adapter = new ListMenuAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        mMenuViewModel.getAllMenu().observe(this, new Observer<List<Menu>>() {
            @Override
            public void onChanged(List<Menu> menuList) {
                adapter.setAllMenu(menuList);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
