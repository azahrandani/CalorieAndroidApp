package com.lab.calorie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MenuViewModel extends AndroidViewModel {

    private MenuRepository mRepository;
    private LiveData<List<Menu>> mAllMenu;

    public MenuViewModel(Application application) {
        super(application);
        mRepository = new MenuRepository(application);
        mAllMenu = mRepository.getAllMenu();
    }

    public LiveData<List<Menu>> getAllMenu() {
        return mAllMenu;
    }

    public void insert(Menu menu) {
        mRepository.insert(menu);
    }

}
