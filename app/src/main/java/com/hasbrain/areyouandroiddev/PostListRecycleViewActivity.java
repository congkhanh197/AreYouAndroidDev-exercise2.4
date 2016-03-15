package com.hasbrain.areyouandroiddev;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.hasbrain.areyouandroiddev.adapter.PostListAdapter;
import com.hasbrain.areyouandroiddev.adapter.PostListRecycleViewAdapter;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

/**
 * Created by Khanh Ultron on 3/15/2016.
 */
public class PostListRecycleViewActivity extends BasePostListActivity {
    private Boolean isLandscape;
    private PostListRecycleViewAdapter myAdapter;


    @Override
    protected void displayPostList(List<RedditPost> postList) {
        super.displayPostList(postList);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        myAdapter = new PostListRecycleViewAdapter(postList,this,isLandscape);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(isLandscape ?
                new GridLayoutManager(this, 3) : new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_list_recycle_view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_list: {
                Intent myIntent = new Intent(this, PostListAdapter.class);
                startActivity(myIntent);
                return true;
            }
            case R.id.post_group: {
                Intent myIntent = new Intent(this, PostInSectionActivity.class);
                startActivity(myIntent);
                return true;
            }
            case R.id.post_recycle: {
                Toast.makeText(this,"You are in it", Toast.LENGTH_SHORT).show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
