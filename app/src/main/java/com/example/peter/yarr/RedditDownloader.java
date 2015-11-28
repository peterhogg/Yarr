package com.example.peter.yarr;

import android.os.AsyncTask;
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
public class RedditDownloader extends AsyncTask<URL, Void, List<Post>> {
    private DownloadCompleteListener listener = null;

    public RedditDownloader(DownloadCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Post> doInBackground(URL... params) {
        ArrayList<Post> posts = new ArrayList<>();
        BufferedReader reader = null;
        try {
            URL url = params[0];
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            String rawData = buffer.toString();

            reader.close();
            JSONArray data = new JSONArray(rawData);
            Post post = new Post();
            String title = data.getJSONObject(1).getJSONObject("data").getJSONObject("children").getJSONObject("0").getJSONObject("data").getString("title");
            Post p = new Post();
            p.setTitle(title);
            Log.d("title", title);
            posts.add(posts.size(), post);

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
    protected void onPostExecute(List<Post> result) {
        listener.complete(result);
    }
}

