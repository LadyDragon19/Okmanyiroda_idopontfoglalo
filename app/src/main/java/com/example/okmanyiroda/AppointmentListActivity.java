 package com.example.okmanyiroda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AppointmentListActivity extends AppCompatActivity {
    private static final String LOG_TAG = AppointmentActivity.class.getName();
    private static final String PREF_KEY = AppointmentActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 42;

    private int gridNumber = 1 ;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private RecyclerView mRecyclerView;
    private ArrayList<AppointmentItem> mAppointmentList;
    private AppointmentItemAdapter mAdapter;

    private FirebaseFirestore mFirestore;

    private NotificationHandler mNotificationHandler;

    private CollectionReference mItems;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        mAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null ) {
            Log.d(LOG_TAG, "Authenticated user.");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user.");
            finish();
        }

        mRecyclerView = findViewById(R.id.lista);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mAppointmentList = new ArrayList<>();
        mAdapter = new AppointmentItemAdapter(this, mAppointmentList);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");
        queryData();

        mNotificationHandler = new NotificationHandler(this);
    }

    private void queryData() {
        mAppointmentList.clear();
        mItems.limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                AppointmentItem item = document.toObject(AppointmentItem.class);
                item.setId(document.getId());
                mAppointmentList.add(item);
            }

            if (mAppointmentList.size() == 0) {
                initializeData();
                queryData();
            }

            // Notify the adapter of the change.
            mAdapter.notifyDataSetChanged();
        });
    }

    private void initializeData() {
        String[] itemsEmail = getResources()
                .getStringArray(R.array.appo_emailcim);
        String[] itemsTipus= getResources()
                .getStringArray(R.array.appo_igazolvany_tipus);
        String[] itemsIdo = getResources()
                .getStringArray(R.array.appo_idopont);

        for (int i = 0; i < itemsEmail.length; i++) {
            mItems.add(new AppointmentItem(
                    itemsEmail[i],
                    itemsTipus[i],
                    itemsIdo[i]
                    ));
        }
    }

    public void deleteItem(AppointmentItem item) {
        DocumentReference ref = mItems.document(item._getID());

        ref.delete().addOnSuccessListener(success -> {
            Log.d(LOG_TAG, "Sikeres törlés: "+ item._getID());
        })
        .addOnFailureListener(failure -> {
          Toast.makeText(this, "Ez az item nem törölhető: " + item._getID(), Toast.LENGTH_LONG).show();
        });
    }
//
//    private void updateItem(AppointmentItem item) {
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.appointment_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out_button:
                Log.d(LOG_TAG, "Logout clicked!");
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startMakeAnAppointment(){
        Intent intent = new Intent(this, AppointmentActivity.class);
        startActivity(intent);
    }


    public void appointment(View view) {
        //TODO : átadni neki az emailt és a jelszót!
//        String userName = userNameET.getText().toString();
//        String pwd = pwdET.getText().toString();

        mNotificationHandler.send("Foglalás");

        startMakeAnAppointment();
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