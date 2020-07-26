package edu.mondragon.user.test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.mondragon.user.dao.UserDaoImp;
import edu.mondragon.user.model.User;
import edu.mondragon.user.service.UserServiceImp;

/**
 * 
 * @author Xabier
 *
 */
public class TestUserService {
	
	final static String USERNAME = "Username1";
	User user;
	
	@Mock
    private UserDaoImp daoMock;

    @InjectMocks
    private UserServiceImp service;

    @Before
    public void setUp() throws Exception {
    	String password = "123";
		String username = USERNAME;
		String firstName = "User";
		String lastName = "Surname";
		String email = "examplemail@gmail.com";
		
		user = new User(password, username, firstName, lastName, email);
		user.setId(101);
    	
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testListUsers() {
    	List<User> list = new ArrayList<User> ();
    	Mockito.when(daoMock.listUsers()).thenReturn(list);
    	
    	
    	assertEquals(service.listUsers(),Matchers.anyListOf(User.class));
    }

    @Test
    public void testUpdateUser() {

    	Mockito.when(daoMock.updateUser(user)).thenReturn(user);
    	
        assertEquals(service.updateUser(user), user);
        
    }
    
    
   
    
    @Test
    public void testAuthenticateInvalidUser() {
    	
    	Mockito.when(daoMock.authenticateUser("NonExistingUser", "NonExistingPassword")).thenReturn(null);
    	
    	assertNull(service.authenticateUser("NonExistingUser", "NonExistingPassword"));
    }
    
    @Test
    public void testGetExistingUser() {
    	
    	Mockito.when(daoMock.getUser(101)).thenReturn(user);
    	
    	assertEquals(service.getUser(101),user);
    }
    
    
    @Test
    public void testDeleteUser(){
    	
    	service.deleteUser(101);
    	Mockito.verify(daoMock, Mockito.times(1)).deleteUser(101);
    	
    	
    }
    
    @Test
    public void testAddUser(){
    	
    	service.addUser(user);
    	Mockito.verify(daoMock, Mockito.times(1)).addUser(user);
    }
    @Test
    public void testAuthenticateValidUser() {
    	Mockito.when(daoMock.authenticateUser(USERNAME, "123")).thenReturn(user);
    	
    	
    	assertEquals(service.authenticateUser(USERNAME,"123"), user);
    }
}