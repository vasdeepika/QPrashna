package com.android.qprashna.ui.feeds;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.qprashna.R;
import com.android.qprashna.api.FollowersResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.ui.common.ViewUtils.getJSessionIdInSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.getUserIdFromSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.isThereInternetConnection;
import static com.android.qprashna.ui.common.ViewUtils.showErrorMessage;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class FollowersFragment extends Fragment {

    private FollowersResponse mFollowersResponse;
    private int mCustomerId;
    private String mFragmentType;

    private MyFollowerItemRecyclerViewAdapter mAdapter;
    private Disposable mDisposable;
    private GridLayoutManager mLayoutManager;

    @BindView(R.id.feeds_loading_indicator)
    ProgressBar mLoadingBar;

    @BindView(R.id.followers_list)
    RecyclerView mFollowersRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FollowersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            mCustomerId = getUserIdFromSharedPreferences(getActivity());

            if (getArguments() != null) {
                mFragmentType = getArguments().getString(FeedsFragment.FEED_TYPE, FragmentTypes.GENERAL.toString());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers_list, container, false);
        ButterKnife.bind(this, view);

        showLoadingBar(true);

        mAdapter = new MyFollowerItemRecyclerViewAdapter();
        mFollowersRecyclerView.setAdapter(mAdapter);
        mFollowersRecyclerView.setHasFixedSize(true);
        // setting recipe columns depends on screen size
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth / 300);
        mLayoutManager = new GridLayoutManager(getActivity(), columns);
        mFollowersRecyclerView.setLayoutManager(mLayoutManager);

        getFollowers();

        return view;
    }

    private void getFollowers() {
        if (getActivity() == null) {
            return;
        }
        if (isThereInternetConnection(getActivity())) {
            Observable<FollowersResponse> responseObservable = getApiService().getFollowers(getJSessionIdInSharedPreferences(getActivity()), mCustomerId);
            if (mFragmentType.equals(FragmentTypes.FOLLOWERS.toString())) {
                responseObservable = getApiService().getFollowers(getJSessionIdInSharedPreferences(getActivity()), mCustomerId);
            } else if (mFragmentType.equals(FragmentTypes.FOLLOWING.toString())) {
                responseObservable = getApiService().getFollowees(getJSessionIdInSharedPreferences(getActivity()), mCustomerId);
            }
            mDisposable = responseObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<FollowersResponse>() {
                        @Override
                        public void onNext(FollowersResponse followersResponse) {
                            showLoadingBar(false);
                            if (followersResponse != null) {
                                mFollowersResponse = followersResponse;
                                mAdapter.setData(mFollowersResponse.getItems(), mCustomerId, mFragmentType);
                                mFollowersRecyclerView.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            // show error message if api call throws an error
                            showLoadingBar(false);
                            showErrorMessage(getActivity(), e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
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

    @Override
    public void onStop() {
        super.onStop();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    private void showLoadingBar(boolean flag) {
        if(flag && mLoadingBar != null && mLoadingBar.getVisibility() != View.VISIBLE) {
            mLoadingBar.setVisibility(View.VISIBLE);
        }else  if(!flag && mLoadingBar != null && mLoadingBar.getVisibility() != View.GONE) {
            mLoadingBar.setVisibility(View.GONE);
        }

    }

}
