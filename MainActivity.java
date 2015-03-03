package com.example.sashank.voip;

import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private MediaRecorder myAudioRecorder;
    private String outputFile=null;
    private Button start, stop, play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.startButton);
        stop = (Button) findViewById(R.id.stopButton);
        play = (Button) findViewById(R.id.playButton);

        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile= Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.reset();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

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

    public void start(View view){
        try{
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        }
        catch (IOException e){
            System.out.println("Error at 1");
            e.printStackTrace();
        }
        catch(IllegalStateException e){
            System.out.println("Error at 2");
            e.printStackTrace();
        }

        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Started Recording", Toast.LENGTH_SHORT).show();
    }

    public void stop(View view){
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder=null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Stopped Recording", Toast.LENGTH_SHORT).show();
    }

    public void play(View view) throws IOException {
        MediaPlayer myMediaPlayer = new MediaPlayer();
        myMediaPlayer.setDataSource(outputFile);
        myMediaPlayer.prepare();
        myMediaPlayer.start();
        Toast.makeText(getApplicationContext(), "Playing recorded", Toast.LENGTH_SHORT).show();

    }
}































