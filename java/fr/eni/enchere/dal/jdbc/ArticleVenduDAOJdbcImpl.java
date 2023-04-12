package fr.eni.enchere.dal.jdbc;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.CodeErreurDAL;
import fr.eni.enchere.dal.DALException;
import fr.eni.enchere.dal.dao.DAOArticleVendu;


public class ArticleVenduDAOJdbcImpl implements DAOArticleVendu {
	private final String SQLINSERT = "INSERT INTO ARTICLES_VENDUS " 
	        + "(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie, etat_vente) " 
	        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private final String SQLUPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, telephone=?, "
			+ "prix_initial=?, prix_vente=?, no_utilisateur=?, no_categorie=? where no_article=?";
	private final String SQLDELETE = "DELETE FROM ARTICLES_VENDUS WHERE no_article=?"
			+ "DELETE FROM ARTICLES_VENDUS WHERE nom_article=?";
	
	public void insert(ArticleVendu a) throws DALException {
	    Connection cnx = JdbcConnexion.connect();
	    PreparedStatement stmt;
	    try {
	        cnx.setAutoCommit(false);
	        stmt=cnx.prepareStatement(SQLINSERT, PreparedStatement.RETURN_GENERATED_KEYS);
	        articleVenduPreparedStatement(a, stmt);
	        stmt.executeUpdate();
	        ResultSet rs = stmt.getGeneratedKeys();
	        if (rs.next()) {
	            a.setNoArticle(rs.getInt(1));
	        }
	        cnx.commit();
	        cnx.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("ERREUR INSERTION");
	    }   
	}


/**
 * Extraire les données de la base par l'id
 * @param id int
 * @return ArticleVendu
 * @throws SQLException
 */
public ArticleVendu selectById(int id) throws DALException {
	Connection cnx = JdbcConnexion.connect();
	ArticleVendu u = null;
	try {
		String SELECT_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article= ?";
		PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ID);
		stmt.setInt(1, id);
		stmt.execute();
		ResultSet rs = stmt.getResultSet();
		if(rs.next()) {
			u = articleVenduResultSet(rs);
		}
		cnx.close();
	}catch (SQLException e) {
		e.printStackTrace();
		System.out.println("ERREUR SELECTION");
	}
	return u;
}

/**
 * 
 * Selectionner tout les ArticleVendus de la base de données
 * @return Arraylist
 */
public List<ArticleVendu> selectAll() throws DALException {
	Connection cnx = JdbcConnexion.connect();
	List<ArticleVendu> liste = new ArrayList<>();
	try {
		Statement stmt = cnx.createStatement();
		String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS";
		stmt.execute(SELECT_ALL);
		ResultSet rs = stmt.getResultSet();
		while (rs.next()) {
			liste.add(articleVenduResultSet(rs));
		}
		cnx.close();
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("ERREUR * SELECTION");
	}
	return liste;
}
/*
 * selection d'article en fonction de leurs categorie
 */
public List<ArticleVendu> filtrerParCategorie(Categorie categorie) throws DALException {
    Connection cnx = JdbcConnexion.connect();
    List<ArticleVendu> articlesVendus = new ArrayList<>();
    try {
        String SELECT_BY_CATEGORY = "SELECT * " +
                "FROM ARTICLES_VENDUS " +
                "INNER JOIN CATEGORIES C on ARTICLES_VENDUS.no_categorie = C.no_categorie " +
                "WHERE C.no_categorie = ?";
        PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_CATEGORY);
        stmt.setInt(1, categorie.getNoCategorie());
        stmt.execute();
        ResultSet rs =  stmt.getResultSet();
        while (rs.next()) {
            articlesVendus.add(articleVenduResultSet(rs));
        }
        cnx.close();
    } catch (SQLException e) {
        e.printStackTrace();
        DALException dalException = new DALException();
        dalException.addError(CodeErreurDAL.ERREUR_SQL_SELECT);
        throw dalException;
    }
    return articlesVendus;
}
/*
 * filtrer par nom d'article
 */
