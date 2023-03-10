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
import org.springframework.transaction.annotation.Transactional;

import app.dao.FiliereDao;
import app.entities.Filiere;

@Repository
public class FiliereDaoImpl extends HibernateDaoSupport implements FiliereDao {
	
	@Autowired
	public void setupSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void save(Filiere o) 
	{
//		sessionFactory.getCurrentSession().saveOrUpdate(o);
		getHibernateTemplate().saveOrUpdate(o);
	}
	

	@Override
	@Transactional
	public boolean delete(Filiere o) 
	{
		try {  
			System.out.println("SUCCESS");
			getHibernateTemplate().delete(o);
			return true;
			} catch (Exception ex) {
				System.out.println("Error");
				return false;
			}
	}

	@Override
	public Filiere getByID(int id)
	{
//		return sessionFactory.getCurrentSession().get(Filiere.class, id);
		return getHibernateTemplate().get(Filiere.class, id);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Filiere> getAll()
	{
		DetachedCriteria c = DetachedCriteria.forClass(Filiere.class).addOrder(Order.asc("id"));
		return (List<Filiere>)getHibernateTemplate().findByCriteria(c);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Filiere> getByExample(Filiere o, MatchMode mode, boolean detached) {
		Example example = Example.create(o);
		example.enableLike(mode);
		example.excludeZeroes();
		DetachedCriteria c = DetachedCriteria.forClass(Filiere.class).add(example);
		List<Filiere> filieres = (List<Filiere>)getHibernateTemplate().findByCriteria(c);
		if(detached) {
			for(Filiere filiere: filieres) {
				getHibernateTemplate().evict(filiere);
			}
		}
		return filieres;
	}
}
