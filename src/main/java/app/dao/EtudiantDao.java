package app.dao;

import java.util.List;

import app.entities.Etudiant;

public interface EtudiantDao extends Idao<Etudiant>
{
	List<Object[]> distribution(String entity);
}
