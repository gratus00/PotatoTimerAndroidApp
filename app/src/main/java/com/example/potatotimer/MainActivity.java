package com.example.potatotimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SeekBar timerSeekBar;
    TextView timerView;
    Boolean isActive = false;
    Button startButton;
    CountDownTimer countDownTimer;


    public void updateTimer(int i){
        int min = i / 60;
        int sec = i % 60;

        String secondString = Integer.toString(sec);

        if (secondString.length() < 2){
            secondString = "0" + secondString ;
        }

        timerView.setText(Integer.toString(min) + ":"+ secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timerSeekBar = (SeekBar) findViewById(R.id.timerBar);
        timerView = (TextView) findViewById(R.id.timeView);
        startButton = findViewById(R.id.startButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void buttonClicked(View view){
        if (isActive){
            timerSeekBar.setEnabled(true);
            countDownTimer.cancel();
            startButton.setText("Go");

        } else {
            timerSeekBar.setEnabled(false);
            startButton.setText("Stop");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {
                public void onTick(long millisUntilDone) {
                    updateTimer((int) millisUntilDone / 1000);
                }

                public void onFinish() {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    timerSeekBar.setEnabled(true);
                }
            }.start();
        }
        isActive = !isActive;
    }

}