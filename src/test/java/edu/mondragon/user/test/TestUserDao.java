package edu.mondragon.user.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.mondragon.user.dao.UserDaoImp;
import edu.mondragon.user.model.User;


/**
 * 
 * @author Xabier
 *
 */
public class TestUserDao {
User user;
	
	@Mock
    private SessionFactory sessionMock;
	
	@Mock
	private Session session;
	
	@Mock
	private TypedQuery<User> query;
	
	@Mock
	private Query<User> queryUser;
	
	@Mock
	private Query<Object> query2;

    @InjectMocks
    private UserDaoImp userDao;

    @Before
    public void setUp() {
    	String password = "123";
		String username = "Username1";
		String firstName = "User";
		String lastName = "Surname";
		String email = "examplemail@gmail.com";
		
		user = new User(password, username, firstName, lastName, email);
		user.setId(101);
    	
        MockitoAnnotations.initMocks(this);
    }
    
    
    
    @Test
    public void testDeleteUser() {
    	Mockito.when(sessionMock.getCurrentSession()).thenReturn(session);
    	Mockito.when(session.load(User.class,101)).thenReturn(user);
    	userDao.deleteUser(101);
    	
    	Mockito.when(session.load(User.class,102)).thenReturn(null);
    	userDao.deleteUser(102);
    	
    	Mockito.verify(sessionMock, Mockito.atLeast(2)).getCurrentSession();
    }
    
    
    @Test
    public void testAuthenticateUser() {
    	
    	given(sessionMock.getCurrentSession()).willReturn(session);
    	given(query.setParameter(Mockito.anyString(), Mockito.anyString())).willReturn(query);
    	Mockito.when(sessionMock.getCurrentSession().createQuery(Mockito.any(),Mockito.any())).thenReturn(query2);
    	
    	given(session.get(User.class, user.getId())).willReturn(user);
    	
    	List<Object> users = new ArrayList<Object>();
    	users.add(user);
    	when(query2.getResultList()).thenReturn(users);
    	
    	
    	assertEquals(userDao.authenticateUser(user.getUserName(), user.getPassword()), user);
    }
    
    @Test
    public void testAuthenticateBadUser() {
    	
    	given(sessionMock.getCurrentSession()).willReturn(session);
    	given(query.setParameter(Mockito.anyString(), Mockito.anyString())).willReturn(query);
    	Mockito.when(sessionMock.getCurrentSession().createQuery(Mockito.any(),Mockito.any())).thenReturn(query2);
    	
    	given(session.get(User.class, user.getId())).willReturn(null);
    	
    	List<Object> users = new ArrayList<Object>();
    	
    	when(query2.getResultList()).thenReturn(users);
    	
    	
    	assertEquals(userDao.authenticateUser("nonExistingUser", "nonExistingPass"), null);
    }
    
    @Test
    public void testAuthenticateEmail() {
    	
    	given(sessionMock.getCurrentSession()).willReturn(session);
    	given(query.setParameter(Mockito.anyString(), Mockito.anyString())).willReturn(query);
    	Mockito.when(sessionMock.getCurrentSession().createQuery(Mockito.any(),Mockito.any())).thenReturn(query2);
    	
    	given(session.get(User.class, user.getId())).willReturn(user);
    	
    	List<Object> users = new ArrayList<Object>();
    	users.add(user);
    	when(query2.getResultList()).thenReturn(users);
    	
    	
    	assertEquals(userDao.authenticateUserEmail(user.getUserName(), user.getEmail()), user);
    }
    
    @Test
    public void testAuthenticateBadEmail() {
    	
    	given(sessionMock.getCurrentSession()).willReturn(session);
    	given(query.setParameter(Mockito.anyString(), Mockito.anyString())).willReturn(query);
    	Mockito.when(sessionMock.getCurrentSession().createQuery(Mockito.any(),Mockito.any())).thenReturn(query2);
    	
    	given(session.get(User.class, user.getId())).willReturn(null);
    	
    	List<Object> users = new ArrayList<Object>();
    	
    	when(query2.getResultList()).thenReturn(users);
    	
    	
    	assertEquals(userDao.authenticateUserEmail("nonExistingUser", "nonExistingPass"), null);
    }
    
    /*
    @Test
    public void testListUsers() {
    	List<User> userList = new ArrayList<>();
    	userList.add(user);
    	given(sessionMock.getCurrentSession()).willReturn(session);
    	Mockito.when(session.createQuery(Mockito.anyString(),User.class)).thenReturn(queryUser);
    	
    	
    	Mockito.when(query.getResultList()).thenReturn(userList);
    	
    	assertEquals(userDao.listUsers(),userList);
    }
    
    @Test
    public void testUpdateUser() {
    	
    	assertEquals(userDao.updateUser(user),user);
    	
    }
    */
}
