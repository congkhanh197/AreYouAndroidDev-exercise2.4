package com.hasbrain.areyouandroiddev.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.OnItemClick;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

/**
 * Created by Khanh Ultron on 3/13/2016.
 */
public class PostListRecycleViewAdapter extends RecyclerView.Adapter<PostListRecycleViewAdapter.ViewHolden> implements View.OnClickListener {
    List<RedditPost> redditPosts;
    OnItemClick sendBack;
    private static final int DEFAULT_ITEM = 0, BOTTOM_ITEM = 1;
    private boolean isLandscape;

    public PostListRecycleViewAdapter(List<RedditPost> redditPosts, OnItemClick sendBack, boolean isLandscape) {
        this.redditPosts = redditPosts;
        this.sendBack= sendBack;
        this.isLandscape= isLandscape;

    }

    @Override
    public ViewHolden onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                viewType == DEFAULT_ITEM ? R.layout.item_view : R.layout.bottom_view, parent, false);
        v.findViewById(R.id.view_click_available).setOnClickListener(this);
        return new ViewHolden(v);
    }

    @Override
    public void onBindViewHolder(ViewHolden holder, int position) {
        if (getItemViewType(position) == DEFAULT_ITEM) {
            final RedditPost data = redditPosts.get(position);
            if (!isLandscape) {
                holder.tvScore.setText(data.getScore() + "");
                holder.tvAuthor.setText(data.getAuthor());
                holder.tvAuthor.setTextColor(0xff0a295a);
                holder.tvTitle.setText(data.getTitle());
                holder.tvSub.setText(data.getSubreddit());
                holder.tvSub.setTextColor(0xff0a295a);
                if (data.isStickyPost())
                    holder.tvTitle.setTextColor(0xff387801);
                else holder.tvTitle.setTextColor(0xff000000);
                holder.tvCommentAndDomain.setText(data.getCommentCount() + " • " + data.getDomain() + " • " + data.getCreatedUTC());
            } else {
                holder.tvScore.setText(data.getScore()+"");
                holder.tvAuthor.setText(data.getAuthor());

                holder.tvTitle.setText(data.getTitle());
                if (data.isStickyPost())
                    holder.tvTitle.setTextColor(0xff387801);
               // else holder.tvTitle.setTextColor(0xff000000);
                holder.tvCommentAndDomain.setText(data.getCommentCount() + " • " + data.getDomain() + " • " + data.getCreatedUTC());
            }
        }
        holder.findViewById(R.id.view_click_available).setTag(position);
    }

    @Override
    public int getItemCount() {
        return redditPosts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount() - 1) ? BOTTOM_ITEM : DEFAULT_ITEM;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        final String url;
        if (getItemViewType(position)== BOTTOM_ITEM){
            url = "https://www.reddit.com/r/androiddev";
        } else {
            final RedditPost data = redditPosts.get(position);
            url= data.getUrl();
        }
        sendBack.OnItemClick(url);

    }


    public class ViewHolden extends RecyclerView.ViewHolder {
        View root;
        TextView tvScore, tvAuthor, tvSub, tvTitle, tvCommentAndDomain;

        public ViewHolden(View v) {
            super(v);
            this.root = v;
            if (isLandscape){
                tvScore = findTextView(R.id.tv_score);
                tvAuthor = findTextView(R.id.tv_author);
                tvTitle = findTextView(R.id.tv_title);
                tvCommentAndDomain = findTextView(R.id.tv_comment_domain_createdutc);
            }else {
                tvScore = findTextView(R.id.tv_score);
                tvAuthor = findTextView(R.id.tv_author);
                tvSub = findTextView(R.id.tv_subreddit);
                tvTitle = findTextView(R.id.tv_title);
                tvCommentAndDomain = findTextView(R.id.tv_comment_domain_createdutc);
            }
        }

        private TextView findTextView(int id) {
            View v = findViewById(id);
            return v == null ?
                    null : (TextView) v;
        }

        private View findViewById(int id) {
            return root.findViewById(id);
        }

    }
}
