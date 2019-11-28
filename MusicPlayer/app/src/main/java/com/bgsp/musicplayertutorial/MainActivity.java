package com.bgsp.musicplayertutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    LinearLayout song1, song2;
    TextView playing, volume;
    SeekBar playingSB, volumeSB;
    Button play,pause,stop;

    MediaPlayer song1MP, song2MP;

    int lastPlayed = 0;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        song1 = findViewById(R.id.song_1);
        song2 = findViewById(R.id.song_2);
        playing = findViewById(R.id.playing_txt);
        volume = findViewById(R.id.volume_txt);
        playingSB = findViewById(R.id.playing_seekbar);
        volumeSB = findViewById(R.id.volume_seekbar);
        play = findViewById(R.id.play_btn);
        stop = findViewById(R.id.stop_btn);
        pause = findViewById(R.id.pause_btn);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        song1MP = MediaPlayer.create(this, R.raw.song1);
        song2MP = MediaPlayer.create(this, R.raw.song2);

        volumeSB.setMax(maxVolume);
        volumeSB.setProgress(currVolume);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(song1MP.isPlaying())
                {
                    playingSB.setProgress(song1MP.getCurrentPosition());
                }else if(song2MP.isPlaying())
                {
                    playingSB.setProgress(song2MP.getCurrentPosition());
                }
            }
        }, 0, 1000);


        playingSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                song1MP.pause();
                song2MP.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(lastPlayed == 0)
                {
                    song1MP.start();
                    song1MP.seekTo(seekBar.getProgress());
                }else
                {
                    song2MP.start();
                    song2MP.seekTo(seekBar.getProgress());

                }
            }
        });

        volumeSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                volume.setText("Playing (" + progress + ")");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        song1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(song2MP.isPlaying())
                {
                    song2MP.stop();
                }

                song1MP.start();
                playing.setText("Playing Song 1");
                lastPlayed = 0;
                playingSB.setMax(song1MP.getDuration());
            }
        });


        song2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(song1MP.isPlaying())
                {
                    song1MP.stop();

                }

                song2MP.start();
                playing.setText("Playing Song 2");
                lastPlayed = 1;
                playingSB.setMax(song2MP.getDuration());
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(song1MP.isPlaying())
                {
                    song1MP.pause();
                }else
                {
                    song2MP.pause();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(song1MP.isPlaying())
                {
                    song1MP.stop();
                    song1MP = MediaPlayer.create(getApplicationContext(), R.raw.song1);


                }else if(song2MP.isPlaying())
                {
                    song2MP.stop();
                    song2MP = MediaPlayer.create(getApplicationContext(), R.raw.song2);
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if( lastPlayed == 0)
               {
                   song1MP.start();
               }else
               {
                   song2MP.start();
               }
            }
        });

    }
}
