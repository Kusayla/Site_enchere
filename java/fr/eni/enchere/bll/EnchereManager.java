package fr.eni.enchere.bll;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DALException;
import fr.eni.enchere.dal.dao.DAO_Factory;

public class EnchereManager {

	// creer une enchere
	public static Enchere creationEnchere(Timestamp dateEnchere, int montantEnchere, int noArticle, int noUtilisateur ) throws DALException, BLLException {
		Enchere enchere = new Enchere(dateEnchere, montantEnchere, noArticle, noUtilisateur);
		// selection de l'article et de l'utilisateur
		ArticleVendu article = DAO_Factory.getArticleDAO().selectById(noArticle);
		Utilisateur utilisateur = DAO_Factory.getUtilisateurDAO().selectById(noUtilisateur);
		// verification de la validité de l'enchere
		BLLException bllException = validerEnchere(enchere, article, utilisateur);
		if(bllException.hasErrors()) {
			throw bllException;
		}else {
			System.out.println("tentative d'insertion creationEnchere: " + enchere );
			DAO_Factory.getEnchereDAO().insert(enchere);
			return enchere;
		}
	}
	
	// updater enchere
	public Enchere  updateEnchere(int noEnchere, Timestamp dateEnchere, int montantEnchere, int noArticle, int noUtilisateur) throws DALException, BLLException {
	    Enchere enchere = DAO_Factory.getEnchereDAO().selectById(noEnchere);
	    enchere.setDateEnchere(dateEnchere);
	    enchere.setMontantEnchere(montantEnchere);
	    enchere.setNoArticle(noArticle);
	    enchere.setNoUtilisateur(noUtilisateur);
	    ArticleVendu article = DAO_Factory.getArticleDAO().selectById(noArticle);
	    Utilisateur utilisateur = DAO_Factory.getUtilisateurDAO().selectById(noUtilisateur);
	    BLLException bllException = validerEnchere(enchere, article, utilisateur);
	    if (bllException.hasErrors()) {
	        throw bllException;
	    } else {
	        DAO_Factory.getEnchereDAO().update(enchere);
	        return enchere;
	    }
	}
	
	// lister les encheres en cours
	public static List<Enchere> getAllEncheres() throws DALException {
	    List<Enchere> encheres = new ArrayList<>();
	    List<Integer> articlesEnVente = DAO_Factory.getArticleDAO().filtrerParEtat("EC");
	    for(Integer noArticle : articlesEnVente) {
	        encheres.add(DAO_Factory.getEnchereDAO().selectById(noArticle));
	    }
	   // syso pour test : 
	    System.out.println("de getAllEncheres : " + encheres.toString());
	    return encheres;
	}
	
	public static Enchere getSpecificEnchere(String id) throws DALException {
		int ID = Integer.parseInt(id);
		Enchere enchere = DAO_Factory.getEnchereDAO().selectById(ID);
		return enchere;
	}
	
	// supprimer une enchere
	public void deleteEnchere(int noEnchere) throws DALException, BLLException {
	    Enchere enchere = DAO_Factory.getEnchereDAO().selectById(noEnchere);
		try {
	        // Suppression de l'enchère
	        DAO_Factory.getEnchereDAO().delete(enchere);
	    } catch (DALException e) {
	        BLLException bllException = new BLLException();
	        bllException.addError(CodeErreurBLL.ERROR_DELETE_ENCHERE);
	        throw bllException;
	    }
	}


	// afficher les détails d'une enchere
		
	public static Map<String, Object> afficheEnchereDetails(Enchere enchere) throws DALException, BLLException {
		Map<String, Object> enchereDetails = new HashMap<>();
		
		ArticleVendu article = DAO_Factory.getArticleDAO().selectById(enchere.getNoArticle());
		Utilisateur utilisateur = DAO_Factory.getUtilisateurDAO().selectById(article.getNoUtilisateur());
		
		enchereDetails.put("Id", enchere.getNoEnchere());
		enchereDetails.put("Prix", enchere.getMontantEnchere());
		enchereDetails.put("PrixDeBase", article.getMiseAPrix());
		enchereDetails.put("NomArticle", article.getNomArticle());
		enchereDetails.put("Description", article.getDescription());
		enchereDetails.put("DateDeFin", article.getDateFinEncheres());
		enchereDetails.put("Vendeur", utilisateur.getPrenom() + " " + utilisateur.getNom());
		
		return enchereDetails;
	}
	
	public static void encherir(String idEnchere, int nouveauMontant, Utilisateur acquereur) throws DALException {
		Enchere enchere = getSpecificEnchere(idEnchere);
		int montantUtilisateur = acquereur.getCredit();
		if (montantUtilisateur - enchere.getMontantEnchere() > -1) {
			acquereur.setCredit(montantUtilisateur - enchere.getMontantEnchere());
			enchere.setMontantEnchere(enchere.getMontantEnchere() + 1);
			DAO_Factory.getEnchereDAO().update(enchere);
			DAO_Factory.getUtilisateurDAO().update(acquereur);
		}
		
	    
	}
	
	
		
	// Validation d'Enchere	
	private static BLLException validerEnchere(Enchere enchere, ArticleVendu article, Utilisateur utilisateur) {
        BLLException bllException = new BLLException();
        // si le montant de l'enchere est inferieur à 1
        if (enchere.getMontantEnchere() < 1 ) {
            bllException.addError(CodeErreurBLL.ERROR_ENCHERE_PRICE);
        }
        if (article == null) {
            bllException.addError(CodeErreurBLL.ERROR_NO_RESULTS);
        }
        if (utilisateur == null) {
            bllException.addError(CodeErreurBLL.ERROR_NO_UTILISATEUR);
        }
//        if (enchere.getDateEnchere().after(article.getDateFinEncheres())) {
//            bllException.addError(CodeErreurBLL.ERROR_START_DATE_AFTER_END_DATE);
//        }
//        if (enchere.getDateEnchere().before(new Date()) || article.getDateFinEncheres().before(new Date())) {
//            bllException.addError(CodeErreurBLL.ERROR_DATE_BEFORE_TODAY);
//        }
        return bllException;
    }
	
}
