package com.android.qprashna.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.qprashna.R;
import com.android.qprashna.api.LoginResponse;
import com.android.qprashna.ui.common.TranslucentProgressBar;
import com.android.qprashna.ui.feeds.MainActivity;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.api.ApiUtils.updateProfileRequestBody;
import static com.android.qprashna.ui.common.ViewUtils.getJSessionIdInSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.getUserIdFromSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.isThereInternetConnection;
import static com.android.qprashna.ui.common.ViewUtils.showErrorMessage;

public class EditProfileFragment extends Fragment {

    public LoginResponse mLoginResponse;
    TranslucentProgressBar mProgressBar;

    @BindView(R.id.edit_profile_layout_first_name)
    TextInputLayout firstNameLayout;

    @BindView(R.id.edit_profile_layout_last_name)
    TextInputLayout lastNameLayout;

    @BindView(R.id.edit_profile_layout_email)
    TextInputLayout emailAddressLayout;

    @BindView(R.id.edit_profile_layout_country)
    TextInputLayout countryLayout;

    @BindView(R.id.edit_profile_layout_state)
    TextInputLayout stateLayout;

    @BindView(R.id.edit_profile_layout_dob)
    TextInputLayout dobLayout;

    @BindView(R.id.edit_profile_layout_gender)
    TextInputLayout genderLayout;

    @BindView(R.id.edit_profile_designation)
    TextInputLayout designationLayout;

    @BindView(R.id.save_changes)
    Button saveChanges;
    private DisposableObserver mDisposable;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLoginResponse = Parcels.unwrap(getArguments().getParcelable(LoginResponse.KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, rootView);
        mProgressBar = TranslucentProgressBar.getInstance();

        if (mLoginResponse != null) {
            firstNameLayout.getEditText().setText(mLoginResponse.getFirstName());
            lastNameLayout.getEditText().setText(mLoginResponse.getLastName());
            emailAddressLayout.getEditText().setText(mLoginResponse.getEmail());
            genderLayout.getEditText().setText(mLoginResponse.getGender());
            designationLayout.getEditText().setText(mLoginResponse.getDesignation());
            countryLayout.getEditText().setText(mLoginResponse.getCountry());
            stateLayout.getEditText().setText(mLoginResponse.getState());

            long val = mLoginResponse.getDateOfBirth();
            Date date = new Date(val);

            SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            String dateText = df2.format(date);
            dobLayout.getEditText().setText(dateText);

            SimpleDateFormat requestFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            final String requestDOBDate = requestFormat.format(date);

            saveChanges.setOnClickListener(v -> updateProfile(firstNameLayout.getEditText().getText().toString(), lastNameLayout.getEditText().getText().toString(), genderLayout.getEditText().getText().toString(), designationLayout.getEditText().getText().toString(),
                    stateLayout.getEditText().getText().toString(), countryLayout.getEditText().getText().toString(), requestDOBDate, emailAddressLayout.getEditText().getText().toString()));
        }
        return rootView;
    }

    private void updateProfile(String firstName, String lastName, String gender, String designation, String state, String country, String dob, String email) {
        if(getActivity()!=null) {
            if (isThereInternetConnection(getActivity())) {
                mProgressBar.showProgress(getActivity());
                Observable<LoginResponse> responseObservable = getApiService().updateProfile(getJSessionIdInSharedPreferences(getActivity()), updateProfileRequestBody(getUserIdFromSharedPreferences(getActivity()),
                        firstName, lastName, gender, designation, state, country, dob, email));

                mDisposable = responseObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<LoginResponse>() {
                            @Override
                            public void onNext(LoginResponse loginResponse) {
                                mProgressBar.unShowProgress();
                                if (loginResponse != null) {
                                    mLoginResponse = loginResponse;
                                    ((MainActivity) getActivity()).setLoginResponse(mLoginResponse);
                                    Toast.makeText(getActivity(), R.string.profile_updated_text, Toast.LENGTH_LONG).show();
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
    public void onDetach() {
        super.onDetach();
    }
}
