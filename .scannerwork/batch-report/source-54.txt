package edu.mondragon.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @version v0.01
 * @brief  	User POJO class entity
 * @date	Creation: 12/04/19
 * @author 	Loredi Altzibar
 *
 */

@Entity
@Table(name = "USER")
public class User implements Serializable {

	/**
	 * @brief Default generated serial
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "firstName")
	private String firstName;

    @Column(name = "lastName")
	private String lastName;
    
    @Column(name = "email")
	private String email;
    
    @Column(name = "admin")
   	private boolean admin;
    
	
	public User() {}
	
	public User(String password, String username, String firstName, String lastName, String email) {
		this.password = password;
		this.userName = username;
		this.firstName = firstName;
	    this.lastName = lastName;
	    this.email = email;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	
	
}
