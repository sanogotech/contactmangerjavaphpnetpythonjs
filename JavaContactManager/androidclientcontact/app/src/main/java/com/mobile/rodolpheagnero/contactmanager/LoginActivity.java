package com.mobile.rodolpheagnero.contactmanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.mobile.rodolpheagnero.contactmanager.helpers.AlertDialogManager;
import com.mobile.rodolpheagnero.contactmanager.helpers.AppConstants;
import com.mobile.rodolpheagnero.contactmanager.helpers.ConnectInternet;
import com.mobile.rodolpheagnero.contactmanager.helpers.HttpUtils;
import com.mobile.rodolpheagnero.contactmanager.helpers.UserSessionManager;
import com.mobile.rodolpheagnero.contactmanager.models.Contact;
import com.mobile.rodolpheagnero.contactmanager.models.LoginForm;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    //déclaration des varaiables globales
    Button login;
    EditText username,password;

    String user, pass;

    //déclaration variable de retour asynchhttp pour l'envoi d'une notif
    static String resultat = "false";
    String res = "false";
    // création de la variable contact de type Contact
    LoginForm loginForm;

    Boolean isConnectInternet=false; //valeur de retour par defaut de la vérification de la connexion à internet
    ConnectInternet conexion ; //appel de la class de connexion

    private ProgressDialog pDialog;

    AlertDialogManager alert= new AlertDialogManager();

    private static String url = "api/login";

    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        //initialisation variables globales déclarées
        login =(Button) findViewById(R.id.login);
        username =(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //récuperation des valeurs entrées par l'utilisateur
                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.length()==0){

                    username.requestFocus();
                    username.setError("Le nom d'utilisateur est réquis.");
                }
                if(pass.length()== 0){

                    password.requestFocus();
                    password.setError("Le mot de passe est réquis.");
                }

                if(username.length() > 0 && pass.length() > 0){

                     /*-----------------enregistrement des données saisies par l'utilisateur--------------------------*/

                    conexion = new ConnectInternet(LoginActivity.this); //instance class connectinternet
                    isConnectInternet = conexion.isOnline(); //get value is connect

                    if(isConnectInternet){ //if connect internet exist

                        new Login().execute(AppConstants.BASE_URL+url);//on exécute l'action


                    } else{

                        Snackbar.make(view, "Vous n'êtes pas connecté à internet. Veuillez réessayer plutard!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }




                }


            }
        });

    }

    /* methode pour convertir les données postées ou envoyée au server distant en inputStream
    *
    * @return
    * */

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
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
    public static String POST(String url, LoginForm loginForm){
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
            httpPost = new HttpPost(url);

            String json = "";

            // 3. Construire jsonObject
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("username", loginForm.getUsername());
            jsonObject.accumulate("password", loginForm.getPassword());

            // 4. Convertit JSONObject en JSON en String
            json = jsonObject.toString();

            // 5. Ajoutez json à StringEntity
            StringEntity se = new StringEntity(json);

            // 6. Définir l'entité httpPost

                httpPost.setEntity(se);

                // 7. Définissez certains en-têtes pour informer le serveur du type de contenu
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // 8.Exécuter la demande POST à ​​l'URL donnée
                httpResponse = httpclient.execute(httpPost);

            // 9. Recevoir une réponse comme inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 9.1 Recevoir une réponse comme int
            responseCode = httpResponse.getStatusLine().getStatusCode();

            // 10. Convertir inputstream en chaîne
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
            }
            else {
                result = "L'authentification a echoué. Veuillez réessayer!";
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
    private class Login extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() { //methode permet de faire patienter l'utilsiateur pendant le processus d'enregustrement du nouveau contact
            super.onPreExecute();

            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Authentification en cours. Veuillez patienter ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            loginForm = new LoginForm("","");

            loginForm.setUsername(user);
            loginForm.setPassword(pass);


            return POST(urls[0],loginForm);
        }
        // onPostExecute affiche les résultats de the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();//fermeture du patienteur

            if(resultat.equals("true")){
                //1 Au cas où l'authentification a réussie, on crée les vairables de session
                session.createUserLoginSession(user, pass,"","","");

                // on affiche un message de connexion en cas de réussite
                Toast.makeText(getBaseContext(), "L'authentification a réussie. Vous êtes à présent connecté à la plateforme de gsetion de contact", Toast.LENGTH_LONG).show();

                //si tout s'est bien déroulé, on rédirige à la liste des contacts et on termine l'activité de suppresionn et visualisation de contact
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            else
                Toast.makeText(getBaseContext(), "L'authentification a échoué. Veuillez réesayer!", Toast.LENGTH_LONG).show();

        }
    }

}
