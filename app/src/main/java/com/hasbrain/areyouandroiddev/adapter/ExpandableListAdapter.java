package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.HashMap;
import java.util.List;


/**
 * Created by Khanh Ultron on 12/23/2015.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    public static final int TYPE_GROUP_VIEW=0;
    public static final int TYPE_BOTTOM_VIEW=1;
    private static final int GROUP_COUNT = 3;
    private List<String> nameHeader;
    private HashMap<String, List<RedditPost>> dataChild;
    private  Context context;

    public ExpandableListAdapter(List<String> nameHeader, HashMap<String, List<RedditPost>> dataChild, Context context) {
        this.nameHeader = nameHeader;
        this.dataChild = dataChild;
        this.context = context;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getGroupCount() {
        return GROUP_COUNT;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return nameHeader.get(groupPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupName = (String) getGroup(groupPosition);
        List<RedditPost> groupData = dataChild.get(groupName);
        return groupData.size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String groupName = (String) getGroup(groupPosition);
        List<RedditPost> groupData = dataChild.get(groupName);
        return groupData.get(childPosition);
    }

    @Override
    public int getChildTypeCount() {
        return 1;
    }

    @Override
    public int getGroupTypeCount() {
        return 2;
    }

    @Override
    public int getGroupType(int groupPosition) {
        if (groupPosition == getGroupCount()-1){
            return TYPE_BOTTOM_VIEW;
        }else{
            return TYPE_GROUP_VIEW;
        }

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v= convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_view, null);
        }
        RedditPost redditPost = (RedditPost) getChild(groupPosition, childPosition);
        if (redditPost != null){
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
            tv_cmt_dm_cd.setText(redditPost.getCommentCount()+ " • " + redditPost.getDomain() + " • " + redditPost.getCreatedUTC());

        }
        return v;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v= convertView;
        int type= getGroupType(groupPosition);
        if (v == null) {
            LayoutInflater infalInflater = LayoutInflater.from(context);
            if (type != TYPE_BOTTOM_VIEW)
                v = infalInflater.inflate(R.layout.group_view, null);
            else
                v = infalInflater.inflate(R.layout.bottom_view, null);
        }
        if(type != TYPE_BOTTOM_VIEW) {
            TextView tv_group = (TextView) v.findViewById(R.id.id_header);
            tv_group.setText(getGroup(groupPosition).toString());
        }
        return v;
    }
}
