package com.puns.punsgame;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Maciej Szalek on 2018-05-13.
 */

public class Dialogs extends Activity{

    private Context mContext;

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
}
