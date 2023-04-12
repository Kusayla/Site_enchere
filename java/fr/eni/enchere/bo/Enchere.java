package fr.eni.enchere.bo;

import java.sql.Timestamp;

public class Enchere {
	private int noEnchere;
	private Timestamp dateEnchere;
	private int montantEnchere;
	private int noArticle;
	private int noUtilisateur;
	/**
	 * Constructeur par défaut
	 */
	public Enchere() {
	}
	
	/**
	 * @param noEnchere 		int non null PK
	 * @param dateEnchere 		Datetime
	 * @param montantEnchere	int non null
	 * @param noArticle 		FK vers ArticleVendu
	 * @param noUtilisateur 	FK vers Utilisateur
	 */
	public Enchere(int noEnchere, Timestamp dateEnchere, int montantEnchere, int noArticle, int noUtilisateur) {
		this.noEnchere = noEnchere;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.noArticle = noArticle;
		this.noUtilisateur = noUtilisateur;
	}
	
	public Enchere(Timestamp dateEnchere, int montantEnchere, int noArticle, int noUtilisateur) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.noArticle = noArticle;
		this.noUtilisateur = noUtilisateur;
	}

	/**
	 * @return the noEnchere
	 */
	public int getNoEnchere() {
		return noEnchere;
	}

	/**
	 * @param noEnchere the noEnchere to set
	 */
	public void setNoEnchere(int noEnchere) {
		this.noEnchere = noEnchere;
	}

	/**
	 * @return the dateEnchere
	 */
	public Timestamp getDateEnchere() {
		return dateEnchere;
	}

	/**
	 * @param dateEnchere the dateEnchere to set
	 */
	public void setDateEnchere(Timestamp dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	/**
	 * @return the montantEnchere
	 */
	public int getMontantEnchere() {
		return montantEnchere;
	}

	/**
	 * @param montantEnchere the montantEnchere to set
	 */
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	/**
	 * @return the noArticle
	 */
	public int getNoArticle() {
		return noArticle;
	}

	/**
	 * @param noArticle the noArticle to set
	 */
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	/**
	 * @return the noUtilisateur
	 */
	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	/**
	 * @param noUtilisateur the noUtilisateur to set
	 */
	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	@Override
	public String toString() {
		return "Enchere [noEnchere=" + noEnchere + ", dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere
				+ ", noArticle=" + noArticle + ", noUtilisateur=" + noUtilisateur + "]";
	}

	public Enchere creationEnchere(java.sql.Timestamp sqlDate, int montantEnchere2, int noArticle2, int noUtilisateur2) {
		// TODO Auto-generated method stub
		return null;
	}
}