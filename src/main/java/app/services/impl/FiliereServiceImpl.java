package app.services.impl;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.dao.FiliereDao;
import app.entities.Filiere;
import app.services.FiliereService;

@Component
public class FiliereServiceImpl implements FiliereService {

	@Autowired
	private FiliereDao filiereDao;
	
	@Override
	@Transactional
	public boolean save(Filiere o) {
		this.filiereDao.save(o);
		return true;
	}

	@Override
	@Transactional
	public boolean delete(Filiere o) {
		return this.filiereDao.delete(o);
	}

	@Override
	@Transactional
	public Filiere getByID(int id) {
		return this.filiereDao.getByID(id);
	}

	@Override
	@Transactional
	public List<Filiere> getAll() { 
		return this.filiereDao.getAll();
	}

	@Override
	@Transactional
	public List<Filiere> getByExample(Filiere f) {
		return this.filiereDao.getByExample(f, MatchMode.START, false);
	}

	@Override
	@Transactional
	public int countNbrEtudiant(int id) {
		return this.filiereDao.getByID(id).getEtudiants().size();
	}

}