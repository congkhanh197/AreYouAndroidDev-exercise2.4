package com.hasbrain.areyouandroiddev;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.hasbrain.areyouandroiddev.adapter.PostListAdapter;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

public class PostListActivity extends BasePostListActivity {

    private Boolean isLandscape;
    private PostListAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void displayPostList(List<RedditPost> postList) {
        super.displayPostList(postList);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        myAdapter = new PostListAdapter(this,postList,isLandscape, this);
        if (!isLandscape){
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(myAdapter);
        } else {
            GridView gridView = (GridView) findViewById(R.id.grid_view);
            gridView.setAdapter(myAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_list: {
                Toast.makeText(this,"You are in it", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.post_group: {
                Intent myIntent = new Intent(this, PostInSectionActivity.class);
                startActivity(myIntent);
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
