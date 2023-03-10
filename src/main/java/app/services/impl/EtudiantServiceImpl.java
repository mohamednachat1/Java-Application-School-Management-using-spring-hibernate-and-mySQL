package app.services.impl;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.dao.EtudiantDao;
import app.entities.Etudiant;
import app.services.EtudiantService;

@Component
public class EtudiantServiceImpl implements EtudiantService {

	@Autowired
	private EtudiantDao EtudiantDao;
	@Override
	@Transactional
	public boolean save(Etudiant o) {
		Etudiant etd = new Etudiant();
		etd.setCne(o.getCne());
		System.out.println("SAVED");
		this.EtudiantDao.save(o);
		return true;
	}

	@Override
	@Transactional
	public boolean delete(Etudiant o) {
		return this.EtudiantDao.delete(o);
	}

	@Override
	@Transactional
	public Etudiant getByID(int id) {
		return this.EtudiantDao.getByID(id);
	}

	@Override
	@Transactional
	public List<Etudiant> getAll() {
		
		return this.EtudiantDao.getAll();
	}
	
	@Override
	@Transactional
	public List<Etudiant> getByExample(Etudiant e) {
		// TODO Auto-generated method stub
		return this.EtudiantDao.getByExample(e, MatchMode.START, false);
	}

	@Override
	@Transactional
	public List<Object[]> distributionByEntity() {
		return this.EtudiantDao.distribution("filiere");
	}

	@Override
	@Transactional
	public List<Object[]> distributionByFiliere() {
		return this.EtudiantDao.distribution("filiere");
	}

}
