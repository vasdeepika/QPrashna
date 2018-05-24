package com.android.qprashna.ui;

import android.content.ContentValues;
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
import com.android.qprashna.api.ProfileDataObject;
import com.android.qprashna.data.QprashnaContract;
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
    public ProfileDataObject mProfileDataObject;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, rootView);
        mProgressBar = TranslucentProgressBar.getInstance();
        if (getArguments() != null) {
            mProfileDataObject = Parcels.unwrap(getArguments().getParcelable(ProfileDataObject.KEY));
        }

        if (mProfileDataObject != null) {
            firstNameLayout.getEditText().setText(mProfileDataObject.getFirstName());
            lastNameLayout.getEditText().setText(mProfileDataObject.getLastName());
            emailAddressLayout.getEditText().setText(mProfileDataObject.getEmail());
            genderLayout.getEditText().setText(mProfileDataObject.getGender());
            designationLayout.getEditText().setText(mProfileDataObject.getDesignation());
            countryLayout.getEditText().setText(mProfileDataObject.getCountry());
            stateLayout.getEditText().setText(mProfileDataObject.getState());

            long val = mProfileDataObject.getDob();
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
        if (getActivity() != null) {
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
                                    updateProfileData();
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

    private void updateProfileData() {
        // update profile via  a ContentResolver

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

        getActivity().getContentResolver().update(QprashnaContract.ProfileEntry.CONTENT_URI, contentValues, null, null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
