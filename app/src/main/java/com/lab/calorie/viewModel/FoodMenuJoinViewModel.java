package com.lab.calorie.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lab.calorie.model.Food;
import com.lab.calorie.model.FoodMenuJoin;
import com.lab.calorie.repository.FoodMenuJoinRepository;

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
