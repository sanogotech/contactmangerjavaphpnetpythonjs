package com.mobile.rodolpheagnero.contactmanager.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectInternet {

		private Context context;
			
			public ConnectInternet(Context context)
			{
				this.context=context;
			}
			
			public boolean VerifyIsInternet(){
				
				ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connectivity != null) 
		        {
		            NetworkInfo[] info = connectivity.getAllNetworkInfo();
		            if (info != null) 
		                for (int i = 0; i < info.length; i++) 
	                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                    {
	                        return true;
	                    }

		        }
				
				return false;
				
			}

	public boolean isOnline(){
		ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=cm.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		else {
			return info.isConnectedOrConnecting();
		}
	}

}
