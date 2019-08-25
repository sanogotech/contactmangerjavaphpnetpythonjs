package com.mobile.rodolpheagnero.contactmanager.helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobile.rodolpheagnero.contactmanager.models.Contact;

import static android.util.Log.*;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private final static int version=1;
	Context context;
	
	//db_name
	private final static String DATABASE_NAME="contactmanager_db";
	
	//les tables
	private final static String TABLE_CONTACT_LOCAL ="contacts";
	private final static String TABLE_USER_LOCAL ="users";
	private final static String TABLE_SETTING_LOCAL ="settings";

	///////////////////////////// CRATION DES PROPRIETES DES TABLES DE LA BD ///////////////////////////////
	
	//les proprietes de la table CONTACT
	private final static String CONTACT_ID="contact_id", CONTACT_NOM="contact_nom", CONTACT_PRENOM="contact_prenom";
	private final static String CONTACT_ADRESSE="contact_adresse", CONTACT_TELEPHONE="contact_telephone", CONTACT_EMAIL="contact_email";
	private final static String CONTACT_SEXE="contact_sexe", CONTACT_DATE_CREATION="contact_date_creation", CONTACT_DATE_MODIFICATION="contact_date_modification";
	
	//les proprietes de la table USER
	private final static String USER_ID="user_id";
	private final static String USER_FIRSTNAME="user_firstname", USER_LASTNAME="user_lastname";

	//les proprietes de la table SETTINGS
	private final static String SETTINGS_ID="settings_id";

	
	///////////////////////////// CRATION DES TABLES DE LA BD ///////////////////////////////
	
	//TABLE CONTACT
	private final static String CREATE_TABLE_CONTACT_LOCAL="CREATE TABLE "+TABLE_CONTACT_LOCAL+" ("+CONTACT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CONTACT_NOM+" TEXT NOT NULL, "+CONTACT_PRENOM
			+" TEXT NOT NULL, "+CONTACT_SEXE+" TEXT NOT NULL, "+CONTACT_TELEPHONE+" TEXT NOT NULL, "+CONTACT_EMAIL+" TEXT NOT NULL, "+CONTACT_ADRESSE
			+" TEXT NOT NULL, "+CONTACT_DATE_CREATION+" DATETIME, "+CONTACT_DATE_MODIFICATION+" DATETIME);";


	//TABLE USER
	private final static String CREATE_TABLE_USER_LOCAL="CREATE TABLE "+TABLE_USER_LOCAL+" ("+USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_FIRSTNAME
			+" TEXT NOT NULL, "+USER_LASTNAME+" TEXT NOT NULL);";


	//TABLE SETTINGS
		private final static String CREATE_TABLE_SETTINGS_LOCAL="CREATE TABLE "+TABLE_SETTING_LOCAL+" ("+SETTINGS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT);";
		
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CREATE_TABLE_CONTACT_LOCAL);
		db.execSQL(CREATE_TABLE_USER_LOCAL);
		db.execSQL(CREATE_TABLE_SETTINGS_LOCAL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACT_LOCAL);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER_LOCAL);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_SETTING_LOCAL);

		onCreate(db);
	}

/*
* METHODES DE MANIPULATION DE LA TABLE CONTACT
* CREATE READ UPDATE DELETE
 */

//METHOD TO INSERT DATA IN TABLE CONTACT
	public void addContact(String nom,String prenom,String sexe,
							String telephone, String email, String adresse,
						   String created, String updated)
	{
		SQLiteDatabase db= this.getWritableDatabase();
		ContentValues values= new ContentValues();

		// Assign values for each row.
		values.put(CONTACT_NOM, nom);
		values.put(CONTACT_PRENOM,prenom);
		values.put(CONTACT_SEXE,sexe);
		values.put(CONTACT_TELEPHONE,telephone);
		values.put(CONTACT_EMAIL,email);
		values.put(CONTACT_ADRESSE,adresse);
		values.put(CONTACT_DATE_CREATION,created);
		values.put(CONTACT_DATE_MODIFICATION,updated);

		// Insert the row into your table
		db.insert(TABLE_CONTACT_LOCAL, null, values);
		//create log after insert
		Log.i("success","Enregistrement effectué avec succès");
		db.close();
	}
	//READ CONTENT OF LINE BY ID CONTACT
	public Contact getContact(String id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Contact	 contact = new Contact("", "", "", "", "", "","");

		Cursor cursor=db.query(TABLE_CONTACT_LOCAL, null, CONTACT_ID+" = ?",
				new String[]{id}, null, null, null);

		if(cursor.getCount()<1) // contact not exist in database
		{
			cursor.close();
			return null;
		} //else contact exist in database
		while(cursor.moveToNext()){

			id= cursor.getString(cursor.getColumnIndex(CONTACT_ID));
			String nom= cursor.getString(cursor.getColumnIndex(CONTACT_NOM));
			String prenom= cursor.getString(cursor.getColumnIndex(CONTACT_PRENOM));
			String sexe= cursor.getString(cursor.getColumnIndex(CONTACT_SEXE));
			String telephone= cursor.getString(cursor.getColumnIndex(CONTACT_TELEPHONE));
			String email= cursor.getString(cursor.getColumnIndex(CONTACT_EMAIL));
			String adresse= cursor.getString(cursor.getColumnIndex(CONTACT_ADRESSE));

			contact = new Contact(id, nom, prenom, sexe,telephone,email,adresse);

		}

		cursor.close();
		//db.close();
		return contact;
	}
