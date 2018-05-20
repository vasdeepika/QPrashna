package com.android.qprashna.ui.feeds;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.qprashna.api.LoginResponse;
import com.android.qprashna.ui.ChangePasswordFragment;
import com.android.qprashna.ui.EditProfileFragment;
import com.android.qprashna.ui.login.SignInCreateAccountActivity;

import org.parceler.Parcels;

import static com.android.qprashna.ui.common.ViewUtils.saveUserIdInSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.storeJSessionIdInSharedPreferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LoginResponse mLoginResponse;
    NavigationView navigationView;
    Toolbar mToolbar;

    public static final String QPRASHNA_FRAGMENT = "fragment";

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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(LoginResponse.KEY)) {
            mLoginResponse = Parcels.unwrap(
                    bundle.getParcelable(LoginResponse.KEY));

            //Setting nav drawer header texts
            ((TextView) (headerView.findViewById(R.id.nav_user_name))).setText(String.format("%s %s", mLoginResponse.getFirstName(), mLoginResponse.getLastName()));
            ((TextView) (headerView.findViewById(R.id.nav_user_email))).setText(mLoginResponse.getEmail());

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
        int id = item.getItemId();

        if (id == R.id.nav_feeds) {
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
        } else if (id == R.id.nav_questions_answered_by_me) {
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

        } else if (id == R.id.nav_questions_unanswered_by_me) {
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

        } else if (id == R.id.nav_my_upvoted_questions) {
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

        } else if (id == R.id.nav_my_questions) {
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

        } else if (id == R.id.nav_followers) {
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

        } else if (id == R.id.nav_following) {
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
        } else if (id == R.id.nav_edit_profile) {
            mToolbar.setTitle(R.string.title_edit_profiile);
            EditProfileFragment editProfileFragment = new EditProfileFragment();
            Bundle fragmentBundle = new Bundle();
            fragmentBundle.putParcelable(LoginResponse.KEY, Parcels.wrap(mLoginResponse));
            editProfileFragment.setArguments(fragmentBundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.qprashna_fragment, editProfileFragment, QPRASHNA_FRAGMENT)
                    .commit();

        } else if (id == R.id.nav_change_password) {
            mToolbar.setTitle(R.string.title_change_password);
            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
            Bundle fragmentBundle = new Bundle();
            changePasswordFragment.setArguments(fragmentBundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.qprashna_fragment, changePasswordFragment, QPRASHNA_FRAGMENT)
                    .commit();
        } else if (id == R.id.nav_sign_out) {
            if (!navigationView.getMenu().getItem(8).isChecked()) {

                //clearing shared preferences after sign out
                saveUserIdInSharedPreferences(this, 0);
                storeJSessionIdInSharedPreferences(this, "");

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

    public void clearSearchText(View view) {
        FeedsFragment feedsFragment = (FeedsFragment) getSupportFragmentManager().findFragmentById(R.id.qprashna_fragment);
        if (feedsFragment != null) {
            feedsFragment.clearSearchText(view);
        }
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        mLoginResponse = loginResponse;
    }
}
