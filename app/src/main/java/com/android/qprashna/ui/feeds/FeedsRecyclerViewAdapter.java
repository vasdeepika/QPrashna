package com.android.qprashna.ui.feeds;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.qprashna.R;
import com.android.qprashna.api.Item;
import com.android.qprashna.ui.feeds.FeedsFragment.OnListFragmentInteractionListener;
import com.android.qprashna.ui.feeds.dummy.DummyContent.DummyItem;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.api.ApiUtils.getErrorMessage;
import static com.android.qprashna.ui.common.ViewUtils.isThereInternetConnection;
import static com.android.qprashna.ui.common.ViewUtils.showErrorMessage;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FeedsRecyclerViewAdapter extends RecyclerView.Adapter<FeedsRecyclerViewAdapter.CardViewHolder> {

    private List<Item> mFeedsItems;
    private Context mContext;
    private int mCustomerId;
    private Disposable mDisposable;

    FeedsRecyclerViewAdapter() {
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.feed_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        Item feedItem = mFeedsItems.get(position);
        holder.feedTitle.setText(feedItem.getFeedTitle());
        holder.timeAgo.setText(feedItem.getTimeAgo());
        holder.feedQuestion.setText(feedItem.getFeedQuestionText());
        String askedBy = String.format(mContext.getResources().getString(R.string.asked_by_text),feedItem.getFeedQuestionAskedBy(), feedItem.getFeedQuestionAskedTO());
        holder.askedBy.setText(askedBy);

        int upVoteCount = 0;

        if (feedItem.getQuestionUpvoteCount() != null) {
            upVoteCount = feedItem.getQuestionUpvoteCount();
        }

        holder.upVoteCount.setText(getUpVoteCountText(upVoteCount));

        if (feedItem.getUpvoted()) {
            holder.upVote.setText(R.string.up_voted);
            holder.upVote.setBackgroundColor(mContext.getResources().getColor(R.color.tranparant_primary));
        } else if (!feedItem.getUpvoted()) {
            holder.upVote.setText(R.string.up_vote);
            holder.upVote.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        holder.upVote.setOnClickListener(v -> {
            int count = getUpvoteCount(holder.upVoteCount.getText().toString());
            if(holder.upVote.getText().equals(mContext.getResources().getString(R.string.up_voted))) {
                int newCount = updateUpVoteCount("unupvote", feedItem.getFeedQuestionId(), feedItem.getFeedId(), count);
                if(count!=newCount && count>newCount) {
                    holder.upVoteCount.setText(getUpVoteCountText(newCount));
                    holder.upVote.setText(R.string.up_vote);
                    holder.upVote.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                }
            } else if (holder.upVote.getText().equals(mContext.getResources().getString(R.string.up_vote))) {
                int newCount = updateUpVoteCount("upvote", feedItem.getFeedQuestionId(), feedItem.getFeedId(), count);
                if(count!=newCount && count<newCount) {
                    holder.upVoteCount.setText(getUpVoteCountText(count));
                    holder.upVote.setText(R.string.up_voted);
                    holder.upVote.setBackgroundColor(mContext.getResources().getColor(R.color.tranparant_primary));
                }
            }
        });
    }

    private int updateUpVoteCount(String action, Integer feedQuestionId, Integer feedId, Integer count) {
        int[] upVoteCount = {count};
        if (isThereInternetConnection((Activity) mContext)) {

            Observable<Integer> updateUpVoteCountObservable = getApiService().putUpVote(mCustomerId,feedQuestionId,action,feedId);
            mDisposable = updateUpVoteCountObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Integer>() {
                        @Override
                        public void onNext(Integer count) {
                            upVoteCount[0] = count;
                        }

                        @Override
                        public void onError(Throwable e) {
                            // show error message if api call throws an error
                           showErrorMessage(mContext, e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
        return  upVoteCount[0];
    }

    private int getUpvoteCount(String text) {
       return Integer.valueOf(text.split("\\s+")[0]);
    }

    private String getUpVoteCountText(int upVoteCount) {
        return String.format(mContext.getResources().getString(R.string.up_votes), Integer.valueOf(upVoteCount).toString());
    }

    @Override
    public int getItemCount() {
        if(mFeedsItems!=null) {
            return mFeedsItems.size();
        }else {
            return 0;
        }
    }

    public void setFeeds(List<Item> feedsList, int customerId) {
        mFeedsItems = feedsList;
        mCustomerId = customerId;
        notifyDataSetChanged();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.feed_title)
        TextView feedTitle;

        @BindView(R.id.time_ago)
        TextView timeAgo;

        @BindView(R.id.feed_question)
        TextView feedQuestion;

        @BindView(R.id.asked_by)
        TextView askedBy;

        @BindView(R.id.upVote_count)
        Button upVoteCount;

        @BindView(R.id.upVote)
        Button upVote;

        CardViewHolder(View cardView) {
            super(cardView);
            ButterKnife.bind(this, cardView);
        }

    }
}
