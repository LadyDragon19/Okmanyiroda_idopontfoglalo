package com.example.okmanyiroda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AppointmentActivity extends AppCompatActivity {
    private static final String LOG_TAG = AppointmentActivity.class.getName();
    private static final String PREF_KEY = AppointmentActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 42;


    RadioGroup igazolvanyRG;
    DatePicker dateDP;
    TimePicker timeTP;
    Button nextToTimeButton;

    private FirebaseUser user;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);


        igazolvanyRG = findViewById(R.id.igazolvanyRadioG);
        igazolvanyRG.check(R.id.szemelyiButton);
        dateDP = findViewById(R.id.idopontDatePicker);
        timeTP = findViewById(R.id.idopontTimePicker);
        timeTP.setIs24HourView(true);

        nextToTimeButton = findViewById(R.id.foglalasButton);

    }

    public void next(View view) {
        Toast.makeText(AppointmentActivity.this, "Sikeres foglalás!", Toast.LENGTH_LONG).show();



        int checkedId = igazolvanyRG.getCheckedRadioButtonId();
        RadioButton radioButton = igazolvanyRG.findViewById(checkedId);
        String igazolvany = radioButton.getText().toString();
        String idopont = ("Dátum: " + dateDP.getYear() + "/" + dateDP.getMonth() + "/"
                            + dateDP.getDayOfMonth() + " "+ timeTP.getHour() +":"+ timeTP.getMinute());

        Log.i(LOG_TAG, "Igazolvány típus: " + igazolvany + ", időpont: " + idopont);

        startAppointmentListActivity();
        //TODO: AppointmentList-be menni, és visszaadni neki: e-mail, jelszó, ig.típus date, time

    }


    private void startAppointmentListActivity(){
        Intent intent = new Intent(this, AppointmentListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }


}