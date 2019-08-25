package com.mobile.rodolpheagnero.contactmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.rodolpheagnero.contactmanager.NewContactActivity;
import com.mobile.rodolpheagnero.contactmanager.R;
import com.mobile.rodolpheagnero.contactmanager.ViewContactActivity;
import com.mobile.rodolpheagnero.contactmanager.models.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactAdapter extends ArrayAdapter<Contact> {

    // Créer une class contenant les champs de la vue
    private static class ViewHolder {
        TextView id;
        TextView nom;
        TextView prenom;
        TextView telephone;
        TextView email;
        TextView adresse;
        TextView sexe;
        TextView nomprenom;
        ImageView editDetailContact;
    }

    private List<Contact> contactlist = null;
    private ArrayList<Contact> arraylist;

    public ContactAdapter(Context context, List<Contact> Contacts) {//au depart ArrayList
        super(context, R.layout.item_contact, Contacts);

        /* ajouter le 09/08/2017 à 02:38 pour gérer la rech
        *
        * */
        this.contactlist = Contacts;
        this.arraylist = new ArrayList<Contact>();
        this.arraylist.addAll(this.contactlist);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact Contact = getItem(position);
       final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            // 1. Créer inflater
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_contact, parent, false);

            // 1. Récupérer les champs de la vue
            viewHolder.id = (TextView) convertView.findViewById(R.id.idcontact);
            viewHolder.nom = (TextView) convertView.findViewById(R.id.firstname);
            viewHolder.prenom = (TextView) convertView.findViewById(R.id.lastname);
            viewHolder.telephone = (TextView) convertView.findViewById(R.id.phone);
            viewHolder.email = (TextView) convertView.findViewById(R.id.email);
            viewHolder.adresse = (TextView) convertView.findViewById(R.id.address);
            viewHolder.sexe = (TextView) convertView.findViewById(R.id.genre);
            viewHolder.nomprenom = (TextView) convertView.findViewById(R.id.fullname);

            viewHolder.editDetailContact = (ImageView) convertView.findViewById(R.id.imageView2detailscontact);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 1. Définir les valeurs des champs de la vue
        viewHolder.id.setText(Contact.getId());
        viewHolder.nom.setText(Contact.getNom());
        viewHolder.prenom.setText(Contact.getPrenom());
        viewHolder.telephone.setText(Contact.getTelephone());
        viewHolder.email.setText(Contact.getEmail());
        viewHolder.adresse.setText(Contact.getAdresse());
        viewHolder.sexe.setText(Contact.getSexe());
        viewHolder.nomprenom.setText(Contact.getNom() +" "+Contact.getPrenom());


        /*
        * METHODE POUR VISUALISER LES DONNEES
        *
        * */
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //faire passer les valeurs de la listview au form
                Intent intent =  new Intent(getContext(), ViewContactActivity.class);

                intent.putExtra("id", viewHolder.id.getText().toString());//key value cad clé valeur
                intent.putExtra("nom", viewHolder.nom.getText().toString());
                intent.putExtra("prenom", viewHolder.prenom.getText().toString());
                intent.putExtra("telephone", viewHolder.telephone.getText().toString());
                intent.putExtra("email", viewHolder.email.getText().toString());
                intent.putExtra("adresse", viewHolder.adresse.getText().toString());

                getContext().startActivity(intent);

            }
        });

        /*
        * METHODE POUR AFFICHER LES INFORMATIONS POUR MODIFICATION
        * */
        viewHolder.editDetailContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //faire passer les valeurs de la listview au form
                Intent intent =  new Intent(getContext(), NewContactActivity.class);

                intent.putExtra("id", viewHolder.id.getText().toString());//key value cad clé valeur
                intent.putExtra("nom", viewHolder.nom.getText().toString());
                intent.putExtra("prenom", viewHolder.prenom.getText().toString());
                intent.putExtra("telephone", viewHolder.telephone.getText().toString());
                intent.putExtra("email", viewHolder.email.getText().toString());
                intent.putExtra("adresse", viewHolder.adresse.getText().toString());

                intent.putExtra("action","MODIFIER");
                intent.putExtra("title","MODIFIER CONTACT N°"+viewHolder.id.getText().toString());

                getContext().startActivity(intent);

            }
        });

        return convertView;
    }


    /* Filter Class
    * ajouter le 09/08/2017 à 02:38 pour gérer la rech
     *
     */
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arraylist = new ArrayList<>(contactlist) ;
        contactlist.clear(); //si liste size not empty, on vide
        if (charText.length() == 0) {
            contactlist.addAll(arraylist); //on charge la liste avec le contenu de Contact
        }
        else
        {
            for (Contact wp : arraylist)
            {
                if ((wp.getNom()+" "+wp.getPrenom()).toLowerCase(Locale.getDefault()).contains(charText))
                {
                    contactlist.add(wp); //on recharge la liste en fonction du nom et prenom saisi dan le champ de recherche
                }
            }
        }
        notifyDataSetChanged();
    }
}