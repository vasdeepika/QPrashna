package com.android.qprashna.ui.feeds;

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

import org.parceler.Parcels;

public class FeedsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LoginResponse mLoginResponse;
    NavigationView navigationView;

    public static final String FEEDS_FRAGMENT = "feeds_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
                    .add(R.id.feeds_fragment, feedsFragment, FEEDS_FRAGMENT)
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
            if(!navigationView.getMenu().getItem(0).isChecked()) {
                FeedsFragment feedsFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.GENERAL.toString());
                feedsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.feeds_fragment, feedsFragment, FEEDS_FRAGMENT)
                        .commit();
            }
            // Handle the camera action
        } else if (id == R.id.nav_questions_answered_by_me) {
            if(!navigationView.getMenu().getItem(1).isChecked()) {
                FeedsFragment feedsFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.QUESTIONS_ANSWERED.toString());
                feedsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.feeds_fragment, feedsFragment, FEEDS_FRAGMENT)
                        .commit();
            }

        } else if (id == R.id.nav_questions_unanswered_by_me) {
            if(!navigationView.getMenu().getItem(2).isChecked()) {
                FeedsFragment feedsFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.QUESTIONS_UNANSWERED.toString());
                feedsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.feeds_fragment, feedsFragment, FEEDS_FRAGMENT)
                        .commit();
            }

        } else if (id == R.id.nav_my_upvoted_questions) {
            if(!navigationView.getMenu().getItem(3).isChecked()) {
                FeedsFragment feedsFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.QUESTIONS_UPVOTED.toString());
                feedsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.feeds_fragment, feedsFragment, FEEDS_FRAGMENT)
                        .commit();
            }

        } else if (id == R.id.nav_my_questions) {
            if (!navigationView.getMenu().getItem(3).isChecked()) {
                FeedsFragment feedsFragment = new FeedsFragment();
                Bundle fragmentBundle = new Bundle();
                fragmentBundle.putString(FeedsFragment.FEED_TYPE, FragmentTypes.QESTIONS_ASKED_BY_ME.toString());
                feedsFragment.setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.feeds_fragment, feedsFragment, FEEDS_FRAGMENT)
                        .commit();
            }

        } else if (id == R.id.nav_edit_profile) {

        } else if (id == R.id.nav_change_password) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clearSearchText(View view) {
        FeedsFragment feedsFragment = (FeedsFragment) getSupportFragmentManager().findFragmentById(R.id.feeds_fragment);
        if (feedsFragment!=null) {
            feedsFragment.clearSearchText(view);
        }
    }
}
