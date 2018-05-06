package com.android.qprashna.ui;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.qprashna.R;

public class ViewUtilsClass {

    public static boolean isThereInternetConnection(Activity activity) {
        boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        if (!isConnected) {
            TranslucentProgressBar.getInstance().unShowProgress();
            Toast.makeText(activity, R.string.network_error, Toast.LENGTH_LONG).show();
        }

        return isConnected;
    }
}
