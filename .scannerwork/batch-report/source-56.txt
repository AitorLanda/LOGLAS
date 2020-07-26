package edu.mondragon.user.service;

import java.util.List;

import edu.mondragon.user.model.User;

/**
 * 
 * @version v0.01
 * @brief  	Service User interface
 * @see		UserServiceImp
 * @date	Creation: 12/04/19
 * @author 	Loredi Altzibar
 *
 */

public interface UserService {
	void addUser(User user);
	public User updateUser(User user);
	public void deleteUser(Integer id);
	public User authenticateUser(String username, String password);
	public User getUser(int id);
	public List<User> listUsers();
	public List<User> listUsersByIds(List<Integer> userIdList);
	User authenticateUserEmail(String userName, String email);
}
