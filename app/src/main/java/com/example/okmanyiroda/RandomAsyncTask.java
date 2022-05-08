package com.example.okmanyiroda;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class RandomAsyncTask extends AsyncTask<Void, Void, String> {

    private WeakReference<TextView> mTextView;
    public RandomAsyncTask(TextView textView) {
        mTextView = new WeakReference<>(textView);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random r = new Random();
        int n = r.nextInt(11);
        int ms = n * 500;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Guest" + ms + " ms után!";
    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}
