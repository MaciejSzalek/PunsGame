package com.puns.punsgame;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EditorActivity extends AppCompatActivity {

    public ScrollView scrollView;
    public TextView textView;

    private DBHelper dbHelper;
    public Uri uri;

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listCategory = new ArrayList<>();
    private List<String> childList = new ArrayList<>();
    private HashMap<String, List<String>> listPun = new HashMap<>();

    private String path;
    private String category;
    private String punPassword;
    private final int REAL_PATH_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        scrollView = findViewById(R.id.editor_scroll);
        textView = findViewById(R.id.editor_textView);
        expListView = findViewById(R.id.exp_list);

        dbHelper = new DBHelper(EditorActivity.this);
        getCategoryFromSql();
        if(listCategory.size() > 0 ){
            Collections.sort(listCategory);
            listAdapter = new ExpandableListAdapter(this, listCategory, listPun);
            expListView.setAdapter(listAdapter);
        }

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                category = listCategory.get(groupPosition);
                getPunsFromCategory(category);
                return false;
            }
        });
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_pun:
                getExcelFilePath();
                Toast.makeText(EditorActivity.this, "add pun", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete_pun:
                if(listCategory == null){
                    Toast.makeText(EditorActivity.this, "pusta", Toast.LENGTH_SHORT).show();
                }else{
                    getPunsFromCategory("Film");
                    scrollView.setVisibility(View.VISIBLE);
                    for(int i = 0; i<childList.size(); i++){
                        textView.append("Child list: " + childList.get(i) + "\n");
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void getExcelFilePath(){
        Intent intent = new Intent();
        intent.setType("gagt/sdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REAL_PATH_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REAL_PATH_REQUEST){
                uri = data.getData();
                path = uri.getPath();
                try {
                    BufferedReader bufferedReader;
                    bufferedReader = new BufferedReader(new InputStreamReader(
                            new FileInputStream(path), Charset.forName("Cp1252")));
                    try {
                        String csvLine;
                        while((csvLine = bufferedReader.readLine()) != null){
                            String[] column = csvLine.split(";", 2);
                            category = column[0];
                            punPassword = column[1];

                            Pun pun = new Pun();
                            pun.setCategory(category);
                            pun.setPassword(punPassword);
                            dbHelper.addNewPun(pun, pun);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(EditorActivity.this, "File not found" ,
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(EditorActivity.this, "File not found" ,
                            Toast.LENGTH_SHORT).show();
                }
                getCategoryFromSql();
                Collections.sort(listCategory);
                listAdapter = new ExpandableListAdapter(this, listCategory, listPun);
                expListView.setAdapter(listAdapter);

                Toast.makeText(EditorActivity.this, "path:" + path,
                        Toast.LENGTH_SHORT).show();

            }

        }
    }
    private void getCategoryFromSql(){
        List<Pun> punListCategory = dbHelper.getAllCategory();
        for(Pun pun: punListCategory){
            category =  pun.getCategory();
            if(!listCategory.contains(category)){
                listCategory.add(category);
            }
        }
    }
    private void getPunsFromCategory(String category){
        List<Pun> punsList = dbHelper.getAllPunsFromCategory(category);
        childList = new ArrayList<>();
        for(Pun pun: punsList){
            punPassword = pun.getPassword();
            childList.add(punPassword);
        }
        Collections.sort(childList);
        listPun.put(category, childList);
    }
}
