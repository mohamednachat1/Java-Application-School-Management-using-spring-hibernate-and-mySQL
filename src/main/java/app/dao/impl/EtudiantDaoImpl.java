package app.dao.impl;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import app.dao.EtudiantDao;
import app.entities.Etudiant;
@Repository
public class EtudiantDaoImpl extends HibernateDaoSupport implements EtudiantDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setupSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void save(Etudiant o) 
	{
//		sessionFactory.getCurrentSession().saveOrUpdate(o);
		getHibernateTemplate().saveOrUpdate(o);
	}

	@Override
	public boolean delete(Etudiant o) 
	{
		try {
			sessionFactory.getCurrentSession().delete(o);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Etudiant getByID(int id)
	{
//		return sessionFactory.getCurrentSession().get(Etudiant.class, id);
		return getHibernateTemplate().get(Etudiant.class, id);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Etudiant> getAll()
	{
//		return sessionFactory.getCurrentSession().createQuery("from Etudiant").list();
		
		DetachedCriteria c = DetachedCriteria.forClass(Etudiant.class).addOrder(Order.asc("id"));
		return (List<Etudiant>)getHibernateTemplate().findByCriteria(c);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Etudiant> getByExample(Etudiant o, MatchMode mode, boolean detached) {
		Example example = Example.create(o);
		example.enableLike(mode);
		example.excludeZeroes();
		DetachedCriteria c = DetachedCriteria.forClass(Etudiant.class).add(example);
		List<Etudiant> etudiants = (List<Etudiant>)getHibernateTemplate().findByCriteria(c);
		if(detached) {
			for(Etudiant etudiant: etudiants) {
				getHibernateTemplate().evict(etudiant);
			}
		}
		return etudiants;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> distribution(String entity) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Etudiant.class)
				.setProjection(
						Projections.projectionList()
						.add(Projections.groupProperty(entity))
						.add(Projections.rowCount())
				);
		return (List<Object[]>)getHibernateTemplate().findByCriteria(criteria);
	}
}