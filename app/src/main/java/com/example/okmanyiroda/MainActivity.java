package com.example.okmanyiroda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 42;

    EditText userNameET;
    EditText pwdET;


    private SharedPreferences preferences;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameET = findViewById(R.id.editTextUsername);
        pwdET = findViewById(R.id.editTextPwd);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        getSupportLoaderManager().restartLoader(0, null, this);

        Log.i(LOG_TAG, "onCreate");
    }

    public void login(View view) {
        String userName = userNameET.getText().toString();
        String pwd = pwdET.getText().toString();


        if (userName.isEmpty()) {
            Log.d(LOG_TAG, "Hiányzó e-mail cím");
            Toast.makeText(MainActivity.this, "Hiányzó e-mail cím ", Toast.LENGTH_LONG).show();
        } else if(pwd.isEmpty()) {
            Log.d(LOG_TAG, "Hiányzó jelszó");
            Toast.makeText(MainActivity.this, "Hiányzó jelszó", Toast.LENGTH_LONG).show();
        } else {

            mAuth.signInWithEmailAndPassword(userName, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Log.d(LOG_TAG, "Sikeres bejelentkezés");
                        Toast.makeText(MainActivity.this, "Sikeres bejelentkezés", Toast.LENGTH_LONG).show();
                        startAppointmentList();

                    } else {
                        Log.d(LOG_TAG, "Sikertelen bejelentkezés");
                        Toast.makeText(MainActivity.this, "Sikertelen bejelentkezés: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            Log.i(LOG_TAG, "Bejelentkezett: " + userName + ", jelszó: " + pwd);

        }

    }


    private void startAppointmentList(){
        Intent intent = new Intent(this, AppointmentListActivity.class);
        startActivity(intent);
    }

//    private void loginAsGuest(){
//        Intent intent = new Intent(this, GuestActivity.class);
//        startActivity(intent);
//    }

//    public void loginAsGuest(View view) {
//        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull  Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Log.d(LOG_TAG, "Sikeres vendég bejelentkezés");
//                    loginAsGuest();
//
//                } else {
//                    Log.d(LOG_TAG, "Sikertelen bejelentkezés");
//                    Toast.makeText(MainActivity.this, "Sikertelen bejelentkezés: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }

    public void loginAsGuest(View view) {
        mAuth.signInAnonymously().addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Log.d(LOG_TAG, "Anonym user loged in successfully");
                startAppointmentList();
            } else {
                Log.d(LOG_TAG, "Anonym user log in fail");
                Toast.makeText(MainActivity.this, "User log in fail: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", 42);
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
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString( "userName", userNameET.getText().toString() );
        editor.putString( "pwd", pwdET.getText().toString() );
        editor.apply();

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

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new RandomAsyncLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Button anonym = findViewById(R.id.buttonGuest);
        anonym.setText(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}