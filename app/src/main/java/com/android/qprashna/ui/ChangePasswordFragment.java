package com.android.qprashna.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.qprashna.R;
import com.android.qprashna.api.ChangePasswordResponse;
import com.android.qprashna.ui.common.TranslucentProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.api.ApiUtils.updatePasswordRequestBody;
import static com.android.qprashna.ui.common.ViewUtils.getJSessionIdInSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.getUserIdFromSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.isThereInternetConnection;
import static com.android.qprashna.ui.common.ViewUtils.showErrorMessage;

public class ChangePasswordFragment extends Fragment {

    @BindView(R.id.current_password)
    TextInputLayout mCurrentPassword;

    @BindView(R.id.new_password)
    TextInputLayout mNewPassword;

    @BindView(R.id.confirm_password)
    TextInputLayout mConfirmPassword;

    @BindView(R.id.change_password)
    Button saveChanges;
    private TranslucentProgressBar mProgressBar;
    private DisposableObserver mDisposable;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);

        ButterKnife.bind(this, rootView);
        mProgressBar = TranslucentProgressBar.getInstance();
        
        saveChanges.setOnClickListener(v -> changePassword(mCurrentPassword.getEditText().getText().toString(), mNewPassword.getEditText().getText().toString(), mConfirmPassword.getEditText().getText().toString()));
        return rootView;
    }

    private void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        if(getActivity()!=null) {
            if (isThereInternetConnection(getActivity())) {
                mProgressBar.showProgress(getActivity());
                Observable<ChangePasswordResponse> responseObservable = getApiService().updatePassword(getJSessionIdInSharedPreferences(getActivity()), updatePasswordRequestBody(getUserIdFromSharedPreferences(getActivity()),
                        currentPassword,newPassword));

                mDisposable = responseObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<ChangePasswordResponse>() {
                            @Override
                            public void onNext(ChangePasswordResponse changePasswordResponse) {
                                mProgressBar.unShowProgress();
                                if (changePasswordResponse != null && changePasswordResponse.getResult().equalsIgnoreCase("Success")) {
                                    Toast.makeText(getActivity(), R.string.password_updated_successfully, Toast.LENGTH_LONG).show();
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
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
