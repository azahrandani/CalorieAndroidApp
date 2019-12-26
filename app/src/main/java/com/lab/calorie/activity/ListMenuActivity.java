package com.lab.calorie.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.lab.calorie.R;
import com.lab.calorie.fragment.DetailMenuFragment;
import com.lab.calorie.fragment.ListMenuFragment;
import com.lab.calorie.model.Menu;
import com.lab.calorie.viewModel.FoodMenuJoinViewModel;

public class ListMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (getIntent().getExtras() != null) {
            System.out.println("###masuk ke if yang getIntent != null");
            Bundle menuBundle = getIntent().getBundleExtra("menu");
            Menu menu = (Menu) menuBundle.getSerializable("menu");
            int menuId = (int) menuBundle.getSerializable("menu_id");

            DetailMenuFragment detailMenuFragment = new DetailMenuFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("item_selected_key", menu);
            bundle.putSerializable("menu_id", menuId);
            detailMenuFragment.setArguments(bundle);
            switchContent(R.id.list_detail_menu_fragment, detailMenuFragment);
        } else {
            System.out.println("###masuk ke else yang getIntent== null");
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
