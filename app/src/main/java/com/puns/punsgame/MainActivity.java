package com.puns.punsgame;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public ImageButton gameButton;
    public ImageButton setTimerButton;
    public ImageButton editorButton;

    private Pun pun;
    private Dialogs mDialogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gameButton = findViewById(R.id.game_button);
        setTimerButton = findViewById(R.id.set_timer_button);
        editorButton = findViewById(R.id.editor_button);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        toolbar.setTitle(R.string.puns_game);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameActivity();
            }
        });
        setTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetGameTimeDialog();
                pun = new Pun();
                Toast.makeText(MainActivity.this, getResources().getString(R.string.game_time)
                        + " " + Integer.toString(pun.getGameTime()) , Toast.LENGTH_SHORT ).show();
            }
        });
        editorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditorActivity();
            }
        });

    }
    public void goToGameActivity(){
        Intent intent = new Intent(MainActivity.this, Game.class);
        startActivity(intent);
    }
    public void goToEditorActivity(){
        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
        startActivity(intent);
    }
    public void showSetGameTimeDialog(){
        mDialogs = new Dialogs(MainActivity.this);
        mDialogs.setTimerDialogBuilder();
    }
}
