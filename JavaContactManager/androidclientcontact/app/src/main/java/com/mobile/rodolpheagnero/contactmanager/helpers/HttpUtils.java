package com.mobile.rodolpheagnero.contactmanager.helpers;

import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

/**
 * Created by VEONE on 08/08/2017.
 */

public class HttpUtils {
    private static final String BASE_URL = "http://192.168.8.100:8080/";

    private static AsyncHttpClient client = new AsyncHttpClient();
    // A SyncHttpClient is an AsyncHttpClient
    public static AsyncHttpClient syncHttpClient= new SyncHttpClient();

    public static void setCookieStore(PersistentCookieStore cookieStore) {
        getClient().setCookieStore(cookieStore);
    }

    /**
     * @return an async client when calling from the main thread, otherwise a sync client.
     */
    private static AsyncHttpClient getClient()
    {
        // Return the synchronous HTTP client when the thread is not prepared
        if (Looper.myLooper() == null)
            return syncHttpClient;
        return client;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
