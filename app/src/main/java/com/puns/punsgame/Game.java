package com.puns.punsgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Game extends AppCompatActivity {

    private Dialogs mDialogs;
    public TextView categoryTextView;
    public TextView passwordTextView;
    public ImageButton randomButton;
    public ImageButton startButton;

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
