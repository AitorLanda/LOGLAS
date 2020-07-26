package edu.mondragon.user.service;

import edu.mondragon.user.model.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mondragon.user.dao.UserDao;

/**
 * 
 * @version v0.01
 * @brief  	Service User
 * @date	Creation: 12/04/19
 * @author 	Loredi Altzibar
 *
 */

@Service
@Transactional
public class UserServiceImp implements UserService {

	@Autowired
	private UserDao userDao;

	
	/**
	 * @brief	Inserts a user into the database
	 * @param 	User: User object
	 */
	@Transactional
	@Override
	public void addUser(User user) {
		userDao.addUser(user);
	}

	/**
	 * @brief	Updates a user from the database
	 * @param 	User
	 * @return	User
	 */
	public User updateUser(User user) {
        return userDao.updateUser(user);
    }
	
	/**
	 * @brief	Deletes a user into the database from ID
	 * @param 	User
	 */
	@Override
    @Transactional
    public void deleteUser(Integer id) {
        userDao.deleteUser(id);
    }
	
	
	
	
	
	/**
	 * @brief	CHECKS if a user exists from the database from USERNAME AND PASSWORD
	 * @return 	boolean
	 */
	@Transactional(readOnly = true)
	@Override
	public User authenticateUser(String username, String password) {
		return userDao.authenticateUser(username, password);
	}
	
	
	/**
	 * @brief	Gets a user from the database as a User from ID
	 * @return 	User
	 */
	@Transactional(readOnly = true)
	@Override
	public User getUser(int id) {
		return userDao.getUser(id);
	}
	
	/**
	 * @brief	Gets all users from the database as a list
	 * @return 	List: all users as a list
	 */
	@Transactional(readOnly = true)
	@Override
	public List<User> listUsers() {
		return userDao.listUsers();
	}
	
	/**
	 * @brief	Gets all users from the database by a user ID list ------------- !!!! UNEFICIENT
	 * 			Does 1 by 1 query ---------------------------------------------- !!!! UNEFICIENT
	 * @return 	List: all users as a list
	 */
	@Transactional(readOnly = true)
	@Override
	public List<User> listUsersByIds(List<Integer> userIdList) {
		List<User> userList = new ArrayList<>();
		for (int i = 0; i < userIdList.size(); i++) {
			userList.add(getUser(userIdList.get(i)));
		}
		return userDao.listUsers();
	}

	@Override
	public User authenticateUserEmail(String userName, String email) {
		return userDao.authenticateUserEmail(userName, email);
	}
	
}
