package com.puns.punsgame;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

/**
 * Created by Maciej Szalek on 2018-05-13.
 */

public class Dialogs extends Activity{

    private Context mContext;
    private Pun pun;

    public Dialogs(Context context){
        this.mContext = context;
    }

    public void editPunDialogBuilder(String punCategory, String punPassword){
        final AlertDialog.Builder dialogEdit = new AlertDialog.Builder(mContext);
        dialogEdit.setTitle(R.string.edit_delete);
        dialogEdit.setMessage(R.string.category + punCategory + "\n"
                + R.string.password + punPassword);
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
            }
        });
        dialogEdit.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(mContext, NewPunActivity.class);
                startActivity(intent);
                dialog.dismiss();
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
        final DBHelper dbHelper = new DBHelper(mContext);
        final String ID = "1";
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
                        pun = new Pun();
                        pun.setGameTime(1);
                        dbHelper.updateGameTime(ID, pun);
                        break;
                    case 1:
                        pun = new Pun();
                        pun.setGameTime(2);
                        dbHelper.updateGameTime(ID, pun);
                        break;
                    case 2:
                        pun = new Pun();
                        pun.setGameTime(3);
                        dbHelper.updateGameTime(ID, pun);
                        break;
                    case 3:
                        pun = new Pun();
                        pun.setGameTime(4);
                        dbHelper.updateGameTime(ID, pun);
                        break;
                }
            }
        });
        builder.setSingleChoiceItems(time, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                game_time[0] = item;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
