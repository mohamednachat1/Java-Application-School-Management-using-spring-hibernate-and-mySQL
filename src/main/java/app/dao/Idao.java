package app.dao;

import java.util.List;

import org.hibernate.criterion.MatchMode;

public interface Idao<T> {
	void save(T o);
	boolean delete (T o);
	T getByID(int id);
	List<T> getAll();
	List<T> getByExample(T o, MatchMode mode, boolean detached);
}