package com.example.peter.yarr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
            final Post post = posts.get(i);
            TextView postTitle;
            TextView postScore;
            TextView postAuthor;
            TextView postLink;

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.reddit_post, viewGroup, false);

            postTitle = (TextView) view.findViewById(R.id.lblTitle);
            postScore = (TextView) view.findViewById(R.id.lblScore);
            postAuthor = (TextView) view.findViewById(R.id.lblAuthor);
            postLink = (TextView) view.findViewById(R.id.lblLink);

            //Sets the text in the view

            /*
            Log.d("title", post.getTitle() + "");
            Log.d("author", post.getAuthor() + "");
            Log.d("score", post.getScore() + "");
            Log.d("url", post.getLink().toString() + "");
            */
            postTitle.setText(post.getTitle());
            postScore.setText(post.getScore() + "");
            postAuthor.setText(post.getAuthor());
            String link = post.getLink();
            if(link.length() > 40) {
                postLink.setText(link.substring(0, 40) + "...");
            }else {
                postLink.setText(link);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(post.getLink()));
                    context.startActivity(i);

                }
            });
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
