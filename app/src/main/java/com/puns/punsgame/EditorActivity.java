package com.puns.punsgame;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditorActivity extends AppCompatActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listCategory;
    private HashMap<String, List<String>> listPun;

    public Uri uri;
    private String path;
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

        //get list view
        expListView = findViewById(R.id.exp_list);
        //set category and puns
        setPunsList();
        listAdapter = new ExpandableListAdapter(this, listCategory, listPun);
        expListView.setAdapter(listAdapter);

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
                Toast.makeText(EditorActivity.this, "delete pun", Toast.LENGTH_SHORT).show();
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
                    FileReader fileReader = new FileReader(path);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    try {
                        String csvLine;
                        while((csvLine = bufferedReader.readLine()) != null){
                            String[] column = csvLine.split("|");
                            String category = column[0];
                            String pun = column[1];
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Toast.makeText(EditorActivity.this, "path:" + path,
                        Toast.LENGTH_SHORT).show();

            }

        }
    }
    private void setPunsList(){
        listCategory = new ArrayList<>();
        listPun = new HashMap<>();

        listCategory.add("cat 1");
        listCategory.add("cat 2");
        listCategory.add("cat 3");

        List<String> child_1 = new ArrayList<>();
        child_1.add("cat 1: child 1");
        child_1.add("cat 1: child 2");
        child_1.add("cat 1: child 3");

        List<String> child_2 = new ArrayList<>();
        child_2.add("cat 2: child 1");
        child_2.add("cat 2: child 2");
        child_2.add("cat 2: child 3");

        List<String> child_3 = new ArrayList<>();
        child_3.add("cat 3: child 1");
        child_3.add("cat 3: child 2");
        child_3.add("cat 3: child 3");

        listPun.put(listCategory.get(0), child_1);
        listPun.put(listCategory.get(1), child_2);
        listPun.put(listCategory.get(2), child_3);
    }
    private void importExelToSQLite(Context context){

    }
}
