package fr.eni.enchere.bll;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.DALException;
import fr.eni.enchere.dal.dao.DAO_Factory;

public class ArticleVenduManager {
	/*
	 * creer un article
	 */
	public ArticleVendu creerArticleVendu(String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres,
			int miseAPrix, int prixDeVente, int noUtilisateur, int noCategorie, String etatVente) throws BLLException, DALException {
	ArticleVendu article = new ArticleVendu(nomArticle, description, dateDebutEncheres, dateFinEncheres,
			 miseAPrix, prixDeVente, noUtilisateur, noCategorie, etatVente);
		BLLException bllException = validationArticleVendu(article);
	if(bllException.hasErrors()) {
		throw bllException;
	}else {
		DAO_Factory.getArticleDAO().insert(article);
		return article;
	}
	}
	/*
	 * Selectionner un article par son ID
	 */
	 public ArticleVendu getArticleById(int id) throws DALException, BLLException {
	        ArticleVendu articleVendu = DAO_Factory.getArticleDAO().selectById(id);
	        if (articleVendu == null) {
	            BLLException bllException = new BLLException();
	            bllException.addError(CodeErreurBLL.ERROR_NO_RESULTS);
	            throw bllException;
	        }
	        return articleVendu;
	    }
	 /*
	  * Mise a jour d'une article
	  */
	 public void updateArticle(ArticleVendu articleToUpdate) throws BLLException, DALException {
	        BLLException bllException = validationArticleVendu(articleToUpdate);
	        if (bllException.hasErrors()) {
	            throw bllException;
	        } else {
	        	DAO_Factory.getArticleDAO().update(articleToUpdate);
	        }
	    }
	 /*
	  * Suppression d'un article
	  */
	 public void deleteArticle(ArticleVendu articleVendu) throws DALException {
		 DAO_Factory.getArticleDAO().delete(articleVendu);
	    }
	 /*
	  * selection de tous les articles
	  */
	   public static List<ArticleVendu> getAllArticlesVendus() throws DALException, BLLException {
	        List<ArticleVendu> articlesVendus = DAO_Factory.getArticleDAO().selectAll();
	        if (articlesVendus.isEmpty()) {
	            BLLException bllException = new BLLException();
	            bllException.addError(CodeErreurBLL.ERROR_NO_RESULTS);
	            throw bllException;
	        } else {
	            return articlesVendus;
	        }
	    }
	   /*
	    * lister les articles en fonction de leur etats
	    */
	   public List<Integer> getArticlesByEtat(String etat) throws DALException, BLLException {
	        List<Integer> articlesVendus = DAO_Factory.getArticleDAO().filtrerParEtat(etat);
	        if (articlesVendus.isEmpty()) {
	            BLLException bllException = new BLLException();
	            bllException.addError(CodeErreurBLL.ERROR_NO_RESULTS);
	            throw bllException;
	        } else {
	            return articlesVendus;
	        }
	    }
	   /*
	    * selection d'article par leurs categorie
	    */
	   public List<ArticleVendu> getArticlesFromCategory(Categorie categorie) throws DALException, BLLException {
	        List<ArticleVendu> articlesVendus = DAO_Factory.getArticleDAO().filtrerParCategorie(categorie);
	        if (articlesVendus.isEmpty()) {
	            BLLException bllException = new BLLException();
	            bllException.addError(CodeErreurBLL.ERROR_NO_RESULTS);
	            throw bllException;
	        } else {
	            return articlesVendus;
	        }
	    }
	    public List<ArticleVendu> getArticlesFilterByNomArticle(String filter) throws DALException, BLLException {
	        List<ArticleVendu> articlesVendus = DAO_Factory.getArticleDAO().filtrerParString(filter);
	        if (articlesVendus.isEmpty()) {
	            BLLException bllException = new BLLException();
	            bllException.addError(CodeErreurBLL.ERROR_NO_RESULTS);
	            throw bllException;
	        } else {
	            return articlesVendus;
	        }
	    }
	/*
	 * methode de validation pour l'utilisation de ArticleVenduManager
	 */
	   private BLLException validationArticleVendu(ArticleVendu articleVendu) {
	        BLLException bllException = new BLLException();
	        if (articleVendu.getNomArticle().length() > 30) {
	            bllException.addError(CodeErreurBLL.ERROR_LENGTH_NOM_ARTICLE);
	        }
	        if (articleVendu.getDescription().length() > 300) {
	            bllException.addError(CodeErreurBLL.ERROR_LENGTH_DESCRIPTION_ARTICLE);
	        }
	        String[] acceptedValuesEtatVente = {"EC", "PV", "VE"};
	        if (!Arrays.asList(acceptedValuesEtatVente).contains(articleVendu.getEtatVente())) {
	            bllException.addError(CodeErreurBLL.ERROR_VALUE_STATUT_VENTE_ARTICLE);
	        }
	        if (articleVendu.getDateDebutEncheres().after(articleVendu.getDateFinEncheres())) {
	            bllException.addError(CodeErreurBLL.ERROR_START_DATE_AFTER_END_DATE);
	        }
	        if (articleVendu.getDateDebutEncheres().before(new Date()) || articleVendu.getDateFinEncheres().before(new Date())) {
	            bllException.addError(CodeErreurBLL.ERROR_DATE_BEFORE_TODAY);
	        }
	        return bllException;
	    }
}
