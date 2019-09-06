package com.example.kingominho;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;


public class DetectConnection {


    public static boolean checkInternetConnection(@NonNull Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean value = connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected();

        return value;
    }
}
