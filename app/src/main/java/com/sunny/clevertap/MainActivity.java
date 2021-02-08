package com.sunny.clevertap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        CleverTapAPI cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        cleverTapAPI.createNotificationChannel(getApplicationContext(),"sl2883","Sunny","Description for my channel sunny", NotificationManager.IMPORTANCE_MAX,true);
    }

    /** Called when the user touches the button */
    public void sendMessage(View view) {
        sendProductViewedEvent();
        // Do something in response to button click
        createAlert(this.getResources().getString(R.string.productViewedTitle),
                this.getResources().getString(R.string.productViewedMessage)).show();
    }

    public AlertDialog createAlert(String title, String message) {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message)
                .setTitle(title);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        return builder.create();
    }

    public void sendProductViewedEvent() {
        CleverTapAPI clevertap = CleverTapAPI.getDefaultInstance(getApplicationContext());
        HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
        prodViewedAction.put("Product Name", "CleverTap");
        prodViewedAction.put("Product ID", 1);

        prodViewedAction.put("Product Image", "https://d35fo82fjcw0y8.cloudfront.net/2018/07/26020307/customer-success-clevertap.jpg");
        prodViewedAction.put("Date", new java.util.Date());

        clevertap.pushEvent("Product Viewed", prodViewedAction);

        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("Email", "sl2883@cornell.edu");
        profileUpdate.put("MSG-email", true);                      // Disable email notifications
        profileUpdate.put("MSG-push", true);                        // Enable push notifications

        clevertap.pushProfile(profileUpdate);
    }

}