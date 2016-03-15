package com.hasbrain.areyouandroiddev;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.hasbrain.areyouandroiddev.adapter.ExpandableListAdapter;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/9/15.
 */
public class PostInSectionActivity extends BasePostListActivity {
    List<String> nameHeader;
    HashMap<String, List<RedditPost>> dataChild;
    ExpandableListView listGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void displayPostList(List<RedditPost> postList) {
        nameHeader = new ArrayList<>();
        nameHeader.add("Sticky Posts");
        nameHeader.add("Normal Posts");
        nameHeader.add("See more");
        dataChild = new HashMap<>();
        List<RedditPost> normalPost = new ArrayList<>();
        List<RedditPost> stickyPost = new ArrayList<>();
        for (int i = 0; i < postList.size() ; i++) {
            if (postList.get(i).isStickyPost()) {
                stickyPost.add(postList.get(i));
            } else {
                normalPost.add(postList.get(i));
            }
        }
        dataChild.put(nameHeader.get(0), stickyPost);
        dataChild.put(nameHeader.get(1), normalPost);
        dataChild.put(nameHeader.get(2), new ArrayList<RedditPost>());
        final ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(nameHeader, dataChild, this);

        setContentView(getLayoutResource());
        listGroup= (ExpandableListView) findViewById(R.id.list_group);
        listGroup.setAdapter(expandableListAdapter);
        listGroup.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent myIntent = new Intent(v.getContext(), WebViewActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                RedditPost post = (RedditPost) expandableListAdapter.getChild(groupPosition, childPosition);

                myIntent.putExtra("url", post.getUrl());


                startActivity(myIntent);
                return true;
            }
        });
        listGroup.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition == nameHeader.size()-1) {
                    Intent myIntent = new Intent(v.getContext(), WebViewActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    myIntent.putExtra("url", getString(R.string.url));
                    startActivity(myIntent);
                }

                    return false;
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_in_section;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_list: {
                Intent myIntent = new Intent(this,PostListActivity.class);
                startActivity(myIntent);
            }
            case R.id.post_group: {
                Toast.makeText(this,"You are in it", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.post_recycle: {
                Intent myIntent = new Intent(this, PostListRecycleViewActivity.class);
                startActivity(myIntent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
