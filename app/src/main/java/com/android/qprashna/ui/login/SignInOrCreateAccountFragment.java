package com.android.qprashna.ui.login;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import com.android.qprashna.data.QprashnaContract;
import com.android.qprashna.ui.common.TranslucentProgressBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.api.ApiUtils.getCreateAccountRequestBody;
import static com.android.qprashna.api.ApiUtils.getErrorMessage;
import static com.android.qprashna.api.ApiUtils.getLoginRequestBody;
import static com.android.qprashna.ui.common.ViewUtils.hideKeyboard;
import static com.android.qprashna.ui.common.ViewUtils.isThereInternetConnection;
import static com.android.qprashna.ui.common.ViewUtils.saveUserIdInSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.showErrorMessage;
import static com.android.qprashna.ui.common.ViewUtils.storeJSessionIdInSharedPreferences;


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
    private LoginResponse mLoginResponse;
    public static final String TAG = SignInOrCreateAccountFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in_create_account, container, false);

        ButterKnife.bind(this, rootView);
        if (getActivity() != null) {
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
            setRXJavaErrorHandling();
            hideKeyboard(getActivity());
        }
        return rootView;
    }

    private void setRXJavaErrorHandling() {
        // RXJava 2 error handling
        RxJavaPlugins.setErrorHandler(e -> {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    mProgressBar.unShowProgress();
                    Toast.makeText(getActivity(), R.string.try_again_text, Toast.LENGTH_LONG).show();
                });

                Log.w(TAG, "Exception Occurred: " + e.getMessage());

                if (mDisposable != null) {
                    mDisposable.dispose();
                }
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
                if (isNewUser() && mViewPager.getAdapter() != null) {
                    mViewPager.getAdapter().notifyDataSetChanged();
                    mViewPager.setCurrentItem(0);
                } else if (mViewPager.getAdapter() != null) {
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
        loginButton.setOnClickListener(view -> {
            mProgressBar.showProgress(getContext());
            if (isNewUser() && loginButton.getText().toString().equals(getResources().getString(R.string.sign_up_text))) {
                createAccountCall(userNameEntry.getText().toString(),
                        passwordEntry.getText().toString(),
                        firstNameLayout.getEditText().getText().toString(),
                        lastNameLayout.getEditText().getText().toString(),
                        emailAddressLayout.getEditText().getText().toString());
            } else if (!isNewUser() && loginButton.getText().toString().equals(getResources().getString(R.string.login_text))) {
                loginCall(userNameEntry.getText().toString(), passwordEntry.getText().toString());
            }
        });
    }

    private void createAccountCall(String userName, String password, String firstName, String lastName, String emailAddress) {
        if (getActivity() == null) {
            return;
        }
        if (isThereInternetConnection(getActivity())) {
            Observable<Response<LoginResponse>> loginResponseObservable = getApiService().createAccount(getCreateAccountRequestBody(userName, password, firstName, lastName, emailAddress));
            mDisposable = loginResponseObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Response<LoginResponse>>() {
                        @Override
                        public void onNext(Response<LoginResponse> loginResponse) {
                            if(loginResponse.headers() != null && loginResponse.headers().get("Set-Cookie") != null) {
                                String jSessionId = loginResponse.headers().get("Set-Cookie").split(";", 2)[0];
                                storeJSessionIdInSharedPreferences(getActivity(), jSessionId);
                            }
                            mProgressBar.unShowProgress();
                            if (loginResponse.body() != null) {
                                mLoginResponse = loginResponse.body();
                                if (mLoginResponse.getStatus().equals(getString(R.string.verify_pending_status))) {
                                    Toast.makeText(getActivity(), R.string.activate_account_text, Toast.LENGTH_LONG).show();
                                } else if (mLoginResponse.getStatus().equalsIgnoreCase(getString(R.string.account_active_status))) {
                                    saveUserIdInSharedPreferences(getActivity(), mLoginResponse.getId());
                                    saveProfileInfo();
                                    ((SignInCreateAccountActivity) getActivity()).launchMainActivity();
                                }
                            } else if (loginResponse.errorBody() != null) {
                                try {
                                    Toast.makeText(getActivity(), getErrorMessage(loginResponse.errorBody().string()), Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            // show error message if api call throws an error
                            mProgressBar.unShowProgress();
                            showErrorMessage(getActivity(), e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private void saveProfileInfo() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QprashnaContract.ProfileEntry.USERID, mLoginResponse.getId());
        contentValues.put(QprashnaContract.ProfileEntry.FIRSTNAME, mLoginResponse.getFirstName());
        contentValues.put(QprashnaContract.ProfileEntry.LASTNAME, mLoginResponse.getLastName());
        contentValues.put(QprashnaContract.ProfileEntry.EMAIL, mLoginResponse.getEmail());
        contentValues.put(QprashnaContract.ProfileEntry.PROFILEPIC, mLoginResponse.getProfilePicURL());
        contentValues.put(QprashnaContract.ProfileEntry.DESIGNATION, mLoginResponse.getDesignation());
        contentValues.put(QprashnaContract.ProfileEntry.DOB, mLoginResponse.getDateOfBirth());
        contentValues.put(QprashnaContract.ProfileEntry.GENDER, mLoginResponse.getGender());
        contentValues.put(QprashnaContract.ProfileEntry.COUNTRY, mLoginResponse.getCountry());
        contentValues.put(QprashnaContract.ProfileEntry.STATE, mLoginResponse.getState());


        // Insert the content values via a ContentResolver
        getActivity().getContentResolver().insert(QprashnaContract.ProfileEntry.CONTENT_URI, contentValues);
    }

    private void loginCall(String userName, String password) {
        if (getActivity() == null) {
            return;
        }
        if (isThereInternetConnection(getActivity())) {
            Observable<Response<LoginResponse>> loginResponseObservable = getApiService().login(getLoginRequestBody(userName, password));
            mDisposable = loginResponseObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Response<LoginResponse>>() {

                        @Override
                        public void onNext(Response<LoginResponse> loginResponse) {
                            mProgressBar.unShowProgress();
                            if (loginResponse != null) {
                                if(loginResponse.headers() != null && loginResponse.headers().get("Set-Cookie") != null) {
                                    String jSessionId = loginResponse.headers().get("Set-Cookie").split(";", 2)[0];
                                    storeJSessionIdInSharedPreferences(getActivity(), jSessionId);
                                }
                                if (loginResponse.body() != null) {
                                    mLoginResponse = loginResponse.body();
                                    if (mLoginResponse.getStatus().equalsIgnoreCase(getString(R.string.verify_pending_status))) {
                                        Toast.makeText(getActivity(), R.string.activate_account_text, Toast.LENGTH_LONG).show();
                                    } else if (mLoginResponse.getStatus().equalsIgnoreCase(getString(R.string.account_active_status))) {
                                        saveUserIdInSharedPreferences(getActivity(), mLoginResponse.getId());
                                        saveProfileInfo();
                                        ((SignInCreateAccountActivity) getActivity()).launchMainActivity();
                                    }
                                } else if (loginResponse.errorBody() != null) {
                                    try {
                                        Toast.makeText(getActivity(), getErrorMessage(loginResponse.errorBody().string()), Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            // show error message if api call throws an error
                            mProgressBar.unShowProgress();
                            showErrorMessage(getActivity(), e);
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
