package fr.eni.enchere.dal.dao;

import fr.eni.enchere.dal.jdbc.ArticleVenduDAOJdbcImpl;
import fr.eni.enchere.dal.jdbc.CategorieDAOJdbcImpl;
import fr.eni.enchere.dal.jdbc.EnchereDAOJdbcImpl;
import fr.eni.enchere.dal.jdbc.RetraitDAOJdbcImpl;
import fr.eni.enchere.dal.jdbc.UtilisateurDAOJdbcImpl;

public class DAO_Factory {

	// Implementation des methodes statiques pour créer les objects DAO
	public static DAOArticleVendu getArticleDAO() {
		return new ArticleVenduDAOJdbcImpl();
	}
	
	public static DAOCategorie getCategorieDAO() {
		return new CategorieDAOJdbcImpl();
	}
	
	public static DAORetrait getRetraitDAO() {
		return new RetraitDAOJdbcImpl();
	}
	
	public static DAOEnchere getEnchereDAO() {
		return new EnchereDAOJdbcImpl();
	}
	
	public static DAOUtilisateur getUtilisateurDAO() {
		return new UtilisateurDAOJdbcImpl();
	}
	
}