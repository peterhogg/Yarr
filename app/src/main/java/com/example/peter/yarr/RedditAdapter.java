package com.example.peter.yarr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by peter on 27/11/15.
 */

public class RedditAdapter extends BaseAdapter {
    private Context context;
    ArrayList<Post> posts;
    public RedditAdapter(Context c, ArrayList<Post> p){
        this.context = c;
        posts = p;
    }

    public int getCount(){
        return posts.size();


    }
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            Post post = posts.get(i);
            TextView postTitle;
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.reddit_post, viewGroup, false);


            //Sets the text in the view
            postTitle = (TextView) view.findViewById(R.id.lblPostTitle);
            Log.d("Inflated title", post.getTitle() + "");
            postTitle.setText(post.getTitle());
        }
            return view;

    }
    public long getItemId(int i) {
        return i;
    }

    public Object getItem(int i) {
        return posts.get(i);
    }



}