//METHOD READ ALL CONTENT TABLE CONTACT
	public ArrayList<Contact> getAllContacts()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Contact	 contact = new Contact("", "", "", "", "", "","");

		Cursor cursor=db.query(TABLE_CONTACT_LOCAL, null, "", null, null, null, null);

		ArrayList<Contact> contacts = new ArrayList<Contact>();

		if(cursor.getCount()<1) // contact not exist in database
		{
			cursor.close();
			return null;
		} //else contact exist in database
		while(cursor.moveToNext()){

			String id= cursor.getString(cursor.getColumnIndex(CONTACT_ID));
			String nom= cursor.getString(cursor.getColumnIndex(CONTACT_NOM));
			String prenom= cursor.getString(cursor.getColumnIndex(CONTACT_PRENOM));
			String sexe= cursor.getString(cursor.getColumnIndex(CONTACT_SEXE));
			String telephone= cursor.getString(cursor.getColumnIndex(CONTACT_TELEPHONE));
			String email= cursor.getString(cursor.getColumnIndex(CONTACT_EMAIL));
			String adresse= cursor.getString(cursor.getColumnIndex(CONTACT_ADRESSE));

			contact = new Contact(id, nom, prenom, sexe,telephone,email,adresse);

			contacts.add(contact);

		}

		cursor.close();
		//db.close();
		return contacts;
	}
//METHOD UPDATE ROW CONTENT
	public void  updateContact(String id,String nom,String prenom,String sexe,
							 String telephone, String email, String adresse, String updated)
	{
		SQLiteDatabase db= this.getWritableDatabase();
		ContentValues values= new ContentValues();

		// Define the updated row content.
		values.put(CONTACT_NOM, nom);
		values.put(CONTACT_PRENOM,prenom);
		values.put(CONTACT_SEXE,sexe);
		values.put(CONTACT_TELEPHONE,telephone);
		values.put(CONTACT_EMAIL,email);
		values.put(CONTACT_ADRESSE,adresse);
		values.put(CONTACT_DATE_MODIFICATION,updated);

		String where=CONTACT_ID+" = ?";
		db.update(TABLE_CONTACT_LOCAL,values, where, new String[]{id});

		//create log after insert
		Log.i("success","Modification effectuée avec succés");
	}


	public int deleteContact(String id)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		String where=CONTACT_ID+"=?";
		int numberOFEntriesDeleted= db.delete(TABLE_CONTACT_LOCAL, where, new String[]{id}) ;
		//create log after insert
		Log.i("success","Suppression effectuée avec succès");
		return numberOFEntriesDeleted;
	}

	public ArrayList<Contact> getDataByKeyEnter(String key, String value)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Contact	 contact = new Contact("", "", "", "", "", "","");
		Cursor cursor=db.query(TABLE_CONTACT_LOCAL, null, key+" LIKE %?%", new String[]{value}, null, null, null);
		if(cursor.getCount() < 1) //if not exist in table
		{
			cursor.close();
			return null;
		}//if exist
		ArrayList<Contact> contacts = new ArrayList<Contact>();

		if(cursor.getCount()<1) // contact not exist in database
		{
			cursor.close();
			return null;
		} //else contact exist in database
		while(cursor.moveToNext()){

			String id= cursor.getString(cursor.getColumnIndex(CONTACT_ID));
			String nom= cursor.getString(cursor.getColumnIndex(CONTACT_NOM));
			String prenom= cursor.getString(cursor.getColumnIndex(CONTACT_PRENOM));
			String sexe= cursor.getString(cursor.getColumnIndex(CONTACT_SEXE));
			String telephone= cursor.getString(cursor.getColumnIndex(CONTACT_TELEPHONE));
			String email= cursor.getString(cursor.getColumnIndex(CONTACT_EMAIL));
			String adresse= cursor.getString(cursor.getColumnIndex(CONTACT_ADRESSE));

			contact = new Contact(id, nom, prenom, sexe,telephone,email,adresse);

			contacts.add(contact);

		}

		cursor.close();
		//db.close();
		return contacts;
	}




}
