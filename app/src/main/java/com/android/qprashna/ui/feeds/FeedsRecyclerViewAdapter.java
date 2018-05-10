package com.android.qprashna.ui.feeds;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.qprashna.R;
import com.android.qprashna.api.Item;
import com.android.qprashna.ui.feeds.FeedsFragment.OnListFragmentInteractionListener;
import com.android.qprashna.ui.feeds.dummy.DummyContent.DummyItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FeedsRecyclerViewAdapter extends RecyclerView.Adapter<FeedsRecyclerViewAdapter.CardViewHolder> {

    private List<Item> mFeedsItems;
    private Context mContext;

    FeedsRecyclerViewAdapter() {
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_feed, parent, false);
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
        holder.upVoteCount.setText(String.valueOf(feedItem.getQuestionUpvoteCount()));
        if (feedItem.getUpvoted()) {
            holder.upVote.setText(R.string.up_voted);
        }

        holder.upVote.setOnClickListener(v -> {
            if(holder.upVote.getText().equals(mContext.getResources().getString(R.string.up_voted))) {
                holder.upVote.setText(R.string.up_vote);
            } else if (holder.upVote.getText().equals(mContext.getResources().getString(R.string.up_vote))) {
                holder.upVote.setText(R.string.up_voted);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mFeedsItems!=null) {
            return mFeedsItems.size();
        }else {
            return 0;
        }
    }

    public void setFeeds(List<Item> feedsList) {
        mFeedsItems = feedsList;
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
