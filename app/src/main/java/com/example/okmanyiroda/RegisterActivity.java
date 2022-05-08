package com.example.okmanyiroda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 42;


    EditText userNameET;
    EditText phoneET;
    EditText emailET;
    EditText pwdET;
    EditText pwdAgainET;
    RadioGroup lakcimGroup;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != 42) {
            finish();
        }

        userNameET = findViewById(R.id.editTextUser);
        phoneET = findViewById(R.id.editTextMobil);
        emailET = findViewById(R.id.editTextEmail);
        pwdET = findViewById(R.id.editTextPassword);
        pwdAgainET = findViewById(R.id.editTextPwdUjra);
        lakcimGroup = findViewById(R.id.RadioLakcim);
        lakcimGroup.check(R.id.buttonLakcim);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("userName", "");
        String pwd = preferences.getString("pwd", "");

        emailET.setText(email);
        pwdET.setText(pwd);

        mAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");
    }

    public void register(View view) {
        String userName = userNameET.getText().toString();
        String phone = phoneET.getText().toString();
        String email = emailET.getText().toString();
        String pwd = pwdET.getText().toString();
        String pwdAgain = pwdAgainET.getText().toString();

        if (!pwd.equals(pwdAgain)){
            Log.e(LOG_TAG, "Nem egyezik a két jelszó!");
            return;
        }

        int checkedId = lakcimGroup.getCheckedRadioButtonId();
        RadioButton radioButton = lakcimGroup.findViewById(checkedId);
        String lakcim = radioButton.getText().toString();

        Log.i(LOG_TAG, "Regisztrált: " + userName + ", E-mail cím: " + email + ", jelszó: " + pwd);
       // startMakeAnAppointment();

        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "Sikeres felhasználó létrehozás");
                    Toast.makeText(RegisterActivity.this, "Sikeres felhasználó létrehozás", Toast.LENGTH_LONG).show();
                    startMakeAnAppointment();
                } else {
                    Log.d(LOG_TAG, "Sikertelen felhasználó létrehozás");
                    Toast.makeText(RegisterActivity.this, "Sikertelen felhasználó létrehozás: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view) {
        finish();
    }

    private void startMakeAnAppointment(){
        Intent intent = new Intent(this, AppointmentActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //TODO
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //TODO
    }

}