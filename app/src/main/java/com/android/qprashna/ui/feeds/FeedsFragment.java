package com.android.qprashna.ui.feeds;

import android.content.Context;
import android.content.Intent;
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
import com.android.qprashna.ui.ProfileViewActivity;
import com.android.qprashna.ui.feeds.dummy.DummyContent.DummyItem;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

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

    private int mCustomerId;
    private String mFeedType;
    private Disposable mDisposable;


    public static final String CUSTOMER_ID = "customer";
    public static final String FEED_TYPE = "feed_type";
    private FeedsResponse mfeedsResponse;
    private FeedsRecyclerViewAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private List<UserResult> mUsersResults;
    ArrayList<String> mUsersAdapterList;
    private ArrayAdapter<String> mUsersAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedsFragment() {
    }

//    @SuppressWarnings("unused")
//    public static FeedsFragment newInstance(int columnCount) {
//        FeedsFragment fragment = new FeedsFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCustomerId = getArguments().getInt(FeedsFragment.CUSTOMER_ID);
            mFeedType = getArguments().getString(FeedsFragment.FEED_TYPE, FeedTypes.GENERAL.toString());
        }

        if(savedInstanceState != null && savedInstanceState.containsKey(UsersResponse.KEY)) {
            mUsersResults = Parcels.unwrap(savedInstanceState.getParcelable(UsersResponse.KEY));
        }
    }

    private void setUpSearchAutoComplete() {
//        hideSoftKeyboard(mSearchText, getActivity());

        mSearchText.setThreshold(2);
        mSearchText.setAdapter(mUsersAdapter);

        mSearchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mSearchText.getText().toString().isEmpty()) {
                    mClearSearch.setVisibility(View.INVISIBLE);
                } else {
                    mClearSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mSearchText.getText().length() >= mSearchText.getThreshold()) {
                    loadUsers(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }
        });

        launchSelectedUsersScreen();
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
                                mUsersResults = usersResponse.getUserResult();
                                mUsersAdapterList.clear();
                                mUsersAdapter.clear();
                                for (UserResult user : usersResponse.getUserResult()) {
                                    mUsersAdapterList.add(user.getFirstName()+" "+user.getLastName());
                                }
                                mUsersAdapter.addAll(mUsersAdapterList);
                                mUsersAdapter.notifyDataSetChanged();
                                mSearchText.showDropDown();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mUsersAdapterList.clear();
                            mUsersAdapter.clear();
                            mUsersAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private void launchSelectedUsersScreen() {
        mSearchText.setOnItemClickListener((parent, view, position, id) -> {
            if(mUsersResults.size() != 0 ) {
                mSearchText.setText("");
                mUsersAdapterList.clear();
                mUsersAdapter.clear();
                mUsersAdapter.notifyDataSetChanged();

                Intent profileViewActivityIntent =
                        new Intent(getContext(), ProfileViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(UserResult.PROFILE, Parcels.wrap(mUsersResults.get(position)));
                profileViewActivityIntent.putExtras(bundle);
                startActivity(profileViewActivityIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
        mUsersAdapter.setNotifyOnChange(true);

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save outState
        outState.putParcelable(UsersResponse.KEY, Parcels.wrap(mUsersResults));
    }
}
