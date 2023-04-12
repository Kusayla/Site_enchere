package fr.eni.enchere.bll;

abstract class CodeErreurBLL {
	static final String PSEUDO_OU_MAIL_DEJA_PRIS = "pseudo ou email deja pris";
	public static final String ERROR_LENGTH_PSEUDO_UTILISATEUR = "Pseudo trop long";
	public static final String ERROR_PSEUDO_NOT_ALPHANUMERIC = "le Pseudo ne doit contenir que des charactères alphanumérique";
	public static final String ERROR_LENGTH_NOM_UTILISATEUR = "Nom trop long";
	public static final String ERROR_LENGTH_PRENOM_UTILISATEUR = "Prenom trop long";
	public static final String ERROR_LENGTH_EMAIL_UTILISATEUR = "Email trop long";
	public static final String ERROR_LENGTH_TELEPHONE_UTILISATEUR = "Téléphone trop long";
	public static final String ERROR_FORMAT_TELEPHONE_UTILISATEUR = "le numéro de téléphone ne doit contenir que des chiffres";
	public static final String ERROR_LENGTH_RUE_UTILISATEUR = "Nom de rue trop long";
	public static final String ERROR_LENGTH_CODE_POSTAL_UTILISATEUR = "Code Postal trop long";
	public static final String ERROR_LENGTH_VILLE_UTILISATEUR = "Nom de ville trop long";
	public static final String ERROR_LENGTH_NOM_ARTICLE = "Nom d'article trop long";
	public static final String ERROR_LENGTH_DESCRIPTION_ARTICLE = "Description d'article trop long";
	public static final String ERROR_VALUE_STATUT_VENTE_ARTICLE = "Statut de l'etat de vente non conforme";
	public static final String ERROR_START_DATE_AFTER_END_DATE = "La date de debut d'enchère ne peu pas être superieur à celle de fin";
	public static final String ERROR_DATE_BEFORE_TODAY = "Les dates ne peuvent pas être anterieur à la date d'aujourd'hui";
	public static final String ERROR_NO_RESULTS = "Pas d'article avec cet Id";
	public static final String ERROR_NO_ENCHERE = "Pas d'enchere avec cet Id";
	public static final String ERROR_NO_UTILISATEUR = "Pas d'utilisateur ayant cet ID";
	public static final String ERROR_DELETE_ENCHERE = "Erreur lors de la suppression de l'enchere";
	public static final String ERROR_ENCHERE_PRICE = "Erreur : le prix de l'enchère n'est pas valide";
	public static final String ERROR_LENGTH_LIBELLE_CATEGORIE = "libellé de categorie trop long";
	public static final String ERROR_LIBELLE_CATEGORIE_ALREADY_TAKEN = "libellé de catégorie deja existant";
	public static final String ERROR_LENGTH_RUE_RETRAIT = "Nom de la rue trop long";
	public static final String ERROR_LENGTH_CODE_POSTAL_RETRAIT = "Code postal trop long";
	public static final String ERROR_LENGTH_VILLE_RETRAIT = "Nom de ville trop long";
	
}
