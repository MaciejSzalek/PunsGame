package com.puns.punsgame;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Game extends AppCompatActivity {

    public TextView categoryTextView;
    public TextView passwordTextView;
    public ImageButton randomButton;
    public ImageButton startButton;

    private DBHelper dbHelper;
    private Dialogs mDialogs;
    private Pun pun;

    private static final Integer ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        categoryTextView = findViewById(R.id.category);
        passwordTextView = findViewById(R.id.password);
        randomButton = findViewById(R.id.random_button);
        startButton = findViewById(R.id.start_button);

        dbHelper = new DBHelper(Game.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        toolbar.setTitle(R.string.puns_game);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFinishGameAlertBuilder();
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pun = dbHelper.getGameTimeSql(Integer.toString(ID));
                Toast.makeText(Game.this, getResources().getString(R.string.game_time)
                        + " " + Integer.toString(pun.getGameTime()) , Toast.LENGTH_SHORT ).show();
            }
        });

    }
    private void createFinishGameAlertBuilder(){
        mDialogs = new Dialogs(Game.this);
        mDialogs.finishGameDialogBuilder();
    }
    @Override
    public void onBackPressed(){
        createFinishGameAlertBuilder();
    }

}
