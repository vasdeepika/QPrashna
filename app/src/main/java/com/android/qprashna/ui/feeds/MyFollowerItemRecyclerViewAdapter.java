package com.android.qprashna.ui.feeds;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.qprashna.R;
import com.android.qprashna.api.FollowerItem;
import com.android.qprashna.api.ProfileDataObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFollowerItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFollowerItemRecyclerViewAdapter.FollowerCardViewHolder> {

    private List<FollowerItem> mFollowerItems;
    private int mCustomerId;
    private String mFragmentType;
    private Context mContext;
    private ProfileDataObject profileDataObject;
    final private FollowerItemClickListener mFollowerItemClickListener;

    MyFollowerItemRecyclerViewAdapter(FollowerItemClickListener followerItemClickListener) {
        mFollowerItemClickListener = followerItemClickListener;
    }

    public String getFragmentType() {
        return mFragmentType;
    }

    public interface FollowerItemClickListener {
        void onFollowerItemClicked(FollowerItem followerItemId);
    }

    @Override
    public FollowerCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.follower_item, parent, false);
        mContext = parent.getContext();
        return new FollowerCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FollowerCardViewHolder holder, int position) {
        FollowerItem followerItem = mFollowerItems.get(position);
        Picasso.with(mContext)
                .load("https://qprashna.com/images/" + followerItem.getProfilePicURL())
                .placeholder(R.drawable.placeholder)
                .resize(450, 400)
                .into(holder.followerImage);

        String name = String.format(mContext.getString(R.string.profile_user_name), followerItem.getFirstName(), followerItem.getLastName());
        holder.followerName.setText(name);
    }

    @Override
    public int getItemCount() {
        return mFollowerItems.size();
    }

    public void setData(List<FollowerItem> items, int customerId, String fragmentType) {
        mFollowerItems = items;
        mCustomerId = customerId;
        mFragmentType = fragmentType;
        notifyDataSetChanged();
    }

    class FollowerCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.follower_name)
        TextView followerName;

        @BindView(R.id.follower_image)
        ImageView followerImage;

        @BindView(R.id.follower_layout)
        LinearLayout followerLayout;

        FollowerCardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFollowerItemClickListener.onFollowerItemClicked(mFollowerItems.get(getAdapterPosition()));
        }
    }
}
