package com.example.peter.yarr;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadCompleteListener {


    String urlBefore = "http://www.reddit.com/r/";
    String urlAfter = ".json";

    //Media player will play default ringtone while data is loading
    MediaPlayer player;

    //A boolean to flag when the examples are deleted
    Boolean examplesDeleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        examplesDeleted = false;

        Button go = (Button) findViewById(R.id.btnGo);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Plays a ring tone while the content is being downloaded
                player = MediaPlayer.create(MainActivity.this,Settings.System.DEFAULT_RINGTONE_URI);
                player.start();

                //Deletes the subreddit exaples
                if (!examplesDeleted) {
                    TextView examples = (TextView) findViewById(R.id.lblSubredditExamples);
                    ((ViewGroup) examples.getParent()).removeView(examples);
                    examplesDeleted = true;
                }

                EditText subreddit = (EditText)findViewById(R.id.tbSubreddit);
                String sub = subreddit.getText().toString();
                if (!sub.equals(null)){
                    RedditDownloader rd = new RedditDownloader(MainActivity.this, MainActivity.this);
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
        listView.setAdapter(new RedditAdapter(this, p));

        if (player!= null && player.isPlaying())
        {
            player.stop();
            player.reset();
        }
    }
}