public List<ArticleVendu> filtrerParString(String filter) throws DALException {
    Connection cnx = JdbcConnexion.connect();
    List<ArticleVendu> articlesVendus = new ArrayList<>();
    try {
        String SELECT_BY_NAME_SEARCH = "SELECT * " +
                "FROM ARTICLES_VENDUS " +
                "WHERE nom_article LIKE ?";
        PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_NAME_SEARCH);
        stmt.setString(1, "%" + filter + "%");
        stmt.execute();
        ResultSet rs = stmt.getResultSet();
        while(rs.next()) {
            articlesVendus.add(articleVenduResultSet(rs));
        }
        cnx.close();
    } catch (SQLException e) {
        e.printStackTrace();
        DALException dalException = new DALException();
        dalException.addError(CodeErreurDAL.ERREUR_SQL_SELECT);
        throw dalException;
    }
    return articlesVendus;
}

/**
 * Mise a jour de la base de données 
 * @param a ArticleVendu
 */
public void update(ArticleVendu a) throws DALException {
	Connection cnx = JdbcConnexion.connect();
	try {
		PreparedStatement stmt = cnx.prepareStatement(SQLUPDATE);
		articleVenduPreparedStatement(a, stmt);
		stmt.setInt(12, a.getNoUtilisateur());
		stmt.executeUpdate();
		cnx.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public void delete(ArticleVendu a) throws DALException{
	Connection cnx = JdbcConnexion.connect();
	try {
		PreparedStatement stmt = cnx.prepareStatement(SQLDELETE);
		stmt.setInt(1, a.getNoArticle());
		stmt.setString(2, a.getNomArticle());
		stmt.executeUpdate();
		cnx.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
/*
 * filtrer les articles par etat 
 * ETATS POSSIBLES :
 * EC > En Cours
 * NV > Non Vendu
 * VE > VEndu
 */
public List<Integer> filtrerParEtat(String etat) throws DALException {
    Connection cnx = JdbcConnexion.connect();
    List<Integer> articlesVendus = new ArrayList<>();
    try {
        String SELECT_BY_ETAT = "SELECT AV.no_article FROM ARTICLES_VENDUS AV WHERE AV.etat_vente = ?";
        PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ETAT);
        stmt.setString(1, etat);
        stmt.execute();
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            articlesVendus.add(rs.getInt("no_article"));
        }
     // pour test
        System.out.println("articles vendus filtrés" + articlesVendus.toString());
        cnx.close();
    } catch (SQLException e) {
        e.printStackTrace();
        DALException dalException = new DALException();
        dalException.addError(CodeErreurDAL.ERREUR_SQL_SELECT);
        throw dalException;
    }
    return articlesVendus;
}

	//instruction utilisée par les demandes INSERT et UPDATE
	private void articleVenduPreparedStatement (ArticleVendu a, PreparedStatement stmt) throws SQLException {
	    stmt.setString(1, a.getNomArticle());
	    stmt.setString(2, a.getDescription());
	    stmt.setTimestamp(3, new java.sql.Timestamp(a.getDateDebutEncheres().getTime()));
	    stmt.setTimestamp(4, new java.sql.Timestamp(a.getDateFinEncheres().getTime()));
	    stmt.setInt(5, a.getMiseAPrix());
	    stmt.setInt(6, a.getNoUtilisateur());
	    stmt.setInt(7, a.getNoCategorie());
	    stmt.setString(8, a.getEtatVente());
}

	
	// instance à partir d’un ResultSet
	private ArticleVendu articleVenduResultSet(ResultSet rs) throws SQLException {
	    return new ArticleVendu(
	            rs.getInt("no_article"),
	            rs.getString("nom_article"),
	            rs.getString("description"),
	            rs.getTimestamp("date_debut_encheres"),
	            rs.getTimestamp("date_fin_encheres"),
	            rs.getInt("prix_initial"),
	            rs.getInt("prix_vente"),
	            rs.getInt("no_utilisateur"),
	            rs.getInt("no_categorie"),
	            rs.getString("etat_vente")
	    );
	}

}