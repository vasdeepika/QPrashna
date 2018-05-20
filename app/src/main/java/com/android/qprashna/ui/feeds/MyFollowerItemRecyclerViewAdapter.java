package com.android.qprashna.ui.feeds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.android.qprashna.api.UserResult;
import com.android.qprashna.ui.ProfileViewActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFollowerItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFollowerItemRecyclerViewAdapter.FollowerCardViewHolder> {

    private List<FollowerItem> mFollowerItems;
    private int mCustomerId;
    private String mFragmentType;
    private Context mContext;

    MyFollowerItemRecyclerViewAdapter() {

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

        ProfileDataObject profileDataObject = new ProfileDataObject();
        if(mFragmentType.equals(FragmentTypes.FOLLOWERS.toString())) {
            profileDataObject.setUserId(followerItem.getUserId());
        } else if(mFragmentType.equals(FragmentTypes.FOLLOWING.toString())) {
            profileDataObject.setUserId(followerItem.getFolloweeId());
        }

        profileDataObject.setFirstName(followerItem.getFirstName());
        profileDataObject.setLastName(followerItem.getFirstName());
        profileDataObject.setProfilePicURL(followerItem.getProfilePicURL());

        holder.followerLayout.setOnClickListener(v -> {
            Intent profileViewActivityIntent =
                    new Intent(mContext, ProfileViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(UserResult.PROFILE, Parcels.wrap(profileDataObject));
            profileViewActivityIntent.putExtras(bundle);
            mContext.startActivity(profileViewActivityIntent);
        });
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

    class FollowerCardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.follower_name)
        TextView followerName;

        @BindView(R.id.follower_image)
        ImageView followerImage;

        @BindView(R.id.follower_layout)
        LinearLayout followerLayout;

        FollowerCardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
