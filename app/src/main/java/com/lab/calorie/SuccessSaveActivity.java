package com.lab.calorie;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;

import com.lab.calorie.databinding.ActivitySuccessSaveBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SuccessSaveActivity extends AppCompatActivity {

    private static final int MY_CAL_WRITE_REQ = 1;
    private FoodMenuJoinAdapter adapter;
    private RecyclerView recyclerView;
    private ActivitySuccessSaveBinding menuBinding;

    private Bundle foodListBundle;
    private String dateString;
    private int bmrValue;
    private int totalCalorie;

    private MenuViewModel mMenuViewModel;
    private FoodMenuJoinViewModel mFoodMenuJoinViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_save);

        mMenuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        mFoodMenuJoinViewModel = new ViewModelProvider(this).get(FoodMenuJoinViewModel.class);

        foodListBundle = getIntent().getBundleExtra("food_list");
        dateString = getIntent().getStringExtra("date_string");
        bmrValue = getIntent().getIntExtra("bmr_value", 0);
        totalCalorie = getIntent().getIntExtra("total_calorie", 0);

        final Calendar calendar = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date date = formatter.parse(dateString);
            System.out.println("CIHUY dateString adalah " + dateString);
            calendar.setTime(date);
            System.out.println("CIHUY jadinya calendar date adalah " + calendar.get(Calendar.DATE));
            System.out.println("CIHUY jadinya calendar month adalah " + calendar.get(Calendar.MONTH));
            System.out.println("CIHUY jadinya calendar year adalah " + calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Menu newMenu = new Menu(calendar, totalCalorie, bmrValue);

        final List<Food> selectedFoodList = new ArrayList<>();

        for (String key : foodListBundle.keySet()) {
            selectedFoodList.add((Food) foodListBundle.getSerializable(key));
        }

        addEvent(calendar, createStringMenu(selectedFoodList));

        CalorieRoomDatabase database = CalorieRoomDatabase.getDatabase(this);
        MenuDao menuDao = database.menuDao();
        FoodMenuJoinDao foodMenuJoinDao = database.foodMenuJoinDao();

        new dbTransactionAsyncTask(this, database, menuDao, foodMenuJoinDao, newMenu, selectedFoodList).execute();

        menuBinding = DataBindingUtil.setContentView(this, R.layout.activity_success_save);

//        Button printCurrentMenu = findViewById(R.id.button_see_all_menu);
//        printCurrentMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("button diklik");
//
//                mMenuViewModel.getAllMenu().observe(SuccessSaveActivity.this, new Observer<List<Menu>>() {
//                    @Override
//                    public void onChanged(List<Menu> allMenu) {
//                        System.out.println("onChanged apa engga sih ini");
//                        System.out.println("size " + allMenu.size());
//                        for (Menu menu : allMenu) {
//                            System.out.println("caloriiiii value: " + menu.getCalorieValue());
//                            System.out.println("bmrrrr value: " + menu.getBmrValue());
//                        }
//                    }
//                });
//            }
//        });

        recyclerView = findViewById(R.id.foodMenuRecyclerView);
        adapter = new FoodMenuJoinAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button buttonSeeAllMenu = findViewById(R.id.button_see_all_menu);
        buttonSeeAllMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessSaveActivity.this, ListMenuActivity.class);
                startActivity(intent);
            }
        });


    }

    private void updateRecylerView(int menuId) {
        mFoodMenuJoinViewModel.getFoodForMenu(menuId).observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foodList) {
                adapter.setFood(foodList);
            }
        });
    }

    private void updateMenuBinding(int menuId) {
        mMenuViewModel.getMenuById(menuId).observe(this, new Observer<Menu>() {
            @Override
            public void onChanged(Menu menu) {
                menuBinding.setMenu(menu);
            }
        });
    }

    private String createStringMenu(List<Food> selectedFoodList) {
        String stringMenu = "";
        for (Food food : selectedFoodList) {
            stringMenu += food.getName() + "\n";
        }
        return stringMenu;
    }

    public void addEvent(Calendar calendar, String menu) {

        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_CAL_WRITE_REQ);
        }

        ContentResolver cr = getContentResolver();

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, date, 9, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, date, 21, 0);

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
        values.put(CalendarContract.Events.TITLE, "Calorie Food List");
        values.put(CalendarContract.Events.DESCRIPTION, menu);
        values.put(CalendarContract.Events.CALENDAR_ID, 6);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Jakarta");
        values.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, "1");
        values.put(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS, "1");

        cr.insert(CalendarContract.Events.CONTENT_URI, values);
    }

    private static class dbTransactionAsyncTask extends AsyncTask<Void, Void, Void> {

        SuccessSaveActivity activity;

        CalorieRoomDatabase database;
        MenuDao menuDao;
        FoodMenuJoinDao foodMenuJoinDao;

        Menu newMenu;
        List<Food> selectedFoodList;

        int newMenuId;

        dbTransactionAsyncTask(SuccessSaveActivity activity,
                               CalorieRoomDatabase database,
                               MenuDao menuDao,
                               FoodMenuJoinDao foodMenuJoinDao,
                               Menu newMenu,
                               List<Food> selectedFoodList) {
            this.activity = activity;
            this.database = database;
            this.menuDao = menuDao;
            this.foodMenuJoinDao = foodMenuJoinDao;
            this.newMenu = newMenu;
            this.selectedFoodList = selectedFoodList;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        newMenuId = (int) menuDao.insert(newMenu);
                        for (Food food : selectedFoodList) {
                            FoodMenuJoin newFoodMenuJoin = new FoodMenuJoin(food.getId(), newMenuId);
                            foodMenuJoinDao.insert(newFoodMenuJoin);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.updateRecylerView(newMenuId);
            activity.updateMenuBinding(newMenuId);
        }
    }

}
