package com.puns.punsgame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Maciej Szalek on 2018-05-13.
 */

public class Dialogs extends Activity{

    private Context mContext;
    private Pun pun;
    private Integer punTime;

    public Dialogs(Context context){
        this.mContext = context;
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
                        break;
                    case 1:
                        pun = new Pun();
                        pun.setGameTime(2);
                        break;
                    case 2:
                        pun = new Pun();
                        pun.setGameTime(3);
                        break;
                    case 3:
                        pun = new Pun();
                        pun.setGameTime(4);
                        break;
                }
                Toast.makeText(mContext, mContext.getResources().getString(R.string.game_time)
                        + " " + Integer.toString(pun.getGameTime()) , Toast.LENGTH_SHORT ).show();
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
