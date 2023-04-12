package fr.eni.enchere.dal.dao;

import java.util.List;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.DALException;

public interface DAOCategorie {

	Categorie selectById(int id) throws DALException;

	List<Categorie> selectAll() throws DALException;

	void insert(Categorie categorie) throws DALException;

	void update(Categorie categorie) throws DALException;

	void delete(Categorie categorie) throws DALException;

	boolean checkForUniqueCategorieLibelle(String libelle) throws DALException;

}
