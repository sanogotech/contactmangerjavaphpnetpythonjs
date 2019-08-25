package com.mobile.rodolpheagnero.contactmanager.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Sms {

	//create attibute
	String phoneNo = "";
	String message = "";
	
	public Sms(String phoneNo, String message) {
		super();
		this.phoneNo = phoneNo;
		this.message = message;
	}

	public Sms() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getIMEI(Context context){

	    TelephonyManager mngr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE); 
	    String imei = mngr.getDeviceId();
	    return imei;

	}
  public void sendSMSMessage(Context context) {
      Log.i("Envoie SMS", "");	   
      
      try {
         SmsManager smsManager = SmsManager.getDefault();
         smsManager.sendTextMessage(phoneNo, null, message, null, null);
         Toast.makeText(context, "SMS envoyé avec succès.", Toast.LENGTH_LONG).show();
     
      } 
      
      catch (Exception e) {
         Toast.makeText(context, "L\' envoie du SMS a échoué, réessayez svp.", Toast.LENGTH_LONG).show();
         e.printStackTrace();
      }
      finally{
    	  
      }
   }
}
