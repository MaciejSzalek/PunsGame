package com.puns.punsgame;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Maci on 2018-11-13.
 */

public class Dialog extends Activity{

    public Dialog(){}

    public void closeGameDialogBuilder(Context context){
        AlertDialog.Builder dialogClose = new AlertDialog.Builder(context);
        dialogClose.setTitle("");
        dialogClose.setMessage("Czy na pewno chcesz zamknąć edytor ?\n" +
                "Utracisz wszystkie niezapisane dane.");
        dialogClose.setCancelable(true);
        dialogClose.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogClose.setPositiveButton("Zamknij", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = dialogClose.create();
        dialog.show();
    }
}
