package com.lab.calorie.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lab.calorie.model.Bmr;
import com.lab.calorie.repository.BmrRepository;

public class BmrViewModel extends AndroidViewModel {

    private BmrRepository mRepository;
    private LiveData<Bmr> mTheBmr;

    public BmrViewModel(Application application) {
        super(application);
        mRepository = new BmrRepository(application);
        mTheBmr = mRepository.getBmr();
    }

    public LiveData<Bmr> getTheBmr() {
        return mTheBmr;
    }

    public void insert(Bmr bmr) {
        mRepository.insert(bmr);
    }
}
