package com.puns.punsgame;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Maciej Szalek on 2018-05-13.
 */

public class Dialogs extends Activity{

    private Context mContext;
    private DBHelper dbHelper;
    private ExpandableListAdapter expandableListAdapter;

    public Dialogs(Context context){
        this.mContext = context;
    }

    public void deleteAllDataDialogBuilder(final ExpandableListView expListView,
                                           final List<String> listCategory,
                                           final HashMap<String, List<String>> listPun){
        AlertDialog.Builder dialogClose = new AlertDialog.Builder(mContext);
        dialogClose.setTitle(R.string.remove_all);
        dialogClose.setMessage(R.string.remove_all_message);
        dialogClose.setCancelable(true);
        dialogClose.setNegativeButton(R.string.finish_game_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogClose.setPositiveButton(R.string.finish_game_ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper = new DBHelper(mContext);
                dbHelper.deleteAllData();
                listCategory.clear();
                listPun.clear();
                expandableListAdapter = new ExpandableListAdapter(mContext, listCategory, listPun);
                expListView.setAdapter(expandableListAdapter);
            }
        });
        AlertDialog dialog = dialogClose.create();
        dialog.show();
    }

    public void editPunDialogBuilder(final String punCategory, final String punPassword,
                                     final ExpandableListView expListView,
                                     final List<String> listCategory,
                                     final HashMap<String, List<String>> listPun){
        final AlertDialog.Builder dialogEdit = new AlertDialog.Builder(mContext);
        dialogEdit.setTitle(R.string.edit_delete);
        dialogEdit.setMessage(mContext.getResources().getString(R.string.category)
            + " " + punCategory + "\n"
            + mContext.getResources().getString(R.string.password) + " " + punPassword);
        dialogEdit.setCancelable(true);
        dialogEdit.setNeutralButton(R.string.finish_game_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogEdit.setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(mContext, NewPunActivity.class);
                intent.putExtra("INTENT_CHECK", "EDIT");
                intent.putExtra("CATEGORY", punCategory);
                intent.putExtra("PASSWORD", punPassword);
                mContext.startActivity(intent);
            }
        });
        dialogEdit.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper = new DBHelper(mContext);
                dbHelper.deletePun(punCategory, punPassword);
                expandableListAdapter = new ExpandableListAdapter(mContext, listCategory, listPun);
                expListView.setAdapter(expandableListAdapter);
            }
        });
        AlertDialog dialog = dialogEdit.create();
        dialog.show();
    }

    public void finishGameDialogBuilder(){
        AlertDialog.Builder dialogClose = new AlertDialog.Builder(mContext);
        dialogClose.setTitle(R.string.finish_game_dialog_title);
        dialogClose.setMessage(R.string.finish_game_dialog_message);
        dialogClose.setCancelable(true);
        dialogClose.setNegativeButton(R.string.finish_game_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogClose.setPositiveButton(R.string.finish_game_ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((Activity) mContext).finish();
            }
        });
        AlertDialog dialog = dialogClose.create();
        dialog.show();
    }

    public void setTimerDialogBuilder(){

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor sharedEditor = sharedPreferences.edit();

        final int position = (sharedPreferences.getInt("TIMER", 1)) - 1;
        final int[] game_time = {0};
        final CharSequence[] time = {"1:00", "2:00", "3:00", "4:00"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle(R.string.set_timer_in_minutes);
        builder.setNegativeButton(R.string.finish_game_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.finish_game_ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(game_time[0]){
                    case 0:
                        sharedEditor.putInt("TIMER", 1);
                        sharedEditor.commit();
                        break;
                    case 1:
                        sharedEditor.putInt("TIMER", 2);
                        sharedEditor.commit();
                        break;
                    case 2:
                        sharedEditor.putInt("TIMER", 3);
                        sharedEditor.commit();
                        break;
                    case 3:
                        sharedEditor.putInt("TIMER", 4);
                        sharedEditor.commit();
                        break;
                }
            }
        });
        builder.setSingleChoiceItems(time, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                game_time[0] = item;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
