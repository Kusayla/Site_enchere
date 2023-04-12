package fr.eni.enchere.bll;

import java.util.List;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.DALException;
import fr.eni.enchere.dal.dao.DAO_Factory;

public class CategorieManager {
/*
 * Creer une categorie
 */
	public void createCategorie(Categorie categorie) throws DALException, BLLException {
        BLLException bllException = new BLLException();
        if (categorie.getLibelle().length() > 30) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_LIBELLE_CATEGORIE);
        }
        if (!DAO_Factory.getCategorieDAO().checkForUniqueCategorieLibelle(categorie.getLibelle())) {
            bllException.addError(CodeErreurBLL.ERROR_LIBELLE_CATEGORIE_ALREADY_TAKEN);
        }
        if (bllException.hasErrors()) {
            throw bllException;
        } else {
        	DAO_Factory.getCategorieDAO().insert(categorie);
        }
    }
	public Categorie getCategorieById(int id) throws DALException {
        return DAO_Factory.getCategorieDAO().selectById(id);
    }
	  public List<Categorie> getAllCategories() throws DALException {
	        return DAO_Factory.getCategorieDAO().selectAll();
	    }
}
