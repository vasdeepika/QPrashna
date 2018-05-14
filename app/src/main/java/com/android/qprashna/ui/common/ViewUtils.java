package com.android.qprashna.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.qprashna.R;

import java.io.IOException;

import retrofit2.HttpException;

import static android.content.Context.MODE_PRIVATE;
import static com.android.qprashna.api.ApiUtils.getErrorMessage;
import static com.android.qprashna.api.Constants.MY_PREFS_NAME;

public class ViewUtils {

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

    public static void hideSoftKeyboard(AutoCompleteTextView input, Activity activity) {
        input.setInputType(0);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
        }
    }

    public static void showErrorMessage(Context context, Throwable e) {
        if (e instanceof HttpException) {
            String errorResponse = null;
            try {
                errorResponse = ((HttpException) e).response().errorBody().string();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Toast.makeText(context, getErrorMessage(errorResponse), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, R.string.try_again_text, Toast.LENGTH_LONG).show();
        }
    }

    public static void saveUserIdInSharedPreferences(Context context, int userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("userId", userId);
        editor.apply();
    }

    public static int getUserIdFromSharedPreferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt("userId", 0);
    }

    public static void storeJSessionIdInSharedPreferences(Context context, String sessionId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("sessionId", sessionId);
        editor.apply();
    }

    public static String getJSessionIdInSharedPreferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString("sessionId", "JSESSIONID=0");
    }
}
