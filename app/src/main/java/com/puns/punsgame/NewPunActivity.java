package com.puns.punsgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewPunActivity extends AppCompatActivity {

    ListView categoryListView;
    EditText categoryEditText;
    EditText passwordEditText;

    DBHelper dbHelper;
    ArrayList<String> listCategory = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    String category;
    String punPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_pun_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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

        categoryListView = findViewById(R.id.new_category_list);
        categoryEditText = findViewById(R.id.category_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        dbHelper = new DBHelper(NewPunActivity.this);
        getCategoryFromSql();

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryEditText.setText(listCategory.get(position));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirm_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.confirm_ic:
                category = categoryEditText.getText().toString();
                punPassword = passwordEditText.getText().toString();
                if(TextUtils.isEmpty(category)){
                    Toast.makeText(this, "Category can not be empty", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(punPassword)){
                    Toast.makeText(this, "Password can not be empty", Toast.LENGTH_SHORT).show();
                }else{
                    Pun pun = new Pun();
                    pun.setCategory(category);
                    pun.setPassword(punPassword);
                    dbHelper.addNewPun(pun, pun);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        Collections.sort(listCategory);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_white_text, listCategory);
        categoryListView.setAdapter(arrayAdapter);
    }
}
