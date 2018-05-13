package com.android.qprashna.ui.feeds;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.qprashna.R;
import com.android.qprashna.api.FeedsResponse;
import com.android.qprashna.api.UserResult;
import com.android.qprashna.api.UsersResponse;
import com.android.qprashna.ui.feeds.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.ui.common.ViewUtils.isThereInternetConnection;
import static com.android.qprashna.ui.common.ViewUtils.showErrorMessage;


public class FeedsFragment extends Fragment {

    @BindView(R.id.search_text)
    AutoCompleteTextView mSearchText;

    @BindView(R.id.clear_search_icon)
    Button mClearSearch;

    @BindView(R.id.feeds_loading_indicator)
    ProgressBar mLoadingBar;

    @BindView(R.id.feeds_list)
    RecyclerView mFeedsRecyclerView;
//
//    @BindView(R.id.input_layout_email)
//    TextInputLayout emailAddressLayout;

    private int mCustomerId;
    private String mFeedType;
    private Disposable mDisposable;


    public static final String CUSTOMER_ID = "customer";
    public static final String FEED_TYPE = "feed_type";
    private FeedsResponse mfeedsResponse;
    private FeedsRecyclerViewAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private UsersResponse mUsersResponse;
    ArrayList<String> mUsersAdapterList;
    private ArrayAdapter<String> mUsersAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FeedsFragment newInstance(int columnCount) {
        FeedsFragment fragment = new FeedsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCustomerId = getArguments().getInt(FeedsFragment.CUSTOMER_ID);
            mFeedType = getArguments().getString(FeedsFragment.FEED_TYPE, FeedTypes.GENERAL.toString());
        }
    }

    private void setUpSearchAutoComplete() {
//        hideSoftKeyboard(mSearchText, getActivity());

        mSearchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing

                loadUsers(s.toString());

                mSearchText.setThreshold(2);
                mSearchText.setAdapter(mUsersAdapter);
                mSearchText.showDropDown();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) {
                    mClearSearch.setVisibility(View.VISIBLE);
                } else {
                    mClearSearch.setVisibility(View.GONE);
                }
                if (mSearchText.enoughToFilter()) {
                    mSearchText.showDropDown();
                    mSearchText.bringToFront();
                }
            }
        });

    }

    private void loadUsers(String s) {
        if (getActivity() == null) {
            return;
        }
        if (isThereInternetConnection(getActivity())) {
            Observable<UsersResponse> feedsResponseObservable = getApiService().getUsers(s);
            mDisposable = feedsResponseObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<UsersResponse>() {
                        @Override
                        public void onNext(UsersResponse usersResponse) {
                            if (usersResponse != null) {
                                for (UserResult user : usersResponse.getUserResult()) {
                                    mUsersAdapterList.add(user.getFirstName()+user.getLastName());
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            // show error message if api call throws an error
                            showErrorMessage(getActivity(),e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void clearSearchText(View v) {
        mSearchText.setText("");
        mClearSearch.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feeds_list, container, false);

        ButterKnife.bind(this, rootView);

        showLoadingBar(true);

        mAdapter = new FeedsRecyclerViewAdapter();
        mFeedsRecyclerView.setAdapter(mAdapter);
        mFeedsRecyclerView.setHasFixedSize(true);

        // setting recipe columns depends on screen size
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth / 300);
        mLayoutManager = new GridLayoutManager(getActivity(), columns);
        mFeedsRecyclerView.setLayoutManager(mLayoutManager);

        mSearchText.setThreshold(1);
        mUsersAdapterList = new ArrayList<>();
        mUsersAdapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.select_dialog_item, mUsersAdapterList);

        setUpSearchAutoComplete();
        if (savedInstanceState == null) {
            loadFeeds();
        }

//        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mCustomerId <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mCustomerId));
//            }
//            recyclerView.setAdapter(new FeedsRecyclerViewAdapter());
//        }
        return rootView;
    }

    private void loadFeeds() {
        if (getActivity() == null) {
            return;
        }
        if (isThereInternetConnection(getActivity())) {
            Observable<FeedsResponse> feedsResponseObservable = getApiService().getFeeds(mCustomerId);
            mDisposable = feedsResponseObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<FeedsResponse>() {
                        @Override
                        public void onNext(FeedsResponse feedsResponse) {
                            showLoadingBar(false);
                            if (feedsResponse != null) {
                                mfeedsResponse = feedsResponse;
                                mAdapter.setFeeds(mfeedsResponse.getItems(), mCustomerId);
                                mFeedsRecyclerView.setVisibility(View.VISIBLE);                            }
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

    private void showLoadingBar(boolean flag) {
        if(flag && mLoadingBar != null && mLoadingBar.getVisibility() != View.VISIBLE) {
            mLoadingBar.setVisibility(View.VISIBLE);
        }else  if(!flag && mLoadingBar != null && mLoadingBar.getVisibility() != View.GONE) {
            mLoadingBar.setVisibility(View.GONE);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
