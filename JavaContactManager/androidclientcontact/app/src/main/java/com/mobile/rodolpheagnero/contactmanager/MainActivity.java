package com.mobile.rodolpheagnero.contactmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    //DECLARATION VRIABLES LOCALES
    Button btnNewContact, btnListContact, btnSynchronisation, btnAide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //afficher la barre d'outils
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ajouter l'icone de l'app icon dans la barre d'outils
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_action_person_mip);

        //INITITALISATION DES VARIABLES LOCALES
        btnNewContact=(Button) findViewById(R.id.new_contact);
        btnListContact=(Button) findViewById(R.id.list_contact);
        btnSynchronisation=(Button) findViewById(R.id.synchroniz);
        btnAide=(Button) findViewById(R.id.help);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnNewContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(MainActivity.this,NewContactActivity.class);
                startActivity(intent);

            }
        });

        btnListContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(MainActivity.this,ListContactActivity.class);
                startActivity(intent);

            }
        });

        btnSynchronisation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);

            }
        });

        btnAide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //pour sortir de l'application avec une confirmation
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle(R.string.app_name)
                .setMessage("Êtes - vous sur de vouloir quitter l\'application ?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                      /*  session=new SessionManager(getApplicationContext());
                        session.logoutUser();*/

                        finish();
                    }
                })

                .setNegativeButton("Annuler", null)
                .show();
    }
}
