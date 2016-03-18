package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

/**
 * Created by Khanh Ultron on 3/13/2016.
 */
public class PostListAdapter extends ArrayAdapter<RedditPost> implements View.OnClickListener {
    public static final int TYPE_NORMAL_ITEM = 0;
    public static final int TYPE_BOTTOM_ITEM = 1;
    OnItemClick sentBack;
    Boolean isLandscape;
    private List<RedditPost> redditPosts;

    public PostListAdapter(Context context, List<RedditPost> redditPosts, Boolean isLandscape, OnItemClick sentBack) {
        super(context, 0, redditPosts);
        this.redditPosts = redditPosts;
        this.isLandscape = isLandscape;
        this.sentBack = sentBack;
    }

    @Override
    public int getCount() {
        return redditPosts.size() + 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == getCount() - 1) ? TYPE_BOTTOM_ITEM : TYPE_NORMAL_ITEM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (type == TYPE_NORMAL_ITEM) {
                v = inflater.inflate(R.layout.item_view, null);
            } else {
                v = inflater.inflate(R.layout.bottom_view, null);
            }
        }
        v.findViewById(R.id.view_click_available).setOnClickListener(this);
        if (type == TYPE_NORMAL_ITEM) {
            RedditPost redditPost = redditPosts.get(position);
            if (redditPost != null) {
                if (!isLandscape) {
                    TextView tv_score = (TextView) v.findViewById(R.id.tv_score);
                    tv_score.setText(redditPost.getScore() + "");
                    TextView tv_au = (TextView) v.findViewById(R.id.tv_author);
                    tv_au.setText(redditPost.getAuthor());
                    tv_au.setTextColor(0xff0a295a);
                    TextView tv_sub = (TextView) v.findViewById(R.id.tv_subreddit);
                    tv_sub.setText(redditPost.getSubreddit());
                    tv_sub.setTextColor(0xff0a295a);

                    TextView tv_tittle = (TextView) v.findViewById(R.id.tv_title);
                    tv_tittle.setText(redditPost.getTitle());
                    if (redditPost.isStickyPost())
                        tv_tittle.setTextColor(0xff387801);
                    else tv_tittle.setTextColor(0xff000000);
                    TextView tv_cmt_dm_cd = (TextView) v.findViewById(R.id.tv_comment_domain_createdutc);
                    tv_cmt_dm_cd.setText(redditPost.getCommentCount() + " • " + redditPost.getDomain() + " • " + redditPost.getCreatedUTC());
                } else {
                    TextView tv_score = (TextView) v.findViewById(R.id.tv_score);
                    tv_score.setText(redditPost.getScore() + "");
                    TextView tv_au = (TextView) v.findViewById(R.id.tv_author);
                    tv_au.setText(redditPost.getAuthor());
                    TextView tv_tittle = (TextView) v.findViewById(R.id.tv_title);
                    tv_tittle.setText(redditPost.getTitle());
                    if (redditPost.isStickyPost())
                        tv_tittle.setTextColor(0xff387801);
                    else tv_tittle.setTextColor(0xff000000);
                    TextView tv_cmt_dm_cd = (TextView) v.findViewById(R.id.tv_comment_domain_createdutc);
                    tv_cmt_dm_cd.setText(redditPost.getCommentCount() + " • " + redditPost.getDomain() + " • " + redditPost.getCreatedUTC());
                }

            }

        }
        v.findViewById(R.id.view_click_available).setTag(position);
        return v;
    }

    @Override
    public void onClick(View v) {
        final int position = (int) v.getTag();
        final String url;
        if (getItemViewType(position)== TYPE_BOTTOM_ITEM){
            url = "https://www.reddit.com/r/androiddev";
        } else {
            final RedditPost data = redditPosts.get(position);
            url= data.getUrl();
        }
        sentBack.OnItemClick(url);
    }
}
