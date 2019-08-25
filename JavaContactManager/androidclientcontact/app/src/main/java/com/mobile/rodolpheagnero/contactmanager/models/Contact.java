package com.mobile.rodolpheagnero.contactmanager.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact {
	private String id;
	private String nom;
	private String prenom;
	private String telephone;
	private String email;
	private String adresse;
	private String sexe;

	public Contact() {
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public Contact(String id, String nom, String prenom, String telephone, String email, String adresse, String sexe) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.adresse = adresse;
		this.sexe = sexe;
	}

	public String toString() {
		return "Contact [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ",sexe=" + sexe + ", telephone=" + telephone + ", email="
				+ email + ", adresse=" + adresse + "]";
	}

	public Contact(JSONObject object) {
		try {
			this.id = object.getString("id");
			this.nom = object.getString("nom");
			this.prenom = object.getString("prenom");
			this.telephone = object.getString("telephone");
			this.email = object.getString("email");
			this.adresse = object.getString("adresse");
			this.sexe = object.getString("sexe");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

