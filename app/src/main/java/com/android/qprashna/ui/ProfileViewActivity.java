package com.android.qprashna.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.qprashna.R;
import com.android.qprashna.api.UserResult;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.api.ApiUtils.isFollowingRequestBody;
import static com.android.qprashna.ui.common.ViewUtils.getUserIdFromSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.isThereInternetConnection;
import static com.android.qprashna.ui.common.ViewUtils.showErrorMessage;

public class ProfileViewActivity extends AppCompatActivity {

    UserResult mUserResult;
    private DisposableObserver<String> mDisposable;
    boolean isFollowing;

    @BindView(R.id.askBtn)
    Button askBtn;

    @BindView(R.id.follow_btn)
    Button followButton;

    @BindView(R.id.question)
    EditText question;

    @BindView(R.id.profile_user_name)
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        ButterKnife.bind(this);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle!= null && bundle.containsKey(UserResult.PROFILE)) {
            mUserResult = Parcels.unwrap(
                    bundle.getParcelable(UserResult.PROFILE));

            name.setText(String.format(getString(R.string.profile_user_name),mUserResult.getFirstName(),mUserResult.getLastName()).replaceAll("\\s", "\\n"));

            ImageView profileImage = findViewById(R.id.profile_image);
            Picasso.with(this)
                    .load("https://qprashna.com/images/" + mUserResult.getProfilePicURL())
                    .placeholder(R.drawable.placeholder)
                    .resize(450, 400)
                    .into(profileImage);

            isUserFollowing();
        }
    }

    public void isUserFollowing() {
        if (isThereInternetConnection(this)) {
            Observable<String> isFollowingResponseObservable = getApiService().isFollowing(isFollowingRequestBody(getUserIdFromSharedPreferences(this), mUserResult.getId()));
            mDisposable = isFollowingResponseObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<String>() {
                        @Override
                        public void onNext(String isUserFollowing) {
                            if (isUserFollowing != null) {
                                isFollowing = Boolean.valueOf(isUserFollowing);
                                if(isFollowing) {
                                    followButton.setText(R.string.unfollow_txt);
                                    followButton.setBackgroundColor(getResources().getColor(R.color.colorRed));
                                    followButton.setVisibility(View.VISIBLE);
                                } else {
                                    followButton.setText(R.string.follow_txt);
                                    followButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                    followButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    followButton.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            // show error message if api call throws an error
                            showErrorMessage(getBaseContext(), e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
