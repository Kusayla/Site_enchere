package fr.eni.enchere.bll;

import fr.eni.enchere.dal.dao.DAOUtilisateur;
import fr.eni.enchere.dal.dao.DAO_Factory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DALException;

public class UtilisateurManager {
	private DAOUtilisateur DAOUtilisateur;

	private static UtilisateurManager instance;

	private UtilisateurManager() {
		this.DAOUtilisateur = DAO_Factory.getUtilisateurDAO();
	}

	public static UtilisateurManager getInstance()
	{
		if (instance == null) {
			instance = new UtilisateurManager();
		}
		return instance;
	}
	
	//Connexion utilisateur
	
	public Utilisateur connexionUtilisateur(String identifiant, String motDePasse) throws DALException, BLLException {
		Utilisateur utilisateur = null;
		Utilisateur utilisateurExiste;
		
		if (identifiant.contains("@"))
		{
			utilisateurExiste = DAOUtilisateur.selectUtilisateurByEmail(identifiant);
		} else {
			utilisateurExiste = DAOUtilisateur.selectUtilisateurByPseudo(identifiant);
		}
		
		if (utilisateurExiste != null)  {
			if(utilisateurExiste.getMotDePasse().equals(motDePasse)) {
				utilisateur = utilisateurExiste;
			}
		}
		
		return utilisateur;
	}

	//Génération cookie 
	
	public String getCookie(HttpServletRequest request, Utilisateur utilisateur)
			throws NoSuchAlgorithmException {
		String noUtilisateur = String.valueOf(utilisateur.getNoUtilisateur());
		String nomUtilisateur = String.valueOf(utilisateur.getNom());
		String motDePasse = utilisateur.getMotDePasse();
		String userAgent = request.getHeader("User-Agent");
		String accept = request.getHeader("Accept");
		String acceptEncoding = request.getHeader("Accept-Encoding");
		String acceptLanguage = request.getHeader("Accept-Language");

		String stringToHash = noUtilisateur + motDePasse + userAgent + accept + acceptEncoding + acceptLanguage
				+ nomUtilisateur;
		byte[] byteArrayToHash = stringToHash.getBytes(StandardCharsets.ISO_8859_1);
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] uniqueId = digest.digest(byteArrayToHash);
		String fingerprint = (new String(Arrays.toString(uniqueId))).replaceAll("[^0123456789]", "");
		return fingerprint;

	}
	
	// Création nouvel utilisateur avec vérification
	public Utilisateur creerUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
	        String codePostal, String ville, String motDePasse) throws BLLException, DALException {
	    Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, 0, false);
	    BLLException bllException = validationUtilisateur(utilisateur);
	    if (!bllException.hasErrors()) {
	        boolean isUniquePseudoAndMail = DAO_Factory.getUtilisateurDAO().checkForUniquePseudoAndMail(pseudo, email);
	        if (isUniquePseudoAndMail) {
	            DAOUtilisateur.insert(utilisateur);
	            return utilisateur;
	        } else {
	            bllException.addError(CodeErreurBLL.PSEUDO_OU_MAIL_DEJA_PRIS);
	        }
	    }
	    throw bllException;
	}

	/**
	 * lire le CRUD
	 * @param pseudo
	 * @return
	 * @throws DALException
	 */
	public Utilisateur getUtilisateurById(String pseudo) throws DALException{
		return DAOUtilisateur.selectUtilisateurByPseudo(pseudo);
	}
	
	public Utilisateur selectById(int noUtilisateur) throws DALException {
		return DAOUtilisateur.selectById(noUtilisateur);
	}
	public List<Utilisateur> getAllUtilisateur() throws DALException{
		return DAOUtilisateur.selectAll();
	}
	/**
	* mise à jour du CRUD
	*/

	public void updateUtilisateur (Utilisateur u ) throws BLLException, DALException{
		BLLException bllException = validationUtilisateur(u);
		
		if(bllException.hasErrors()) {
			throw bllException;
		}else {
		DAOUtilisateur.update(u);
		}
	}
	/**
	 * supprimer une partie du CRUD
	 * @param utilisateur
	 * @throws DALException
	 */
	public void deleteUtilisateur (Utilisateur utilisateur) throws DALException {
		DAOUtilisateur.delete(utilisateur);
    }
	
	// Validation d'un nouvel utilisateur
	
	private BLLException validationUtilisateur(Utilisateur utilisateur) {
        String pseudoValidationChar = "[A-Za-z0-9]+";
        String telephoneNumberValidationInt = "^0[1-9][0-9]{8}$";
        BLLException bllException = new BLLException();
        if (utilisateur.getPseudo().length() > 30) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_PSEUDO_UTILISATEUR);
            }
        if (!Pattern.matches(pseudoValidationChar, utilisateur.getPseudo())) {
            bllException.addError(CodeErreurBLL.ERROR_PSEUDO_NOT_ALPHANUMERIC);
        }
        if (utilisateur.getNom().length() > 30) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_NOM_UTILISATEUR);
        }
        if (utilisateur.getPrenom().length() > 30) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_PRENOM_UTILISATEUR);
        }
        if (utilisateur.getEmail().length() > 40) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_EMAIL_UTILISATEUR);
        }
        if (utilisateur.getTelephone().length() > 15) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_TELEPHONE_UTILISATEUR);
        }
        if (!Pattern.matches(telephoneNumberValidationInt, utilisateur.getTelephone())) {
            bllException.addError(CodeErreurBLL.ERROR_FORMAT_TELEPHONE_UTILISATEUR);
        }
        if (utilisateur.getRue().length() > 30) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_RUE_UTILISATEUR);
        }
        if (utilisateur.getCodePostal().length() > 30) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_CODE_POSTAL_UTILISATEUR);
        }
        if (utilisateur.getVille().length() > 30) {
            bllException.addError(CodeErreurBLL.ERROR_LENGTH_VILLE_UTILISATEUR);
        }

		return bllException;
}
}