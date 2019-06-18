package com.example.omaradel.mymusicpalyer;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.security.Provider;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import dyanamitechetan.vusikview.VusikView;


import static com.example.omaradel.mymusicpalyer.MainActivity.duration;
import static com.example.omaradel.mymusicpalyer.MainActivity.musicView;
import static com.example.omaradel.mymusicpalyer.MainActivity.playButton;
import static com.example.omaradel.mymusicpalyer.MainActivity.seekBar;
import static com.example.omaradel.mymusicpalyer.MainActivity.timer;
import static com.example.omaradel.mymusicpalyer.MainActivity.valumbar;

public class myservies extends Service {
private MediaPlayer player;

    private int realtimeLength;
    private int mediaFileLength;
    int playPosition;
    String midiapath="sdcard/ncs.mp3";
    final Handler handler = new Handler();
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override


    public IBinder onBind(Intent intent) {

        return null;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.isPlaying())
                {
                    player.start();
                    playButton.setImageResource(R.drawable.ic_stop);

                }
                else
                {
                    player.pause();
                    playButton.setImageResource(R.drawable.ic_play);



                }
                musicView.start();
                seekBar.setMax(99); // 100% (0~99)
                realtimeLength=player.getCurrentPosition();
                mediaFileLength = player.getDuration();
                duration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(mediaFileLength),
                        TimeUnit.MILLISECONDS.toSeconds(mediaFileLength) -
                                TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaFileLength))));

                updateSeekBar();
            }
        });


        return  START_NOT_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player=new MediaPlayer();
        player = MediaPlayer.create(this,R.raw.ncs);
        player.setVolume(0.5f,0.5f);

//

player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Uri uri=Uri.parse(midiapath);
//        try {
//            player.setDataSource(getApplicationContext(),uri);
//            player.prepare();
//            player.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
      //  player=MediaPlayer.create(getApplicationContext(),Uri.parse(Environment.));

//        player.setLooping(true);



    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        player.pause();
    }
    @SuppressLint("ClickableViewAccessibility")
    private void updateSeekBar() {
        seekBar.setProgress((int) (((float) player.getCurrentPosition() / mediaFileLength) * 100));
        if (player.isPlaying()) {
            Runnable updater = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                   realtimeLength += 1000; // declare 1 second
                    timer.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(realtimeLength),
                            TimeUnit.MILLISECONDS.toSeconds(realtimeLength) -
                                    TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(realtimeLength))));

                }

            };
            seekBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                        SeekBar seekBar = (SeekBar)v;
                         playPosition = (mediaFileLength/100)*seekBar.getProgress();
                        realtimeLength=playPosition;
                        player.seekTo(playPosition);

                    return false;
                }
            });
            valumbar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            float valumnum=progress/100f;
                            player.setVolume(valumnum,valumnum);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    }
            );

            handler.postDelayed(updater, 1000);

        }
    }
}