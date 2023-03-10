package app.services;

import java.util.List;
import app.entities.Etudiant;

public interface EtudiantService {
	boolean save(Etudiant o);
	boolean delete (Etudiant o);
	Etudiant getByID(int id);
	List<Etudiant> getAll();
	List<Etudiant> getByExample(Etudiant e);
	List<Object[]> distributionByEntity();
	List<Object[]> distributionByFiliere();
}
