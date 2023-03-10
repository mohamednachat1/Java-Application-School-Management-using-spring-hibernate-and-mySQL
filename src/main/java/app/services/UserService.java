package app.services;

import java.util.List;

import app.entities.User;


public interface UserService {
	void save(User u);
	boolean delete (User u);
	User getByID(int id);
	List<User> getAll();
	List<User> getByExample(User u);
}
