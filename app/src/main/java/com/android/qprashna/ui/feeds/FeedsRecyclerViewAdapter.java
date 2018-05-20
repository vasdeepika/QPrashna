package com.android.qprashna.ui.feeds;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.qprashna.R;
import com.android.qprashna.api.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.ui.common.ViewUtils.getJSessionIdInSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.isThereInternetConnection;
import static com.android.qprashna.ui.common.ViewUtils.showErrorMessage;

public class FeedsRecyclerViewAdapter extends RecyclerView.Adapter<FeedsRecyclerViewAdapter.CardViewHolder> {

    private List<Item> mFeedsItems;
    private Context mContext;
    private int mCustomerId;
    private String mFragmentType;
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
        String answeredBy = String.format(mContext.getResources().getString(R.string.answered_by_text), feedItem.getFeedQuestionAskedTOName(), feedItem.getFeedQuestionAskedByName());
        String askedBy = String.format(mContext.getResources().getString(R.string.asked_by_text),feedItem.getFeedQuestionAskedByName(), feedItem.getFeedQuestionAskedTOName());
        String postedBy = String.format(mContext.getResources().getString(R.string.posted_by_text), feedItem.getFeedPostBy());
        if(mFragmentType.equals(FragmentTypes.QUESTIONS_ANSWERED.toString())) {
            holder.askedBy.setText(answeredBy);
            holder.upVote.setVisibility(View.GONE);
            holder.feedTitle.setText(feedItem.getFeedQuestionText());
            holder.feedQuestion.setText(feedItem.getFeedAnswerText());
            holder.feedQuestion.setTypeface(null, Typeface.NORMAL);
            holder.feedTitle.setTypeface(null, Typeface.BOLD);
            holder.timeAgo.setVisibility(View.GONE);
        } else {
            holder.feedTitle.setText(feedItem.getFeedTitle());
            holder.timeAgo.setText(feedItem.getTimeAgo());
            if (feedItem.getFeedType().equals("P")) {
                holder.feedQuestion.setText(feedItem.getFeedPostText());
                holder.askedBy.setText(postedBy);
            } else {
                holder.feedQuestion.setText(feedItem.getFeedQuestionText());
                holder.askedBy.setText(askedBy);
            }
        }

        if(feedItem.getFeedAnswerText()!=null && !mFragmentType.equals(FragmentTypes.QUESTIONS_ANSWERED.toString())) {
            String qtnAndAnswertext = String.format(mContext.getResources().getString(R.string.qtn_and_answer_text), feedItem.getFeedQuestionText(), feedItem.getFeedAnswerText());
            holder.feedQuestion.setText(qtnAndAnswertext);
            holder.askedBy.setText(answeredBy);
        }

        if(mFragmentType.equals(FragmentTypes.QUESTIONS_UPVOTED.toString()) || mFragmentType.equals(FragmentTypes.QESTIONS_ASKED_BY_ME.toString()) || mFragmentType.equals(FragmentTypes.QUESTIONS_UNANSWERED.toString())) {
            holder.timeAgo.setVisibility(View.GONE);
            holder.feedTitle.setVisibility(View.GONE);
        }

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
                updateUpVoteCount("unupvote", feedItem.getFeedQuestionId(), feedItem.getFeedId(), count, holder.upVoteCount, holder.upVote);
            } else if (holder.upVote.getText().equals(mContext.getResources().getString(R.string.up_vote))) {
                updateUpVoteCount("upvote", feedItem.getFeedQuestionId(), feedItem.getFeedId(), count, holder.upVoteCount, holder.upVote);
            }
        });
    }

    private void updateUpVoteCount(String action, Integer feedQuestionId, Integer feedId, Integer oldCount, Button upVoteCount, Button upVote) {
        if (isThereInternetConnection((Activity) mContext)) {

            Observable<Integer> updateUpVoteCountObservable = getApiService().putUpVote(getJSessionIdInSharedPreferences(mContext), mCustomerId,feedQuestionId,action,feedId);
            mDisposable = updateUpVoteCountObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Integer>() {
                        @Override
                        public void onNext(Integer count) {
                            if(!oldCount.equals(count) && oldCount<count) {
                                upVoteCount.setText(getUpVoteCountText(count));
                                upVote.setText(R.string.up_voted);
                                upVote.setBackgroundColor(mContext.getResources().getColor(R.color.tranparant_primary));
                            }
                            if(!oldCount.equals(count) && oldCount>count) {
                                upVoteCount.setText(getUpVoteCountText(count));
                                upVote.setText(R.string.up_vote);
                                upVote.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                            }
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

    public void setData(List<Item> feedsList, int customerId, String fragmentType) {
        mFeedsItems = feedsList;
        mCustomerId = customerId;
        mFragmentType = fragmentType;
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
