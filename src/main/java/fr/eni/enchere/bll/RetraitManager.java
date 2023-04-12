package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.dal.DALException;
import fr.eni.enchere.dal.dao.DAO_Factory;

public class RetraitManager {
/*
 * creer un retrait
 */
    public void createRetrait(Retrait retrait) throws DALException, BLLException {
        BLLException bllException = validationRetrait(retrait);
        if (bllException.hasErrors()) {
            throw bllException;
        } else {
            DAO_Factory.getRetraitDAO().insert(retrait);
        }
    }
    /*
     * recupérer un retrait par numero d'article
     */
    public Retrait getRetraitByNoArticle(int noArticle) throws DALException {
        return DAO_Factory.getRetraitDAO().selectById(noArticle);
    }
    /*
     * mise a jour d'un retrait
     */
    public void updateRetrait(Retrait retrait) throws DALException, BLLException {
        BLLException bllException = validationRetrait(retrait);
        if (bllException.hasErrors()) {
            throw bllException;
        } else {
        	DAO_Factory.getRetraitDAO().update(retrait);
        }
    }
    /*
     * supprimer un retrait
     */
    public void deleteRetrait(Retrait retrait) throws DALException {
    	DAO_Factory.getRetraitDAO().delete(retrait);
    }
    /*
     * Valider le format des différents champs d’une instance de retrait avant toute action avec la DB
     * Vérifier la longueur des différents champs
     */
    private BLLException validationRetrait(Retrait retraitAValide) {
        BLLException bllException = new BLLException();
        if (retraitAValide.getRue().length() > 30) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_RUE_RETRAIT);
        }
        if (retraitAValide.getCodePostal().length() > 15) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_CODE_POSTAL_RETRAIT);
        }
        if (retraitAValide.getVille().length() > 30) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_VILLE_RETRAIT);
        }
        return bllException;
    }
}
