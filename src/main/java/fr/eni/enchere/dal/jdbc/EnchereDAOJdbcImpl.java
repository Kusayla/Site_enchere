package fr.eni.enchere.dal.jdbc;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.dal.DALException;
import fr.eni.enchere.dal.dao.DAOEnchere;

public class EnchereDAOJdbcImpl implements DAOEnchere {

	private final String SQLINSERT = "INSERT INTO ENCHERES " 
			+ "(date_enchere , montant_enchere , no_article , no_utilisateur) " 
			+ "VALUES (?, ?, ?, ?)";
	private final String SQLUPDATE = "UPDATE ENCHERES SET date_enchere=?, montant_enchere=?, no_article=?, no_utilisateur=? "
            + "where no_enchere=?";
	private final String SQLDELETE = "DELETE FROM ENCHERES WHERE no_enchere=?"
			+ "DELETE FROM ENCHERES WHERE date_enchere =?";
	
			public void insert(Enchere a) throws DALException {
		Connection cnx = JdbcConnexion.connect();
		PreparedStatement stmt;
		try {
			cnx.setAutoCommit(false);
			stmt=cnx.prepareStatement(SQLINSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			encherePreparedStatement(a, stmt);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
                a.setNoEnchere(rs.getInt(1));
		}
			cnx.commit();
			cnx.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERREUR INSERTION EnchereDAOJdbcImpl");
		}	
	}

/**
 * Extraire les données de la base par l'id
 * @param id int
 * @return ArticleVendu
 * @throws SQLException
 */
public Enchere selectById(int id) throws DALException {
	Connection cnx = JdbcConnexion.connect();
	Enchere u = null;
	try {
		String SELECT_BY_ID = "SELECT * FROM ENCHERES WHERE no_enchere= ?";
		PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ID);
		stmt.setInt(1, id);
		stmt.execute();
		ResultSet rs = stmt.getResultSet();
		if(rs.next()) {
			u = enchereResultSet(rs);
		}
		// pour test
		System.out.println("enchere selectionné par id : " + u);
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
public List<Enchere> selectAll() throws DALException {
	Connection cnx = JdbcConnexion.connect();
	List<Enchere> liste = new ArrayList<>();
	try {
		Statement stmt = cnx.createStatement();
		String SELECT_ALL = "SELECT * FROM ENCHERES";
		stmt.execute(SELECT_ALL);
		ResultSet rs = stmt.getResultSet();
		while (rs.next()) {
			liste.add(enchereResultSet(rs));
		}
		cnx.close();
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("ERREUR * SELECTION");
	}
	return liste;
}

/**
 * Mise a jour de la base de données 
 * @param a ArticleVendu
 */
public void update(Enchere a) throws DALException {
	Connection cnx = JdbcConnexion.connect();
	try {
		cnx.setAutoCommit(false);
		System.out.println("Update Enchere (EnchereDAOJdbcImpl) : " + a.toString());
		PreparedStatement stmt = cnx.prepareStatement(SQLUPDATE);
		encherePreparedStatement(a, stmt);
		stmt.setInt(5, a.getNoEnchere());
		stmt.executeUpdate();
		cnx.commit();
		cnx.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public void delete(Enchere a) throws DALException{
	Connection cnx = JdbcConnexion.connect();
	try {
		PreparedStatement stmt = cnx.prepareStatement(SQLDELETE);
		stmt.setInt(1, a.getNoEnchere());
		stmt.setTimestamp(2, (Timestamp) (a.getDateEnchere()));
		stmt.executeUpdate();
		cnx.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

	//instruction utilisée par les demandes INSERT et UPDATE
	private void encherePreparedStatement(Enchere a, PreparedStatement stmt) throws SQLException {
	    stmt.setTimestamp(1, (Timestamp) a.getDateEnchere());
	    stmt.setInt(2, a.getMontantEnchere());
	    stmt.setInt(3, a.getNoArticle());
	    stmt.setInt(4, a.getNoUtilisateur());
}

	
	// instance à partir d’un ResultSet
	 private Enchere enchereResultSet(ResultSet rs) throws SQLException {
	        return new Enchere(
	                rs.getInt("no_enchere"),
	                rs.getTimestamp("date_enchere"),
	                rs.getInt("montant_enchere"),
	                rs.getInt("no_article"),
	                rs.getInt("no_utilisateur")
	        );
	    }
}
