package fr.eni.enchere.dal.dao;

import java.util.List;

import fr.eni.enchere.bll.BLLException;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DALException;

public interface DAOUtilisateur {
	Utilisateur selectById(int noUtilisateur) throws DALException;

	Utilisateur selectUtilisateurByPseudo(String pseudo) throws DALException;
	
	Utilisateur selectUtilisateurByEmail(String email) throws DALException;

	List<Utilisateur> selectAll() throws DALException;

	void update(Utilisateur u) throws DALException;

	void insert(Utilisateur utilisateur) throws DALException;

	void delete(Utilisateur u) throws DALException;

	boolean checkForUniquePseudoAndMail(String pseudo, String mail) throws DALException;

	boolean checkForUniquePseudo(String pseudo) throws DALException;

	boolean checkForUniqueMail(String mail) throws DALException;
	
	boolean verifMDP(Utilisateur utilisateur, String MotDePasse) throws DALException, BLLException;
}