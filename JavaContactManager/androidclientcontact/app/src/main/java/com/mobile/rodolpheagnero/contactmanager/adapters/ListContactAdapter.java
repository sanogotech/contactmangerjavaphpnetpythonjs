package com.mobile.rodolpheagnero.contactmanager.adapters;

import java.util.ArrayList;
import java.util.List;

import com.mobile.rodolpheagnero.contactmanager.models.Contact;
import com.mobile.rodolpheagnero.contactmanager.NewContactActivity;
import com.mobile.rodolpheagnero.contactmanager.ViewContactActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.rodolpheagnero.contactmanager.R;

public class ListContactAdapter extends BaseAdapter {
	LayoutInflater inflater;
	Context mcontext;
	List<Contact> contacts;
	ArrayList<Contact> arrListContact;
	

	final String id="id";
	final String nom="nom";
	final String prenom="prenom";
	final String sexe="sexe";
	final String telephone="telephone";
	final String email="email";
	final String adresse="adresse";
	final String nomprenoms="nomprenoms";
	
	public ListContactAdapter(Context context, List<Contact> contacts) {
		this.mcontext=context;
		this.inflater=LayoutInflater.from(context);
		arrListContact=new ArrayList<Contact>();
		this.contacts = contacts;

		arrListContact.addAll(contacts);
	}

	@Override
	public int getCount() {
		
		return contacts.size();
	}

	@Override
	public Object getItem(int position) {
		
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		
		view=inflater.inflate(R.layout.item_contact, null);
		final TextView idContact=(TextView) view.findViewById(R.id.idcontact);
		final TextView nomContact=(TextView) view.findViewById(R.id.firstname);
		final TextView prenomContact=(TextView) view.findViewById(R.id.lastname);
		final TextView nomPrenomContact=(TextView) view.findViewById(R.id.fullname);
		final TextView telContact=(TextView) view.findViewById(R.id.phone);
		final TextView emailContact=(TextView) view.findViewById(R.id.email);
		final TextView adresseContact=(TextView) view.findViewById(R.id.address);
		final TextView sexeContact=(TextView) view.findViewById(R.id.genre);
		final ImageView editDetailContact = (ImageView) view.findViewById(R.id.imageView2detailscontact);

		idContact.setText(contacts.get(position).getId());
		nomContact.setText(contacts.get(position).getNom());
		prenomContact.setText(contacts.get(position).getPrenom());
		nomPrenomContact.setText(contacts.get(position).getNom()+ " "+contacts.get(position).getPrenom());
		telContact.setText(contacts.get(position).getTelephone());
		emailContact.setText(contacts.get(position).getEmail());
		adresseContact.setText(contacts.get(position).getAdresse());
		sexeContact.setText(contacts.get(position).getSexe());
		
		if(position % 2 ==1)
		{
			view.setBackgroundResource(R.color.colorWite);
		}
		else
		{
			view.setBackgroundResource(R.color.colorPrimary);
		}
		
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(mcontext, ViewContactActivity.class);

				intent.putExtra(id, idContact.getText().toString());
				intent.putExtra(nom, nomContact.getText().toString());
				intent.putExtra(prenom, prenomContact.getText().toString());
				intent.putExtra(sexe, sexeContact.getText().toString());
				intent.putExtra(adresse, adresseContact.getText().toString());
				intent.putExtra(email, emailContact.getText().toString());
				intent.putExtra(telephone, telContact.getText().toString());
				intent.putExtra(nomprenoms, nomPrenomContact.getText().toString());
				
				mcontext.startActivity(intent);
				
			}
		});
		
		editDetailContact.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent =  new Intent(mcontext, NewContactActivity.class);
				intent.putExtra(id, idContact.getText().toString());
				intent.putExtra(nom, nomContact.getText().toString());
				intent.putExtra(prenom, prenomContact.getText().toString());
				intent.putExtra(sexe, sexeContact.getText().toString());
				intent.putExtra(adresse, adresseContact.getText().toString());
				intent.putExtra(email, emailContact.getText().toString());
				intent.putExtra(telephone, telContact.getText().toString());
				intent.putExtra(nomprenoms, nomPrenomContact.getText().toString());
				
				mcontext.startActivity(intent);
				
			}
		});
		
		return view;
	}

}
