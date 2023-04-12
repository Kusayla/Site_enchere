package fr.eni.enchere.dal.dao;

import java.util.List;

import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.dal.DALException;

public interface DAOEnchere {
	
	Enchere selectById(int id) throws DALException;

	List<Enchere> selectAll() throws DALException;

	void update(Enchere a) throws DALException;

	void insert(Enchere a) throws DALException;

	void delete(Enchere a) throws DALException;
}
