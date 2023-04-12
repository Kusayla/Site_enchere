package fr.eni.enchere.dal.dao;


import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.dal.DALException;


public interface DAORetrait {

	Retrait selectById(int noArticle) throws DALException;

	void update(Retrait retrait) throws DALException;

	void insert(Retrait retrait) throws DALException;

	void delete(Retrait retrait) throws DALException;

}
