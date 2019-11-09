package com.lab.calorie.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lab.calorie.model.Menu;
import com.lab.calorie.repository.MenuRepository;

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

    public LiveData<Menu> getMenuById(int id) {
        return mRepository.getMenuById(id);
    }

    public void insert(Menu menu) {
        mRepository.insert(menu);
    }

}
