package app.services.impl;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.dao.UserDao;
import app.entities.User;
import app.services.UserService;

@Component
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao  userdao;
	@Override
	@Transactional
	public void save(User u) {
		this.userdao.save(u);
	}

	@Override
	@Transactional
	public boolean delete(User u) {
		return this.userdao.delete(u);
	}

	@Override
	@Transactional
	public User getByID(int id) {
		return this.userdao.getByID(id);
	}

	@Override
	@Transactional
	public List<User> getAll() {
		return this.userdao.getAll();
	}

	@Override
	@Transactional
	public List<User> getByExample(User u) {
		return this.userdao.getByExample(u, MatchMode.EXACT, false);
	}

}
