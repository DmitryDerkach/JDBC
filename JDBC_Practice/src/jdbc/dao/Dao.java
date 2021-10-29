package jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface Dao <K, E>{
	boolean delete(K id);
	E save(E inputData);
	void update(E inputData);
	Optional<E> findById(K id);
	List<E> findAll();
	
}
