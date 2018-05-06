package com.android.qprashna.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.qprashna.R;
import com.android.qprashna.api.LoginResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.api.ApiUtils.getErrorMessage;
import static com.android.qprashna.api.ApiUtils.getLoginRequestBody;
import static com.android.qprashna.ui.ViewUtilsClass.isThereInternetConnection;


/**
 * A placeholder fragment containing a simple view.
 */
public class SignInOrCreateAccountFragment extends Fragment {

    @BindView(R.id.not_registered_text)
    TextView notRegisteredTextView;

    @BindView(R.id.login_button)
    Button loginButton;

    @BindView(R.id.input_layout_first_name)
    TextInputLayout firstNameLayout;

    @BindView(R.id.input_layout_last_name)
    TextInputLayout lastNameLayout;

    @BindView(R.id.input_layout_email)
    TextInputLayout emailAddressLayout;

    @BindView(R.id.user_name_entry)
    EditText userNameEntry;

    @BindView(R.id.password_entry)
    EditText passwordEntry;

    private ViewPager mViewPager;
    TranslucentProgressBar mProgressBar;


    public static final String USER_TYPE = "user_type";
    private Disposable mDisposable;
    public static final String TAG = SignInOrCreateAccountFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in_create_account, container, false);

        ButterKnife.bind(this, rootView);
        mViewPager = getActivity().findViewById(R.id.viewPager);
        mProgressBar = TranslucentProgressBar.getInstance();

        if (isNewUser()) {
            if (firstNameLayout.getVisibility() != View.VISIBLE)
                firstNameLayout.setVisibility(View.VISIBLE);
            if (lastNameLayout.getVisibility() != View.VISIBLE)
                lastNameLayout.setVisibility(View.VISIBLE);
            if (emailAddressLayout.getVisibility() != View.VISIBLE)
                emailAddressLayout.setVisibility(View.VISIBLE);
            loginButton.setText(getResources().getString(R.string.sign_up_text));
        }
        setNotRegisteredSpannableString();
        setLoginButtonOnClickListener();
//        setRXJavaErrorHandling();
        return rootView;
    }

    private void setRXJavaErrorHandling() {
        // RXJava 2 error handling
        RxJavaPlugins.setErrorHandler(e -> {
            getActivity().runOnUiThread(() -> {
                mProgressBar.unShowProgress();
                Toast.makeText(getActivity(), R.string.try_again_text, Toast.LENGTH_LONG).show();
            });

            Log.w(TAG, "Exception Occurred: " + e.getMessage());

            if (mDisposable != null) {
                mDisposable.dispose();
            }
        });
    }

    private void setNotRegisteredSpannableString() {
        String text;
        if (isNewUser()) {
            text = getResources().getString(R.string.registered_signin_now);

        } else {
            text = getResources().getString(R.string.not_registered_signup_now);
        }
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (isNewUser() && mViewPager.getAdapter()!=null) {
                    mViewPager.getAdapter().notifyDataSetChanged();
                    mViewPager.setCurrentItem(0);
                } else if (mViewPager.getAdapter()!=null){
                    mViewPager.getAdapter().notifyDataSetChanged();
                    mViewPager.setCurrentItem(1);
                }
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, text.indexOf('S'), text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.BLUE);
        ss.setSpan(fcs, text.indexOf('S'), text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        notRegisteredTextView.setText(ss);
        notRegisteredTextView.setMovementMethod(LinkMovementMethod.getInstance());
        notRegisteredTextView.setHighlightColor(Color.TRANSPARENT);
    }

    private void setLoginButtonOnClickListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.showProgress(getContext());
                loginCall(userNameEntry.getText().toString(), passwordEntry.getText().toString());

            }
        });
    }

    private void loginCall(String userName, String password) {
        if (getActivity() == null){
           return;
        }
        if(isThereInternetConnection(getActivity())) {
            Observable<LoginResponse> loginResponseObservable = getApiService().login(getLoginRequestBody(userName, password));
            mDisposable = loginResponseObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<LoginResponse>() {
                        @Override
                        public void onNext(LoginResponse loginResponse) {
                            mProgressBar.unShowProgress();
                            if (loginResponse != null) {


                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            // show error message if api call throws an error
                            mProgressBar.unShowProgress();
                            if (e instanceof HttpException) {
                                String errorResponse = null;
                                try {
                                    errorResponse = ((HttpException) e).response().errorBody().string();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                Toast.makeText(getActivity(), getErrorMessage(errorResponse), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), R.string.try_again_text, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private boolean isNewUser() {
        return ((getArguments() != null) && (getArguments().getString(USER_TYPE) != null) && (getArguments().getString(USER_TYPE).equals(UserTypes.NEW.toString())));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}