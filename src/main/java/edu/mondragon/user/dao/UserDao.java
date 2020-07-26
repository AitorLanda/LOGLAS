package edu.mondragon.user.dao;

import java.util.List;

import edu.mondragon.user.model.User;

/**
 * 
 * @version v0.01
 * @brief  	DAO User interface
 * @see 	UserDaoImp
 * @date	Creation: 12/04/19
 * @author 	Loredi Altzibar
 *
 */

public interface UserDao {
	public void addUser(User user);
	public User updateUser(User user);
	public void deleteUser(Integer id);
	
	
	public User authenticateUser(String username, String password);
	
	public User getUser(int id);
	public List<User> listUsers();
	public User authenticateUserEmail(String userName, String email);

}
