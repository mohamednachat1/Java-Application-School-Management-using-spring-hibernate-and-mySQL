package app.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import app.dao.UserDao;
import app.entities.User;

@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao{
	//@Autowired
	//private SessionFactory sessionFactory;
	
	@Autowired
	public void setupSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void save(User u) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(u);
	}

	@Override
	public boolean delete(User u) {
		try {
			getHibernateTemplate().delete(u);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public User getByID(int id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		DetachedCriteria c = DetachedCriteria.forClass(User.class).addOrder(Order.asc("id"));
		List<User> users =  (List<User>)getHibernateTemplate().findByCriteria(c);
		return users;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getByExample(User u, MatchMode mode, boolean detached) {
		Example example = Example.create(u);
		example.enableLike(mode);
		example.excludeZeroes();
		DetachedCriteria c = DetachedCriteria.forClass(User.class).add(example);
		List<User> users = (List<User>)getHibernateTemplate().findByCriteria(c);
		if(detached) {
			for(User user: users) {
				getHibernateTemplate().evict(user);
			}
		}
		return users;
	}
}
