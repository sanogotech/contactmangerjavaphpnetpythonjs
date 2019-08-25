package com.mobile.rodolpheagnero.contactmanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.rodolpheagnero.contactmanager.R;
import com.mobile.rodolpheagnero.contactmanager.helpers.AlertDialogManager;
import com.mobile.rodolpheagnero.contactmanager.helpers.AppConstants;
import com.mobile.rodolpheagnero.contactmanager.helpers.ConnectInternet;
import com.mobile.rodolpheagnero.contactmanager.helpers.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;

public class ViewContactActivity extends AppCompatActivity {

    EditText editTextId,editTextnom,editTextprenom,editTexttelephone,editTextemail,editTextadresse;
    TextView textViewTitle;
    Button buttonContact;

    private ProgressDialog pDialog;

    AlertDialogManager alert= new AlertDialogManager();

    com.mobile.rodolpheagnero.contactmanager.helpers.HttpUtils HttpUtils=new HttpUtils();

    private static String url_add="contact/add";
    private static String url_edit="contact/update";

    private static final String TAG_SUCCESS="201";

    Boolean isConnectInternet=false; //valeur de retour par defaut de la vérification de la connexion à internet
    ConnectInternet conexion ; //appel de la class de connexion

    private  String res = "true";
    private  String id = "";
    private  int codeResponse = 200;

    private static String url_delete="contact/delete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        //permet d'afficher la barre barre d'outil
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //permet de revenir à l'activité précedente
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialisation des champs de saisie
        editTextId =(EditText)findViewById(R.id.edit_id);
        textViewTitle =(TextView)findViewById(R.id.textView1);

        editTextnom =(EditText)findViewById(R.id.edit_nom);
        editTextprenom =(EditText)findViewById(R.id.edit_prenom);
        editTexttelephone =(EditText)findViewById(R.id.edit_telephone);
        editTextemail =(EditText)findViewById(R.id.edit_mail);
        editTextadresse =(EditText)findViewById(R.id.edit_adresse);

        buttonContact =(Button)findViewById(R.id.delete);

        //récupération des valeurs au clic de la listview dans l'activité de liste des contacts
        editTextId.setText(getIntent().getStringExtra("id"));
        editTextnom.setText(getIntent().getStringExtra("nom"));
        editTextprenom.setText(getIntent().getStringExtra("prenom"));
        editTexttelephone.setText(getIntent().getStringExtra("telephone"));
        editTextemail.setText(getIntent().getStringExtra("email"));
        editTextadresse.setText(getIntent().getStringExtra("adresse"));

        textViewTitle.setText(textViewTitle.getText()+" N° : "+editTextId.getText());

        id = editTextId.getText().toString();

        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                /*----------------suppression de la donnée (contact en fonction de l'ID---------------------*/

                conexion = new ConnectInternet(ViewContactActivity.this); //instance class connectinternet
                isConnectInternet = conexion.isOnline(); //get value is connect

                if(isConnectInternet) { //if connect internet exist

                        //permet de créer une boite de dialogue pour confirmer l'action avant de continuer ou de poursuivre
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewContactActivity.this);

                        builder.setTitle(R.string.app_name); //permet de modifier le titre de la boite de dialogue
                        builder.setMessage("Voulez-vous vraiment supprimer cette donnée ? \n Cette opération supprimera définitivement la donnée"); //permet de modifier le texte donc le corps de la boite de dialogue

                        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) { //si la reponse de l'utilisateur est oui alors on éxecute l'action demandée par celui-ci

                                    new ViewContactActivity.DeleteContact().execute(AppConstants.BASE_URL + url_delete+"/"+id);//on exécute l'action

                            }

                        });

                        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() { // si non on annule l'action de l'utilisateur

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Snackbar.make(view, "Action annulée par l'utilisateur", Snackbar.LENGTH_LONG)
                                        .setActionTextColor(Color.RED)
                                        .setAction("Action", null).show();


                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                else{

                    Snackbar.make(view, "Vous n'êtes pas connecté à internet. Veuillez réessayer plutard!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* methode pour convertir les données postées ou envoyée au server distant en inputStream
    *
    * @return
    * */

    private String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public String DELETE(String url) {

        String result = "";
        try {
            int TIMEOUT_MILLISEC = 100000; // = 10 seconds
            HttpClient httpClient = new DefaultHttpClient();
            HttpDelete httpDelete=new HttpDelete(url);

            // 7. Définissez certains en-têtes pour informer le serveur du type de contenu
            httpDelete.setHeader("Accept", "application/json");
            httpDelete.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpClient.execute(httpDelete);

            codeResponse = httpResponse.getStatusLine().getStatusCode();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // 10. retour résultat
        if(codeResponse == 200 || codeResponse == 204) {
            res = "true";
        }
        else {
            res = "false";
        }

        return result;
    }
    /*
* cette methode permet de récupérer les données sur le serveur distant
*
* @return
* */
    private class DeleteContact extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() { //methode permet de faire patienter l'utilisateur pendant le processus d'enregustrement du nouveau contact
            super.onPreExecute();

            pDialog = new ProgressDialog(ViewContactActivity.this);
            pDialog.setMessage("Suppression des données en cours. Veuillez patienter ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            return DELETE(urls[0]);
        }
        // onPostExecute affiche les résultats de the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();//fermeture du patienteur

            if(res == "false")
                Toast.makeText(getBaseContext(), "Impossible de supprimer la donnée!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getBaseContext(), "La donnée a été supprimée avec succès!", Toast.LENGTH_LONG).show();

            //si tout s'est bien déroulé, on rédirige à la liste des contacts et on termine l'activité de suppresionn et visualisation de contact
            Intent intent = new Intent(ViewContactActivity.this, ListContactActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
