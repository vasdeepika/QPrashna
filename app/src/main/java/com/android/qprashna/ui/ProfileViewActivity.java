package com.android.qprashna.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.qprashna.R;
import com.android.qprashna.api.AskQuestionResponse;
import com.android.qprashna.api.FollowUserResponse;
import com.android.qprashna.api.ProfileDataObject;
import com.android.qprashna.api.RemoveFolloweeResponse;
import com.android.qprashna.api.UserResult;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.android.qprashna.api.ApiUtils.askQuestionRequestBody;
import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.api.ApiUtils.getFollowUserRequestBody;
import static com.android.qprashna.api.ApiUtils.isFollowingRequestBody;
import static com.android.qprashna.ui.common.ViewUtils.getJSessionIdInSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.getUserIdFromSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.isThereInternetConnection;
import static com.android.qprashna.ui.common.ViewUtils.showErrorMessage;

public class ProfileViewActivity extends AppCompatActivity {

    ProfileDataObject mProfileDataObject;
    private DisposableObserver mDisposable;
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

        //Enable up button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(UserResult.PROFILE)) {
            mProfileDataObject = Parcels.unwrap(
                    bundle.getParcelable(UserResult.PROFILE));

            name.setText(String.format(getString(R.string.profile_user_name), mProfileDataObject.getFirstName(), mProfileDataObject.getLastName()).replaceAll("\\s+", System.getProperty("line.separator")));

            ImageView profileImage = findViewById(R.id.profile_image);
            Picasso.with(this)
                    .load("https://qprashna.com/images/" + mProfileDataObject.getProfilePicURL())
                    .placeholder(R.drawable.placeholder)
                    .resize(450, 400)
                    .into(profileImage);

            isUserFollowing();
            setUpAskQuestion();
        }
    }

    private void setUpAskQuestion() {
        askBtn.setOnClickListener(v -> {
            if (question.getText().length() > 0) {
                if (isThereInternetConnection(ProfileViewActivity.this)) {
                    Observable<AskQuestionResponse> askQuestionResponseObservable = getApiService().askQuestion(getJSessionIdInSharedPreferences(ProfileViewActivity.this), askQuestionRequestBody(getUserIdFromSharedPreferences(ProfileViewActivity.this), mProfileDataObject.getUserId(), question.getText().toString()));
                    mDisposable = askQuestionResponseObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<AskQuestionResponse>() {
                                @Override
                                public void onNext(AskQuestionResponse askQuestionResponse) {
                                    if (askQuestionResponse != null && askQuestionResponse.getFeedQuestionText().equals(question.getText().toString())) {
                                        Toast.makeText(getBaseContext(), "Your question submitted successfully!", Toast.LENGTH_LONG).show();
                                        question.setText("");
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
        });

    }

    public void isUserFollowing() {
        if (isThereInternetConnection(this)) {
            Observable<String> isFollowingResponseObservable = getApiService().isFollowing(isFollowingRequestBody(getUserIdFromSharedPreferences(this), mProfileDataObject.getUserId()));
            mDisposable = isFollowingResponseObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<String>() {
                        @Override
                        public void onNext(String isUserFollowing) {
                            if (isUserFollowing != null) {
                                isFollowing = Boolean.valueOf(isUserFollowing);
                                if (isFollowing) {
                                    followButton.setText(R.string.unfollow_txt);
                                    followButton.setBackgroundColor(getResources().getColor(R.color.colorRed));
                                    followButton.setTextColor(getResources().getColor(R.color.colorWhite));
                                    followButton.setVisibility(View.VISIBLE);
                                } else {
                                    followButton.setText(R.string.follow_txt);
                                    followButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                    followButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    followButton.setVisibility(View.VISIBLE);
                                }
                                setUpFollowBtn();
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

    private void setUpFollowBtn() {
        followButton.setOnClickListener(v -> {
            if (followButton.getText().equals(getResources().getString(R.string.follow_txt))) {
                followUser();
            } else if (followButton.getText().equals(getResources().getString(R.string.unfollow_txt))) {
                unFollowUser();
            }
        });
    }

    private void unFollowUser() {
        if (isThereInternetConnection(this)) {
            Observable<RemoveFolloweeResponse> isFollowingResponseObservable = getApiService().unFollowUser(getJSessionIdInSharedPreferences(this), getFollowUserRequestBody(getUserIdFromSharedPreferences(this), mProfileDataObject.getUserId()));
            mDisposable = isFollowingResponseObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<RemoveFolloweeResponse>() {
                        @Override
                        public void onNext(RemoveFolloweeResponse removeFolloweeResponse) {
                            if (removeFolloweeResponse != null && removeFolloweeResponse.getResponse().equalsIgnoreCase(getResources().getString(R.string.remove_success_text))) {
                                followButton.setText(getResources().getString(R.string.follow_txt));
                                followButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                followButton.setTextColor(getResources().getColor(R.color.colorPrimary));
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

    private void followUser() {
        if (isThereInternetConnection(this)) {
            Observable<FollowUserResponse> isFollowingResponseObservable = getApiService().followUser(getJSessionIdInSharedPreferences(this), getFollowUserRequestBody(getUserIdFromSharedPreferences(this), mProfileDataObject.getUserId()));
            mDisposable = isFollowingResponseObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<FollowUserResponse>() {
                        @Override
                        public void onNext(FollowUserResponse followUserResponse) {
                            if (followUserResponse != null && followUserResponse.getIsStillFollowing()) {
                                followButton.setText(getResources().getString(R.string.unfollow_txt));
                                followButton.setBackgroundColor(getResources().getColor(R.color.colorRed));
                                followButton.setTextColor(getResources().getColor(R.color.colorWhite));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }
}
