package fr.eni.enchere.dal;

import java.util.ArrayList;
import java.util.List;

public class DALException extends Exception{
	private static final long serialVersionUID = 1L;
private List<String> listErrorCodes;
    
    public DALException() {
        super();
        this.listErrorCodes=new ArrayList<>();
    }

    /**
     *ajoute un code erreur
     * @param erreurSqlSelect 
     */
    public void addError(String erreurSqlSelect)
    {
        if(!this.listErrorCodes.contains(erreurSqlSelect))
        {
            this.listErrorCodes.add(erreurSqlSelect);
        }
    }

    /**
     * Renvoie true si l’instance contient des codes d’erreur et false si non
     * @return boolean
     */
    public boolean hasErrors()
    {
        return this.listErrorCodes.size()>0;
    }

    /**
     * Renvoie la liste des codes d’erreur stockés dans l’instance
     * @return List
     */
    public List<String> getListErrorCodes()
    {
        return this.listErrorCodes;
    }

}

