package edu.mondragon.user.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.mondragon.user.model.User;

/**
 * 
 * @author Xabier
 *
 */
public class TestUser {
	User user;
	User user2;
	User user3;
	
	@Before
	public void prepareUser() {
		String password = "123";
		String username = "Username1";
		String firstName = "User";
		String lastName = "Surname";
		String email = "examplemail@gmail.com";
		
		user = new User(password, username, firstName, lastName, email);
		user2 = new User(null, null, null, null, null);
		user3 = new User();
		
		user2.setAdmin(false);
		user2.setId(2);
		user2.setUserName(username);
		user2.setFirstName(firstName);
		user2.setLastName(lastName);
		user2.setPassword(password);
		user2.setEmail(email);
		
		
	}
	
	@Test 
	public void testUserPassword(){
		assertEquals(user2.getPassword(), user.getPassword());
		assertEquals(user3.getPassword(),null);
	}
	@Test 
	public void testUsername(){
		assertEquals(user2.getUserName(), user.getUserName());
	}
	@Test 
	public void testUsersName(){
		assertEquals(user2.getFirstName(), user.getFirstName());
	}
	@Test 
	public void testUserSurname(){
		assertEquals(user2.getLastName(), user.getLastName());
	}
	@Test 
	public void testUserMail(){
		assertEquals(user2.getEmail(), user.getEmail());
	}
	@Test
	public void testUserId() {
		assertEquals(2,user2.getId());
	}
	@Test
	public void testAdmin() {
		assertEquals(false,user2.isAdmin());
	}
}
