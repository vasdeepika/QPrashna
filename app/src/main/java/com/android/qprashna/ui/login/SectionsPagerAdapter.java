package com.android.qprashna.ui.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.android.qprashna.ui.login.SignInOrCreateAccountFragment.USER_TYPE;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a SignInOrCreateAccountFragment (defined as a static inner class below).
        SignInOrCreateAccountFragment fragment = new SignInOrCreateAccountFragment();
        Bundle bundle = new Bundle();
        if (position == 0) {
            bundle.putString(USER_TYPE, UserTypes.EXISTING.toString());
        } else  if (position == 1) {
            bundle.putString(USER_TYPE, UserTypes.NEW.toString());
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        // Show sign in and create account total pages.
        return 2;
    }
}
