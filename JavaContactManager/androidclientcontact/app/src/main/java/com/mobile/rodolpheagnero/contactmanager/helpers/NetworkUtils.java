package com.mobile.rodolpheagnero.contactmanager.helpers;

/**
 * Created by VEONE on 08/08/2017.
 */

/*
*helper ou util qui prmet de vérifier :
* Si le réseau est connecté.
* Si WiFi est connecté.
* Si les données mobiles sont connectées.
* Il utilise une API ancienne ou nouvelle selon la plate-forme en cours d'exécution:
*
* */

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;

public class NetworkUtils {

    public static boolean isConnected(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static boolean isWifiConnected(@NonNull Context context) {
        return isConnected(context, ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isMobileConnected(@NonNull Context context) {
        return isConnected(context, ConnectivityManager.TYPE_MOBILE);
    }

    private static boolean isConnected(@NonNull Context context, int type) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(type);
            return networkInfo != null && networkInfo.isConnected();
        } else {
            return isConnected(connMgr, type);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean isConnected(@NonNull ConnectivityManager connMgr, int type) {
        Network[] networks = connMgr.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = connMgr.getNetworkInfo(mNetwork);
            if (networkInfo != null && networkInfo.getType() == type && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }
}
