package com.lab.calorie;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BmrDataFragment extends Fragment {

    RecyclerView recyclerView;
    private BmrViewModel mBmrViewModel;

    public static BmrDataFragment newInstance() {
        return new BmrDataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bmr_data_fragment, container, false);

        final BmrDataAdapter adapter = new BmrDataAdapter(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewBmrData);
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
