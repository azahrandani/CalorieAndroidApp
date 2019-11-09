package com.lab.calorie.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.calorie.R;
import com.lab.calorie.adapter.BmrValueAdapter;
import com.lab.calorie.model.Bmr;
import com.lab.calorie.viewModel.BmrViewModel;

public class BmrValueFragment extends Fragment {
    RecyclerView recyclerView;
    private BmrViewModel mBmrViewModel;

    public static BmrValueFragment newInstance() {
        return new BmrValueFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bmr_value_fragment, container, false);

        final BmrValueAdapter adapter = new BmrValueAdapter(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewBmrValue);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mBmrViewModel = new ViewModelProvider(this).get(BmrViewModel.class);
        mBmrViewModel.getTheBmr().observe(this, new Observer<Bmr>() {
            @Override
            public void onChanged(Bmr bmr) {
                adapter.setBmr(bmr);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
