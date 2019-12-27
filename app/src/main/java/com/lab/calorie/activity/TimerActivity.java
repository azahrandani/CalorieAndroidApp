package com.lab.calorie.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lab.calorie.R;
import com.lab.calorie.broadcastReceiver.StopwatchNotificationPublisher;
import com.lab.calorie.service.TimerService;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private String exercise;
    private int minutes = 1;
    private String calories;
    private Boolean inputDisabled;
    private long timerEndTime;

    private ScrollView scrollView;
    private TextView burnValue;
    private NumberPicker numberPicker;
    private RadioGroup radioGroup;
    private TextView countdownText;
    private Button startButton;
    private Button stopButton;

    private AlarmManager alarmManager;

    private BroadcastReceiver timerBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            exercise = intent.getStringExtra("exercise");
            minutes = intent.getIntExtra("seconds", 0) / 60;

            Boolean isFinish = intent.getBooleanExtra("finish", false);
            updateTheTimer(intent);
            if (isFinish) {
                System.out.println("masuk ke isFinish");
                stopButton.setVisibility(View.INVISIBLE);
                startButton.setBackgroundColor(getResources().getColor(R.color.bmrGreen));
                startButton.setEnabled(true);
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    radioButton.setEnabled(true);
                    radioButton.setTextColor(getResources().getColor(R.color.bmrGreen));
                }

                numberPicker.setValue(1);
                numberPicker.setEnabled(true);
                inputDisabled = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        inputDisabled = false;

        numberPicker = findViewById(R.id.minutes_number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(20);
        numberPicker.setOnValueChangedListener(onValueChangeListener);

        scrollView = findViewById(R.id.timer_scrollview);
        countdownText = findViewById(R.id.countdown_timer);
        stopButton = findViewById(R.id.stop_button);
        burnValue = findViewById(R.id.value_burn);

        radioGroup = findViewById(R.id.exercise_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                updateChosenExercise();
                calculateBurnCalories();
            }
        });
        radioGroup.check(R.id.radio_jumping);
        updateChosenExercise();
        calculateBurnCalories();

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateChosenExercise();
                startTimerService();
                stopButton.setVisibility(View.VISIBLE);
                startButton.setBackgroundColor(getResources().getColor(R.color.bmrGrey));
                startButton.setEnabled(false);
                scrollView.smoothScrollTo(0, 260);
                registerNotification();
            }
        });

        stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimerActivity.this, TimerService.class);
                stopService(intent);
                countdownText.setText("00:00");
                stopButton.setVisibility(View.GONE);
                startButton.setBackgroundColor(getResources().getColor(R.color.bmrGreen));
                startButton.setEnabled(true);
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    radioButton.setEnabled(true);
                    radioButton.setTextColor(getResources().getColor(R.color.bmrGreen));
                }

                numberPicker.setValue(1);
                numberPicker.setEnabled(true);
                inputDisabled = false;
                cancelNotification();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(timerBroadcastReceiver, new IntentFilter(TimerService.COUNTDOWN_SERVICE));
        System.out.println("###Registered broadcast receiver");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(timerBroadcastReceiver);
        System.out.println("###Unregistered broadcast receiver");
    }

    NumberPicker.OnValueChangeListener onValueChangeListener =
            new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    minutes = numberPicker.getValue();
                    System.out.println(minutes + " minutes");
                    calculateBurnCalories();
                }
            };

    private void updateChosenExercise() {
        RadioButton exerciseInput = findViewById(radioGroup.getCheckedRadioButtonId());
        if (exerciseInput != null) exercise = exerciseInput.getText().toString();
        System.out.println("exercise chosen: " + exercise);
    }

    private void calculateBurnCalories() {
        int integerBurnValue = 0;
        if (exercise != null && minutes > 0) {
            if (exercise.equals("Jump rope") || exercise.equals("Lompat tali")) {
                integerBurnValue = 18 * minutes;
            } else if (exercise.equals("Running") || exercise.equals("Lari")) {
                integerBurnValue = 13 * minutes;
            } else {
                integerBurnValue = 6 * minutes;
            }
        }
        String stringBurnValue = String.valueOf(integerBurnValue);
        burnValue.setText(stringBurnValue);
        calories = stringBurnValue;
    }

    private void startTimerService() {
        Intent startIntent = new Intent(this, TimerService.class);
        startIntent.putExtra("seconds", minutes * 60);
        startIntent.putExtra("exercise", exercise);
        startService(startIntent);
        System.out.println("###Started service from TimerActivity.");
    }

    private void updateTheTimer(Intent intent) {
        System.out.println("###Current scrollView Y offset " + scrollView.getScrollY());
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            System.out.println("###From TimerActivity, countdown seconds remaining: " + millisUntilFinished / 1000);
            int minutes = (int) (millisUntilFinished / 1000) / 60;
            int seconds = (int) (millisUntilFinished / 1000) % 60;

            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            countdownText.setText(timeLeftFormatted);
            stopButton.setVisibility(View.VISIBLE);
            startButton.setBackgroundColor(getResources().getColor(R.color.bmrGrey));
            startButton.setEnabled(false);

            if (!inputDisabled) {
                scrollView.scrollTo(0, 260);

                if (exercise.equals("Jump rope") || exercise.equals("Lompat tali")) {
                    radioGroup.check(R.id.radio_jumping);
                } else if (exercise.equals("Running") || exercise.equals("Lari")) {
                    radioGroup.check(R.id.radio_running);
                } else {
                    radioGroup.check(R.id.radio_cycling);
                }

                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    radioButton.setEnabled(false);
                    radioButton.setTextColor(getResources().getColor(R.color.bmrGrey));
                }

                numberPicker.setValue(this.minutes);
                System.out.println("###minutes yg diset adalah " + this.minutes);
                numberPicker.setEnabled(false);

                inputDisabled = true;
            }

        }
    }

    private void registerNotification() {
        Intent notificationIntent = new Intent(this, StopwatchNotificationPublisher.class);
        notificationIntent.putExtra("exercise", exercise);
        notificationIntent.putExtra("minutes", minutes);
        notificationIntent.putExtra("calories", calories);

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        int millis = minutes * 60 * 1000;
        timerEndTime = System.currentTimeMillis() + millis;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timerEndTime, broadcast);
    }

    private void cancelNotification() {
        Intent notificationIntent = new Intent(this, StopwatchNotificationPublisher.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(broadcast);
        System.out.println("###Cancelled notification");
    }

}
