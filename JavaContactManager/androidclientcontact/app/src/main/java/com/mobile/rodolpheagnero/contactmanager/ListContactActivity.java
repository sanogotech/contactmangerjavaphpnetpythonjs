package com.mobile.rodolpheagnero.contactmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.mobile.rodolpheagnero.contactmanager.adapters.ContactAdapter;
import com.mobile.rodolpheagnero.contactmanager.clients.ContactRestClient;
import com.mobile.rodolpheagnero.contactmanager.helpers.ConnectInternet;
import com.mobile.rodolpheagnero.contactmanager.models.Contact;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.widget.EditText;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class ListContactActivity extends AppCompatActivity {

    private ListView contactList;
    private Boolean isConnectInternet=false; //valeur de retour par defaut de la vérification de la connexion à internet
    private ConnectInternet conexion ; //appel de la class de connexion

    private ProgressDialog pDialog;

    String res = "true";
    EditText editsearch;

    ArrayList<Contact> contactArray;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        //afficher la barre d'outils
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //permet de revenir à l'activité précedente
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        conexion = new ConnectInternet(ListContactActivity.this); //instance class connectinternet
        isConnectInternet = conexion.isOnline(); //get value is connect

        if(isConnectInternet) { //if connect internet exist

            getContacts();
             //new ListAllContact().execute();

        } else {

            Toast.makeText(getApplicationContext(), "Vous n'êtes connecter à internet. Veuillez réessayer plutard!", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_list_contact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ListContactActivity.this,NewContactActivity.class);
                startActivity(intent);
            }
        });

        editsearch = (EditText) findViewById(R.id.searchEditText);
        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                contactAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });






    }

    private void getContacts() {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        ContactRestClient.get(ListContactActivity.this, "contact/readall", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                         contactArray = new ArrayList<Contact>();
                         contactAdapter = new ContactAdapter(ListContactActivity.this, contactArray);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                contactAdapter.add(new Contact(response.getJSONObject(i)));
                                res = "true";
                            } catch (JSONException e) {
                                e.printStackTrace();
                                res = "false";
                                Log.d("Error", e.getMessage());
                            }
                        }

                        contactList = (ListView) findViewById(R.id.list_contacts);
                        contactList.setAdapter(contactAdapter);
                    }
                });
    }



    /*
  * cette methode permet de récupérer les données sur le serveur distant
  *
  * @return
  * */
    private class ListAllContact extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() { //methode permet de faire patienter l'utilisateur pendant le processus d'enregustrement du nouveau contact
            super.onPreExecute();

            pDialog = new ProgressDialog(ListContactActivity.this);
            pDialog.setMessage("Données en cours de chargement. Veuillez patienter ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

           getContacts();

            //résultat de retour
            return  res;
        }
        // onPostExecute affiche les résultats de the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();//fermeture du patienteur

            if(res == "false")
            Toast.makeText(getBaseContext(), "Impossible de récupérer les contacts!", Toast.LENGTH_LONG).show();
        }
    }



}
