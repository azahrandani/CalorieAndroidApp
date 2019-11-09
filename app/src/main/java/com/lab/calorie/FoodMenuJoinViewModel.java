package com.lab.calorie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FoodMenuJoinViewModel extends AndroidViewModel {

    private FoodMenuJoinRepository mRepository;
    private LiveData<List<FoodMenuJoin>> mAllFoodMenuJoin;

    public FoodMenuJoinViewModel(Application application) {
        super(application);
        mRepository = new FoodMenuJoinRepository(application);
        mAllFoodMenuJoin = mRepository.getAllFoodMenuJoin();
    }

    public LiveData<List<FoodMenuJoin>> getAllFoodMenuJoin() {
        return mAllFoodMenuJoin;
    }

    public void insert(FoodMenuJoin foodMenuJoin) {
        mRepository.insert(foodMenuJoin);
    }

    public LiveData<List<Food>> getFoodForMenu(int menuId) {
        return mRepository.getFoodForMenu(menuId);
    }

}
