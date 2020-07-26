package edu.mondragon.user.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.mondragon.object.Password;
import edu.mondragon.user.model.User;

/**
 * 
 * @version v0.01
 * @brief  	DAO User
 * @date	Creation: 12/04/19
 * @author 	Loredi Altzibar
 *
 */

@Repository
public class UserDaoImp implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * @brief Change user password to hashed using bcrypt implementation
	 * @param user
	 */
	@Override
	public void addUser(User user) {
		String password = user.getPassword();
		String computedHash = Password.hashPassword(password);
		user.setPassword(computedHash);
		sessionFactory.getCurrentSession().save(user);
	}
	
	@Override
    public User updateUser(User user) {
		User us = getUser(user.getId());
		
		if(!us.getPassword().equals(user.getPassword())) {
			String password = user.getPassword();
			String computedHash = Password.hashPassword(password);
			us.setPassword(computedHash);
		}
		us.setEmail(user.getEmail());
		us.setFirstName(user.getFirstName());
		us.setLastName(user.getLastName());
		us.setUserName(user.getUserName());
		// Use evict in order to update
		/*sessionFactory.getCurrentSession().evict(us);
		us.setPassword(user.getPassword());*/
		sessionFactory.getCurrentSession().update(us);
        return us;
    }
	
	@Override
    public void deleteUser(Integer id) {
        User user = sessionFactory.getCurrentSession().load(
                User.class, id);
        if (null != user) {
            this.sessionFactory.getCurrentSession().delete(user);
        }
 
    }
	
	
	/**
	 * @brief Check if user exists in database using bcrypt implementation
	 */
	public User authenticateUser(String username, String password) {
		User result = null;
		TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User where userName= :username", User.class);
		query.setParameter("username", username); //can also do this with the entire user object
		
		List<User> users = query.getResultList();
		
		for (User user : users) {
			if (Password.checkPassword(password, user.getPassword())) {
				result = user;
			}
		}
		return result;
	}
	
	
	public User getUser(int id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

	@Override
	public List<User> listUsers() {
		TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User", User.class);
		
		return query.getResultList();
	}

	@Override
	public User authenticateUserEmail(String userName, String email) {
		TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User where userName= :username"
				+ " and email= :email", User.class);
		query.setParameter("username", userName); //can also do this with the entire user object
		query.setParameter("email", email);
		List<User> users = query.getResultList(); 
		
		return !users.isEmpty() ? getUser(users.get(0).getId()) : null;
	}
	
}
