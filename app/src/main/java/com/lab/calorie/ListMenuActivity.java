package com.lab.calorie;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class ListMenuActivity extends AppCompatActivity {

    private FoodMenuJoinViewModel mFoodMenuJoinViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        mFoodMenuJoinViewModel = new ViewModelProvider(this).get(FoodMenuJoinViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (getIntent().getExtras() != null) {
            Bundle menuBundle = getIntent().getBundleExtra("menu");
            Menu menu = (Menu) menuBundle.getSerializable("menu");

            DetailMenuFragment detailMenuFragment = new DetailMenuFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("item_selected_key", menu);
            detailMenuFragment.setArguments(bundle);

//            fragmentTransaction.replace(R.id.list_detail_menu_fragment, detailMenuFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
            switchContent(R.id.list_detail_menu_fragment, detailMenuFragment);
        } else {
            ListMenuFragment listMenuFragment = new ListMenuFragment();
            fragmentTransaction.replace(R.id.list_detail_menu_fragment, listMenuFragment);
            fragmentTransaction.commit();
        }

    }

    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }

}
