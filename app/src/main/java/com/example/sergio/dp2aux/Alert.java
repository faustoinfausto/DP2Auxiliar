package com.example.sergio.dp2aux;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Alert {
    public static void generar_alerta(Context contexto, String mensaje, String titulo){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(contexto);
        alertDialogBuilder.setMessage(mensaje)
                .setTitle(titulo);
        //alertDialogBuilder.setNegativeButton("Ok", null);
        alertDialogBuilder.setNegativeButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
