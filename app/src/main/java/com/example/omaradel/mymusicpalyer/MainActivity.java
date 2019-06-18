package com.example.omaradel.mymusicpalyer;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import dyanamitechetan.vusikview.VusikView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static VusikView musicView;
    static ImageButton playButton;
    private Button pauseButton;
    public static SeekBar seekBar;
    public static SeekBar valumbar;
    private int mediaFileLength;
    private MediaPlayer mediaPlayer;
    static TextView timer;
    static TextView duration;
    private String CHANNEL_ID="0";
    private int count=0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  String midiapath="sdcard/ncs.mp3";
        setContentView(R.layout.activity_main);
musicView=findViewById(R.id.musicview);
        playButton=(ImageButton)findViewById(R.id.play);
        duration=(TextView)findViewById(R.id.duration);

        timer=findViewById(R.id.textimer);
       // NotificationGenerator.openNotification(getApplicationContext());
valumbar=findViewById(R.id.valumbar);
        seekBar = (SeekBar)findViewById(R.id.seekbar);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("mymusicpalyer")
                .setContentText("hey baby")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());
        startService(new Intent(this, myservies.class));

    }

    @Override
    public void onClick(View v) {
        if(v==playButton)
        {
            count++;
            if(count==1) {
                Toast.makeText(MainActivity.this, "play", Toast.LENGTH_LONG).show();


            }
        }

    }
}
