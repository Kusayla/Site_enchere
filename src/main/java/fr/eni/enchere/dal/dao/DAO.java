package fr.eni.enchere.dal.dao;

import java.util.List;

import fr.eni.enchere.dal.DALException;

public interface DAO<T> {
	  void insert(T var) throws DALException;
	    T selectById(int id) throws DALException;
	    List<T> selectAll() throws DALException;
	    void update(T var) throws DALException;
	    void delete(T var) throws DALException;
}
