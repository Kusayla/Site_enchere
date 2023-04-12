package fr.eni.enchere.dal.dao;

import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.DALException;

public interface DAOArticleVendu {

	ArticleVendu selectById(int id) throws DALException;

	List<ArticleVendu> selectAll() throws DALException;

	void update(ArticleVendu a) throws DALException;

	void insert(ArticleVendu a) throws DALException;

	void delete(ArticleVendu a) throws DALException;

	List<Integer> filtrerParEtat(String etat) throws DALException;

	List<ArticleVendu> filtrerParCategorie(Categorie categorie) throws DALException;

	List<ArticleVendu> filtrerParString(String filter)throws DALException;
}
