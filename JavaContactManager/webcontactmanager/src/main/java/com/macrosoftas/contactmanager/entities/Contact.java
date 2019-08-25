package com.macrosoftas.contactmanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.macrosoftas.contactmanager.validation.Telephone;


/**
 * Contact entity.
 */
@Entity
@Table(name = "contacts")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "contacts_id")
	private Integer id;

	@Column(name = "contacts_nom")
	@NotNull(message = "Le nom ne peut pas être vide")
	@Size(min = 3, max = 150, message = "Le nom du contact peut comporter au minimum 3 et maximum 30 caractères")
	private String nom;

	@Column(name = "contacts_prenom")
	@NotNull(message = "Le prénom ne peut pas être vide")
	private String prenom;

	@Column(name = "contacts_sexe")
	@NotNull
	private String sexe;	
	
	@Column(name = "contacts_telephone")
	@NotNull(message = "Le prénom ne peut pas être vide")
	@Size(min=8) @Telephone
	private String telephone;

	@Column(name = "contacts_email")
	@NotNull(message = "L'adresse email ne peut pas être vide")
	@Email
	private String email;

	@Column(name = "contacts_adresse")
	@NotNull(message = "L'adresse géographique ne peut pas être vide")
	private String adresse;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
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

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contact(Integer id, String nom, String prenom, String telephone, String email, String adresse, String sexe) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.adresse = adresse;
		this.sexe = sexe;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ",sexe=" + sexe + ", telephone=" + telephone + ", email="
				+ email + ", adresse=" + adresse + "]";
	}

}
