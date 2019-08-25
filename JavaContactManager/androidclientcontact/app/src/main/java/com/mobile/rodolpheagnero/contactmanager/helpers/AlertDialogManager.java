package com.mobile.rodolpheagnero.contactmanager.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager {

	public void showAlertDialog(Context context, String title, String message,
	        Boolean status) {
	    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
	    alertDialog.setTitle(title);
	    alertDialog.setMessage(message);

	    if (status != null)
	        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                alertDialog.dismiss();
	            }
	        });
	    alertDialog.show();
	}
}
