package com.android.qprashna.ui.feeds;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.qprashna.R;
import com.android.qprashna.api.ProfileDataObject;
import com.android.qprashna.data.QprashnaContract;
import com.android.qprashna.ui.ChangePasswordFragment;
import com.android.qprashna.ui.EditProfileFragment;
import com.android.qprashna.ui.login.SignInCreateAccountActivity;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import org.parceler.Parcels;

import static com.android.qprashna.ui.common.ViewUtils.saveUserIdInSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.storeJSessionIdInSharedPreferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks {

    ProfileDataObject mProfileDetails;
    NavigationView navigationView;
    Toolbar mToolbar;

    public static final String QPRASHNA_FRAGMENT = "fragment";
    private int navDrawerSelectedItemId;

    private static final String JOB_TAG = "QprashnaReminderJobService";
    private FirebaseJobDispatcher mDispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        View headerView = navigationView.getHeaderView(0);

        if (savedInstanceState == null) {
            getSupportLoaderManager().initLoader(1, null, this);

            if(getIntent().hasExtra(FeedsFragment.FEED_TYPE)) {
                if(getIntent().getStringExtra(FeedsFragment.FEED_TYPE).equals(FragmentTypes.QUESTIONS_UNANSWERED.toString())) {
                    navigationView.getMenu().getItem(2).setChecked(true);
                    FeedsFragment feedsFragment = new FeedsFragment();
                    Bundle fragmentBundle = new Bundle();
                    fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.QUESTIONS_UNANSWERED.toString());
                    feedsFragment.setArguments(fragmentBundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.qprashna_fragment, feedsFragment, QPRASHNA_FRAGMENT)
                            .commit();
                }

            } else {
                FeedsFragment feedsFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.GENERAL.toString());
                feedsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.qprashna_fragment, feedsFragment, QPRASHNA_FRAGMENT)
                        .commit();
            }
        }

        if (savedInstanceState != null) {
            mProfileDetails = Parcels.unwrap(savedInstanceState.getParcelable(ProfileDataObject.KEY));
        }

        if (mProfileDetails != null) {
            //Setting nav drawer header texts
            ((TextView) (headerView.findViewById(R.id.nav_user_name))).setText(String.format("%s %s", mProfileDetails.getFirstName(), mProfileDetails.getLastName()));
            ((TextView) (headerView.findViewById(R.id.nav_user_email))).setText(mProfileDetails.getEmail());
        }

        mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        scheduleJob();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        navDrawerSelectedItemId = item.getItemId();

        if (navDrawerSelectedItemId == R.id.nav_feeds) {
            if (!navigationView.getMenu().getItem(0).isChecked()) {
                mToolbar.setTitle(R.string.title_activity_feeds);
                FeedsFragment feedsFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.GENERAL.toString());
                feedsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.qprashna_fragment, feedsFragment, QPRASHNA_FRAGMENT)
                        .commit();
            }
            // Handle the camera action
        } else if (navDrawerSelectedItemId == R.id.nav_questions_answered_by_me) {
            if (!navigationView.getMenu().getItem(1).isChecked()) {
                mToolbar.setTitle(R.string.title_activity_qtns_answered_by_me);
                FeedsFragment feedsFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.QUESTIONS_ANSWERED.toString());
                feedsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.qprashna_fragment, feedsFragment, QPRASHNA_FRAGMENT)
                        .commit();
            }

        } else if (navDrawerSelectedItemId == R.id.nav_questions_unanswered_by_me) {
            if (!navigationView.getMenu().getItem(2).isChecked()) {
                mToolbar.setTitle(R.string.title_activity_qtns_un_answered_by_me);
                FeedsFragment feedsFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.QUESTIONS_UNANSWERED.toString());
                feedsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.qprashna_fragment, feedsFragment, QPRASHNA_FRAGMENT)
                        .commit();
            }

        } else if (navDrawerSelectedItemId == R.id.nav_my_upvoted_questions) {
            if (!navigationView.getMenu().getItem(3).isChecked()) {
                mToolbar.setTitle(R.string.title_activity_qtns_my_upvoted_qtns);
                FeedsFragment feedsFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.QUESTIONS_UPVOTED.toString());
                feedsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.qprashna_fragment, feedsFragment, QPRASHNA_FRAGMENT)
                        .commit();
            }

        } else if (navDrawerSelectedItemId == R.id.nav_my_questions) {
            if (!navigationView.getMenu().getItem(4).isChecked()) {
                mToolbar.setTitle(R.string.title_activity_qtns_asked_by_me);
                FeedsFragment questionAskedByMeFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.QESTIONS_ASKED_BY_ME.toString());
                questionAskedByMeFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.qprashna_fragment, questionAskedByMeFragment, QPRASHNA_FRAGMENT)
                        .commit();
            }

        } else if (navDrawerSelectedItemId == R.id.nav_followers) {
            if (!navigationView.getMenu().getItem(5).isChecked()) {
                mToolbar.setTitle(R.string.title_followers);
                FollowersFragment followersFragment = new FollowersFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.FOLLOWERS.toString());
                followersFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.qprashna_fragment, followersFragment, QPRASHNA_FRAGMENT)
                        .commit();
            }

        } else if (navDrawerSelectedItemId == R.id.nav_following) {
            if (!navigationView.getMenu().getItem(6).isChecked()) {
                mToolbar.setTitle(R.string.title_following);
                FollowersFragment followingsFragment = new FollowersFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.FOLLOWING.toString());
                followingsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.qprashna_fragment, followingsFragment, QPRASHNA_FRAGMENT)
                        .commit();
            }
        } else if (navDrawerSelectedItemId == R.id.nav_edit_profile) {
            if (!navigationView.getMenu().getItem(7).getSubMenu().getItem(0).isChecked()) {
                mToolbar.setTitle(R.string.title_edit_profiile);
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putParcelable(ProfileDataObject.KEY,Parcels.wrap(mProfileDetails));
                editProfileFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.qprashna_fragment, editProfileFragment, QPRASHNA_FRAGMENT)
                        .commit();
            }

        } else if (navDrawerSelectedItemId == R.id.nav_change_password) {
            if (!navigationView.getMenu().getItem(7).getSubMenu().getItem(1).isChecked()) {
                mToolbar.setTitle(R.string.title_change_password);
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.qprashna_fragment, changePasswordFragment, QPRASHNA_FRAGMENT)
                        .commit();
            }
        } else if (navDrawerSelectedItemId == R.id.nav_sign_out) {
            if (!navigationView.getMenu().getItem(8).isChecked()) {

                //clearing shared preferences after sign out
                saveUserIdInSharedPreferences(this, 0);
                storeJSessionIdInSharedPreferences(this, "");
                deleteProfileData();

                Intent signInCreateAccountIntent =
                        new Intent(this, SignInCreateAccountActivity.class);
                signInCreateAccountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signInCreateAccountIntent);
                finish();
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void deleteProfileData() {
        getContentResolver().delete(QprashnaContract.ProfileEntry.CONTENT_URI, null, null);
    }

    public void clearSearchText(View view) {
        FeedsFragment feedsFragment = (FeedsFragment) getSupportFragmentManager().findFragmentById(R.id.qprashna_fragment);
        if (feedsFragment != null) {
            feedsFragment.clearSearchText(view);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save outState
        outState.putParcelable(ProfileDataObject.KEY, Parcels.wrap(mProfileDetails));
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        Uri CONTENT_URI = QprashnaContract.ProfileEntry.CONTENT_URI;
        return new CursorLoader(this, CONTENT_URI, null,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object cursor) {
        Cursor profileCursor = (Cursor) cursor;

        mProfileDetails = new ProfileDataObject();

        if (profileCursor != null && profileCursor.moveToFirst()) {

            profileCursor.moveToFirst();

            mProfileDetails.setUserId(profileCursor.getInt(profileCursor.getColumnIndex(QprashnaContract.ProfileEntry.USERID)));
            mProfileDetails.setFirstName(profileCursor.getString(profileCursor.getColumnIndex(QprashnaContract.ProfileEntry.FIRSTNAME)));
            mProfileDetails.setLastName(profileCursor.getString(profileCursor.getColumnIndex(QprashnaContract.ProfileEntry.LASTNAME)));
            mProfileDetails.setEmail(profileCursor.getString(profileCursor.getColumnIndex(QprashnaContract.ProfileEntry.EMAIL)));
            mProfileDetails.setDesignation(profileCursor.getString(profileCursor.getColumnIndex(QprashnaContract.ProfileEntry.DESIGNATION)));
            mProfileDetails.setDob(profileCursor.getLong(profileCursor.getColumnIndex(QprashnaContract.ProfileEntry.DOB)));
            mProfileDetails.setCountry(profileCursor.getString(profileCursor.getColumnIndex(QprashnaContract.ProfileEntry.COUNTRY)));
            mProfileDetails.setState(profileCursor.getString(profileCursor.getColumnIndex(QprashnaContract.ProfileEntry.STATE)));
            mProfileDetails.setProfilePicURL(profileCursor.getString(profileCursor.getColumnIndex(QprashnaContract.ProfileEntry.PROFILEPIC)));
        }

        if (profileCursor != null) {
            profileCursor.close();
        }

        if (mProfileDetails != null) {
            //Setting nav drawer header texts
            ((TextView) (navigationView.getHeaderView(0).findViewById(R.id.nav_user_name))).setText(String.format("%s %s", mProfileDetails.getFirstName(), mProfileDetails.getLastName()));
            ((TextView) (navigationView.getHeaderView(0).findViewById(R.id.nav_user_email))).setText(mProfileDetails.getEmail());
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    private void scheduleJob() {
        Job myJob = mDispatcher.newJobBuilder()
                .setService(QprashnaJobService.class)
                .setTag(JOB_TAG)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(0, 30))
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();
        mDispatcher.mustSchedule(myJob);
    }
}
