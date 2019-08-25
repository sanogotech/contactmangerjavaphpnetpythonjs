/**
 * 
 */
package com.macrosoftas.contactmanager.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author   MacroSOFT AS
 *
 */
@Entity
@Table(name = "users")
public class User {

	
	public User() {
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "users_id")
	Integer  id;
	
	@Column(name = "users_username", nullable = false, length = 100, unique = true)
	String  username;
	
	@Column(name = "users_password", nullable = false, unique = true)
	String  password;
	
	@Column(name = "users_email")
	@Email
	@NotEmpty(message = "L'adresse email ne peut pas être vide")
	String email;
	
	@Column(name = "users_firstname")	
	@NotEmpty(message = "Le nom ne peut pas être vide")
	String firstname;
	
	@Column(name = "users_lastname")	
	@NotEmpty(message = "Le prénom ne peut pas être vide")
	String lastname;
	
	@Column(name = "users_genre")	
	@NotEmpty(message = "Le sexe ne peut pas être vide")
	String genre;
	
	@Column(name = "users_phone")		
	String phone;
	
	@Column(name = "users_mobile")		
	String mobile;
	
	@Column(name = "users_active")		
	String active;  
	  
	@Column(name = "users_address")		
	String address;  

	@Column(name = "users_avatar")		
	String avatar;  
	  
	@Column(name = "users_commentaires")		
	String commentaires;  
	  
	@Column(name = "users_isadmin")		
	String isadmin;  
	  
	@Column(name = "users_lastconnected")		
	Date lastconnected;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public Date getLastconnected() {
		return lastconnected;
	}

	public void setLastconnected(Date lastconnected) {
		this.lastconnected = lastconnected;
	}

	public User(Integer id, String username, String password, String email, String firstname, String lastname,
			String genre, String phone, String mobile, String active, String address, String avatar,
			String commentaires, String isadmin, Date lastconnected) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.genre = genre;
		this.phone = phone;
		this.mobile = mobile;
		this.active = active;
		this.address = address;
		this.avatar = avatar;
		this.commentaires = commentaires;
		this.isadmin = isadmin;
		this.lastconnected = lastconnected;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", genre=" + genre + ", phone=" + phone
				+ ", mobile=" + mobile + ", active=" + active + ", address=" + address + ", avatar=" + avatar
				+ ", commentaires=" + commentaires + ", isadmin=" + isadmin + ", lastconnected=" + lastconnected + "]";
	}  
	  
	
}
