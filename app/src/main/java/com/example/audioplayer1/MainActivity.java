package com.example.audioplayer1;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView playerPosition,playerDuration;
    SeekBar seekBar;
    ImageView btRew,btPlay,btPause,btFf;

    MediaPlayer mediaPlayer;
    Handler handler=new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerPosition=findViewById(R.id.player_position);
        playerDuration=findViewById(R.id.player_duration);
        seekBar=findViewById(R.id.seek_bar);
        btRew=findViewById(R.id.bt_rew);
        btFf=findViewById(R.id.bt_ff);
        btPlay=findViewById(R.id.bt_play);
        btPause=findViewById(R.id.pause);


        mediaPlayer= MediaPlayer.create(this,R.raw.vaathi);

        runnable =new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());

                handler.postDelayed(this,500);
            }
        };
        int duration =mediaPlayer.getDuration();

        String sDuration=convertFormat(duration);

        playerDuration.setText(sDuration);

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btPlay.setVisibility(View.GONE);

                btPause.setVisibility(View.VISIBLE);

                mediaPlayer.start();

                seekBar.setMax(mediaPlayer.getDuration());

                handler.postDelayed(runnable,0);
            }
        });

        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btPause.setVisibility(View.GONE);

                btPlay.setVisibility(View.VISIBLE);

                mediaPlayer.pause();

                handler.removeCallbacks(runnable);


            }
        });

        btFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition=mediaPlayer.getCurrentPosition();

                int duration=mediaPlayer.getDuration();

                if(mediaPlayer.isPlaying() && duration!=currentPosition){
                    currentPosition=currentPosition+5000;

                    playerPosition.setText(convertFormat(currentPosition));

                    mediaPlayer.seekTo(currentPosition);
                }


            }
        });


        btRew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition =mediaPlayer.getCurrentPosition();

                if(mediaPlayer.isPlaying() && currentPosition >5000){

                    currentPosition=currentPosition-5000;

                    playerPosition.setText(convertFormat(currentPosition));

                    mediaPlayer.seekTo(currentPosition);

                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);

                }
                playerPosition.setText(convertFormat(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                btPause.setVisibility(View.GONE);

                btPlay.setVisibility(View.VISIBLE);

                mediaPlayer.seekTo(0);
            }
        });

    }



    private String convertFormat(int duration) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),TimeUnit.MILLISECONDS.toSeconds(duration)- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

    }
}

