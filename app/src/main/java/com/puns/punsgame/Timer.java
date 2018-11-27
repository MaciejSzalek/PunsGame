package com.puns.punsgame;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Timer extends AppCompatActivity {

    private TextView timerTextView;

    public CountDownTimer countDownTimer;
    public DBHelper dbHelper;
    public Pun pun;
    public Integer time;
    private static final String ID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        timerTextView = findViewById(R.id.timer_text_view);

        dbHelper = new DBHelper(Timer.this);
        pun = dbHelper.getGameTimeSql(ID);
        time = pun.getGameTime() * 60000;

        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds =(int) millisUntilFinished/1000;
                int minutes = seconds / 60;
                seconds = seconds % 60;

                DecimalFormat decimalFormat = new DecimalFormat("#00");
                timerTextView.setText(String.valueOf(decimalFormat.format(minutes))
                        + ":" + String.valueOf(decimalFormat.format(seconds)));
            }

            @Override
            public void onFinish() {
                playAlarm();
                finish();
            }
        }.start();

    }
    private void playAlarm(){
        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(Timer.this, alarm);
        ringtone.play();
    }
}
