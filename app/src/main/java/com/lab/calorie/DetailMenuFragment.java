package com.lab.calorie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.lab.calorie.databinding.DetailMenuFragmentBinding;

public class DetailMenuFragment extends Fragment {

    DetailMenuFragmentBinding binding;

    public static DetailMenuFragment newInstance() {
        return new DetailMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_menu_fragment, container, false);
        View view = binding.getRoot();
        System.out.println("apakah bundle di detail menu fragment null? " + bundle.isEmpty());
        System.out.println(bundle.getSerializable("item_selected_key"));

        Menu menu = (Menu) bundle.getSerializable("item_selected_key");
        System.out.println(menu.getCalendarInSlash());
        System.out.println(menu.getCalorieValue());
        System.out.println(menu.getBmrValue());

        binding.setMenu(menu);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
