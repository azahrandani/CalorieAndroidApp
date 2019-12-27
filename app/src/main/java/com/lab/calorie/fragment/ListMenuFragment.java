package com.lab.calorie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lab.calorie.R;
import com.lab.calorie.activity.MainActivity;
import com.lab.calorie.adapter.ListMenuAdapter;
import com.lab.calorie.model.Menu;
import com.lab.calorie.viewModel.MenuViewModel;

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
        System.out.println("###onCreateView ListMenuFragment");

        View view = inflater.inflate(R.layout.list_menu_fragment, container, false);

        mMenuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        listMenu = new ArrayList<>();

        recyclerView = view.findViewById(R.id.listMenuRecyclerView);
        adapter = new ListMenuAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        mMenuViewModel.getAllMenu().observe(this, new Observer<List<Menu>>() {
            @Override
            public void onChanged(List<Menu> menuList) {
                adapter.setAllMenu(menuList);
                System.out.println("###MenuList size " + menuList.size());
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            SystemClock.sleep(2000);
            Menu menu = (Menu) bundle.getSerializable("item_selected_key");
            simulateClick(menu);
        }

        ImageView homeButton = view.findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toHomeIntent = new Intent(getContext(), MainActivity.class);
                startActivity(toHomeIntent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void simulateClick(Menu menu) {
        System.out.println("lewat simulateCLick menu");
        adapter.fragmentJump(menu);
    }
}
