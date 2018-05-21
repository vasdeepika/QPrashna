package com.android.qprashna.ui.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.android.qprashna.R;

public class TranslucentProgressBar {

    @SuppressLint("StaticFieldLeak")
    private static TranslucentProgressBar mProgressBar;
    private Dialog pd;
    private RelativeLayout layout;

    private TranslucentProgressBar() {
    }

    public static TranslucentProgressBar getInstance() {
        if (mProgressBar == null) {
            mProgressBar = new TranslucentProgressBar();
        }
        return mProgressBar;
    }

    public void showProgress(Context context) {

        pd = new Dialog(context, android.R.style.Theme_Black);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_progress_bar_layout, null);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (pd.getWindow() != null) {
            pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
        }
        pd.setContentView(view);
        pd.show();
    }

    public void unShowProgress() {
        if (pd != null && pd.isShowing()) {
            pd.cancel();
        }
    }
}
