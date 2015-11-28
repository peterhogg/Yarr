package com.example.peter.yarr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadCompleteListener {


    String urlBefore = "http://www.reddit.com/r/";
    String urlAfter = ".json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DownloadCompleteListener listener = this;

        Button go = (Button) findViewById(R.id.btnGo);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText subreddit = (EditText)findViewById(R.id.tbSubreddit);
                String sub = subreddit.getText().toString();
                if (!sub.equals(null)){
                    RedditDownloader rd = new RedditDownloader(listener);
                    try{
                        URL url = new URL(urlBefore + sub +urlAfter);
                        rd.execute(url);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void complete(ArrayList<Post> p){
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new RedditAdapter(this,p));
    }
}
