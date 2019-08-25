package com.mobile.rodolpheagnero.contactmanager;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobile.rodolpheagnero.contactmanager.helpers.*;
import com.mobile.rodolpheagnero.contactmanager.models.Contact;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;

//import org.apache.http.Header;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.message.BasicHeader;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.util.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class NewContactActivity extends AppCompatActivity {

    //SessionManager session; //class à importer pour la gestion des session
    private ProgressDialog pDialog;

    AlertDialogManager alert= new AlertDialogManager();

    HttpUtils HttpUtils=new HttpUtils();

    private static String url_add="contact/add";
    private static String url_edit="contact/update";

    private static final String TAG_SUCCESS="201";

    Boolean isConnectInternet=false; //valeur de retour par defaut de la vérification de la connexion à internet
    ConnectInternet conexion ; //appel de la class de connexion

    EditText editTextnom,editTextprenom,editTexttelephone,editTextemail,editTextadresse;
    TextView textViewId, textViewTitle;
    Button buttonContact;

    /*-------------déclaration des varaiables liées aux champs de saisie---------------------------*/
    String idContact;
    String sexeContact = "H"; //figer la valur de sexe le temps de gérer
    String nomContact = "";
    String prenomContact = "";
    String telContact = "";
    String emailContact = "";
    String adresseContact = "";

    //déclaration variable de retour asynchhttp pour l'envoi d'une notif
    static String resultat = "false";
    String res = "false";
    // création de la variable contact de type Contact
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        //permet d'afficher la barre barre d'outil
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //permet de revenir à l'activité précedente
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialisation des champs de saisie
        textViewId =(TextView)findViewById(R.id.id);
        textViewTitle =(TextView)findViewById(R.id.textView1);

        editTextnom =(EditText)findViewById(R.id.nom);
        editTextprenom =(EditText)findViewById(R.id.prenom);
        editTexttelephone =(EditText)findViewById(R.id.telephone);
        editTextemail =(EditText)findViewById(R.id.mail);
        editTextadresse =(EditText)findViewById(R.id.adresse);

        buttonContact =(Button)findViewById(R.id.add);

        //récupération des valeurs au clic du button edit dans l'activité de liste des contacts
        textViewId.setText(getIntent().getStringExtra("id"));
        editTextnom.setText(getIntent().getStringExtra("nom"));
        editTextprenom.setText(getIntent().getStringExtra("prenom"));
        editTexttelephone.setText(getIntent().getStringExtra("telephone"));
        editTextemail.setText(getIntent().getStringExtra("email"));
        editTextadresse.setText(getIntent().getStringExtra("adresse"));

        if(getIntent().getStringExtra("title") != null) textViewTitle.setText(getIntent().getStringExtra("title"));
        if(getIntent().getStringExtra("action") != null ) buttonContact.setText(getIntent().getStringExtra("action"));

        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                idContact = textViewId.getText().toString();
                sexeContact ="H";
                nomContact = editTextnom.getText().toString();
                prenomContact = editTextprenom.getText().toString();
                telContact = editTexttelephone.getText().toString();
                emailContact = editTextemail.getText().toString();
                adresseContact = editTextadresse.getText().toString();

                /*-----------------enregistrement des données saisies par l'utilisateur--------------------------*/

                conexion = new ConnectInternet(NewContactActivity.this); //instance class connectinternet
                isConnectInternet = conexion.isOnline(); //get value is connect

                if(isConnectInternet){ //if connect internet exist

                    if (validateField()) {

                        //permet de créer une boite de dialogue pour confirmer l'action avant de continuer ou de poursuivre
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewContactActivity.this);

                        builder.setTitle(R.string.app_name); //permet de modifier le titre de la boite de dialogue
                        builder.setMessage("Voulez-vous enregistrer cette donnée ?"); //permet de modifier le texte donc le corps de la boite de dialogue

                        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) { //si la reponse de l'utilisateur est oui alors on éxecute l'action demandée par celui-ci

                                if (idContact == null || idContact.isEmpty())
                                    new SaveContact().execute(AppConstants.BASE_URL+url_add);//on exécute l'action
                                else
                                    new SaveContact().execute(AppConstants.BASE_URL+url_edit+"/"+idContact);//on exécute l'action

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

                    else {
                        Snackbar.make(view, "Il existe des erreurs sur la page.\n Veuillez les corriger avant de continuer", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }


                }
                else{//if is connect internet then do that bellow
                    if (validateField()) {

                    }

                    else {
                        Snackbar.make(view, "Il existe des erreurs sur la page.\n Veuillez les corriger avant de continuer", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                }

            }


        });


    }//end create

    /*-------------------------------------------METHODES LOCALES-----------------------------------------------------*/

    /**
     * valider le formulaire de saisie
     *
     * @return
     */

    private boolean validateField() {
        boolean ret = true;

        if (!Validation.hasText(editTextnom, "Le nom est obligatoire"))
            ret = false;
        if (!Validation.hasText(editTextprenom, "Le prénom est obligatoire"))
            ret = false;
        if (!Validation.hasText(editTexttelephone, "Le téléphone est obligatoire"))
            ret = false;

        if (!Validation.hasText(editTextemail, "L\'adresse email est obligatoire"))
            ret = false;
        else {
            if (!isValidEmail(editTextemail.getText().toString()))
                ret = false;
        }

        if (!Validation.hasText(editTextadresse, "L\'adresse est obligatoire"))
            ret = false;


        return ret;
    }

    /**
     * valider l'adresse email saisie
     *
     * @return
     */
    public final boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * initialiser le formulaire de saisie
     *
     * @return
     */
    private void initField() {

        editTextnom.setText("");
        editTextprenom.setText("");
        editTexttelephone.setText("");
        editTextemail.setText("");
        editTextadresse.setText("");
    }
    /*
    * cette methode permet de sauver ou d'enregister les données sur les serveur distant
    * elle est basée sur asyncHttp
    *
    * @return
    * */
    class SaveContact_Htt extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() { //methode permet de faire patienter l'utilsiateur pendant le processus d'enregustrement du nouveau contact
            super.onPreExecute();

            pDialog = new ProgressDialog(NewContactActivity.this);
            pDialog.setMessage("Enregistrement des données en cours. Veuillez patienter ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... result) {//cette methode permet la réceuperation et l'enresgitement sur le serveur distant

            //ligne a activer pour la gestion de la connexion et session user
           /* session= new SessionManager(getApplicationContext());
            //on check ou vérifie en français si l'utilisateur est connecté, sinon on le rédirige à la page de connexion
            session.checkLogin();
            //on récupère les donnée de l'utilisateur
            HashMap<String, String > user= session.getUserDetails();
            */

            RequestParams rp = new RequestParams();

            rp.add("nom", nomContact);
            rp.add("prenom", prenomContact);
            rp.add("sexe", sexeContact);
            rp.add("telephone", telContact);
            rp.add("email", emailContact);
            rp.add("adresse", adresseContact);

            Log.i("ID : ",idContact); //afficher le ID du contact voir la valeur

            //on teste si id est nul alors methode post sinon methode put
            if (idContact == null || idContact.isEmpty()) {

                //ajouter l'entête de l'envoi
                List<Header> headers = new ArrayList<Header>();
                headers.add(new BasicHeader("Accept", "application/json"));
                headers.add(new BasicHeader("Content-Type", "application/json"));

                HttpUtils.post(url_add, headers.toArray(new Header[headers.size()]), rp, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray
                        Log.d("asd", "---------------- this is response : " + response);
                        try {
                            JSONObject serverResp = new JSONObject(response.toString());

                            if (statusCode == 201) {
                                res = "true";
                                // successfully created
                                //here, we'll send sms to customer
                            }else if(statusCode != 201) {
                                res="false";
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            res = "false";
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        res = "false";
                    }
                });
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();

            if(result.equals("false")){

                Toast.makeText(getApplicationContext(), "Erreur survenue pendant l'enregistrement du contact !", Toast.LENGTH_LONG).show();
            }
            else if(result.equals("true")){

                Toast.makeText(getApplicationContext(), "Enregistrement effectué avec succès.", Toast.LENGTH_LONG).show();
                finish();
            }

        }

    }

    /* methode pour convertir les données postées ou envoyée au server distant en inputStream
    *
    * @return
    * */

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    /* methode pour envoyer les données saisies au server distant en inputStream
   *
   * @return
   * */
    public static String POST(String url, Contact contact){
        InputStream inputStream = null;
        String result = "";
        HttpResponse httpResponse;
        int responseCode = 200;
        try {

            // 1. Créer le client HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = null;
            HttpPut HttpPut = null;

            // 2. Faire une demande POST à ​​l'URL donnée

            if (contact.getId() == null || contact.getId().isEmpty())
                httpPost = new HttpPost(url);
            else
                HttpPut = new HttpPut(url);

            String json = "";

            // 3. Construire jsonObject
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("id", contact.getId());

            jsonObject.accumulate("nom", contact.getNom());
            jsonObject.accumulate("prenom", contact.getPrenom());
            jsonObject.accumulate("telephone", contact.getTelephone());
            jsonObject.accumulate("email", contact.getEmail());
            jsonObject.accumulate("adresse", contact.getAdresse());
            jsonObject.accumulate("sexe", contact.getSexe());

            // 4. Convertit JSONObject en JSON en String
            json = jsonObject.toString();

            // ** Autre moyen de convertir l'objet Contact en chaîne JSON usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper ();
            // json = mapper.writeValueAsString(contact);

            // 5. Ajoutez json à StringEntity
            StringEntity se = new StringEntity(json);

            // 6. Définir l'entité httpPost ou httpPut
            if (contact.getId() == null || contact.getId().isEmpty()){
                httpPost.setEntity(se);

                // 7. Définissez certains en-têtes pour informer le serveur du type de contenu
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // 8.Exécuter la demande POST à ​​l'URL donnée
                 httpResponse = httpclient.execute(httpPost);
        }
            else {
                HttpPut.setEntity(se);

                // 7. Définissez certains en-têtes pour informer le serveur du type de contenu
                HttpPut.setHeader("Accept", "application/json");
                HttpPut.setHeader("Content-type", "application/json");

                // 8.Exécuter la demande POST à ​​l'URL donnée
                 httpResponse = httpclient.execute(HttpPut);
            }

            // 9. Recevoir une réponse comme inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 9.1 Recevoir une réponse comme int
             responseCode = httpResponse.getStatusLine().getStatusCode();

            // 10. Convertir inputstream en chaîne
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
            }
            else {
                result = "L'envoi des données n'a pas fonctionné!";
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        if(responseCode == 200 || responseCode == 201)
            resultat = "true";
        else
            resultat ="false";
        // 11. on retourne le résultat de retour
        return result;
    }

    /*
   * cette methode permet de sauver ou d'enregister les données sur les serveur distant
   * elle est basée sur httpPost
   *
   * @return
   * */
    private class SaveContact extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() { //methode permet de faire patienter l'utilsiateur pendant le processus d'enregustrement du nouveau contact
            super.onPreExecute();

            pDialog = new ProgressDialog(NewContactActivity.this);
            pDialog.setMessage("Données en cours d'envoi. Veuillez patienter ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            contact = new Contact();

            contact.setId(idContact);

            contact.setNom(nomContact);
            contact.setPrenom(prenomContact);
            contact.setTelephone(telContact);
            contact.setEmail(emailContact);
            contact.setAdresse(adresseContact);
            contact.setSexe(sexeContact);

            return POST(urls[0],contact);
        }
        // onPostExecute affiche les résultats de the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();//fermeture du patienteur

            if(resultat.equals("true"))
                Toast.makeText(getBaseContext(), "Données envoyées avec succès!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getBaseContext(), "l'envoi des données a échoué!", Toast.LENGTH_LONG).show();

        }
    }

}
