package com.example.peter.yarr;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 27/11/15.
 */
public class RedditDownloader extends AsyncTask<URL, Void, ArrayList<Post>> {
    private DownloadCompleteListener listener;
    private Context context;

    public RedditDownloader(DownloadCompleteListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected ArrayList<Post> doInBackground(URL... params) {
        ArrayList<Post> posts = new ArrayList<>();
        BufferedReader reader = null;
        try {

            URL url = params[0];

            //Checks if the data exsists in the shared prefferences
            String rawData;
            rawData = PreferenceManager.getDefaultSharedPreferences(context).getString(url.toString(),null) ;

            if (rawData == null) {
                Log.d("Downloading", "Downloading New JSON Data");
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);

                rawData = buffer.toString();

                reader.close();

                //Saves the JSON data
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString(url.toString(), rawData).apply();
            }



            JSONObject data = new JSONObject(rawData);

            //Saves the post data into a list of posts
            JSONArray redditPosts = data.getJSONObject("data").getJSONArray("children");
            for (int i = 0; i < redditPosts.length(); i ++){
                Post post = new Post();
                post.setTitle(redditPosts.getJSONObject(i).getJSONObject("data").getString("title"));
                post.setAuthor(redditPosts.getJSONObject(i).getJSONObject("data").getString("author"));
                post.setScore(redditPosts.getJSONObject(i).getJSONObject("data").getInt("score"));
                post.setLink(redditPosts.getJSONObject(i).getJSONObject("data").getString("url"));
                posts.add(posts.size(), post);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return posts;
    }

    private String stripTags(String code) {
        return code; // for now
    }

    @Override
    protected void onPostExecute(ArrayList<Post> result) {
        listener.complete(result);
    }
}

