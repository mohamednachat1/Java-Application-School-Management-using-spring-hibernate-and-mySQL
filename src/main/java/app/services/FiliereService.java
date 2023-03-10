package app.services;

import java.util.List;
import app.entities.Filiere;

public interface FiliereService {

	boolean save(Filiere o);
	boolean delete (Filiere o);
	Filiere getByID(int id);
	List<Filiere> getAll();
	List<Filiere> getByExample(Filiere f);
	int countNbrEtudiant(int id);
}
