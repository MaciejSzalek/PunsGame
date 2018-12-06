package com.puns.punsgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends AppCompatActivity {

    public TextView categoryTextView;
    public TextView passwordTextView;
    public ImageButton randomButton;
    public ImageButton startButton;

    private DBHelper dbHelper;
    private Dialogs mDialogs;

    private List<Integer> idList = new ArrayList<>();
    private List<Integer> idUsedList = new ArrayList<>();

    private String category;
    private String password;

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
        getIdListSQL();

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

        randomButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Integer id = getRandomId();
                if(!idUsedList.contains(id)){
                    getPuns(id);
                    categoryTextView.setText(getResources().getString(R.string.category)
                            + "\n" + category + "\n");
                    passwordTextView.setText(getResources().getString(R.string.password)
                            + "\n" + password);
                    idUsedList.add(id);
                }else{
                    getRandomId();
                }
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goToTimerActivity();
            }
        });

    }
    private void goToTimerActivity(){
        Intent intent = new Intent(Game.this, Timer.class);
        startActivity(intent);
    }
    private void getPuns(Integer id){
        Pun pun = dbHelper.getPunsDataSql(Integer.toString(id));
        category = pun.getCategory();
        password = pun.getPassword();
    }
    private Integer getRandomId(){
        Random random = new Random();
        int listSize = idList.size();
        int randomIndex = random.nextInt(listSize);
        return idList.get(randomIndex);
    }
    private void getIdListSQL(){
        List<Pun> punList = dbHelper.getAllSqlData();
        for(Pun pun: punList){
            Integer idInt = pun.getId();
            idList.add(idInt);
        }
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
