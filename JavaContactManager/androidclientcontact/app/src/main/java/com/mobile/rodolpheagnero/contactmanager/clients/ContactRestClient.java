package com.mobile.rodolpheagnero.contactmanager.clients;

import android.content.Context;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

public class ContactRestClient {
    private static final String BASE_URL = " http://192.168.8.100:8080/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static AsyncHttpClient syncHttpClient= new SyncHttpClient();

    public static void setCookieStore(PersistentCookieStore cookieStore) {
        getClient().setCookieStore(cookieStore);
    }

    /**
     * @return an async client when calling from the main thread, otherwise a sync client.
     */
    public static AsyncHttpClient getClient()
    {
        // Return the synchronous HTTP client when the thread is not prepared
        if (Looper.myLooper() == null)
            return syncHttpClient;
        return client;
    }

    public static void get(Context context, String url, Header[] headers, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {

            client.get(context, getAbsoluteUrl(url), headers, params, responseHandler);

    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}