package fr.eni.enchere.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DALException;
import fr.eni.enchere.dal.dao.DAORetrait;

public class RetraitDAOJdbcImpl implements DAORetrait{
	private final String SQLINSERT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?, ?, ?, ?)";
	private final String SELECT_BY_ID = "SELECT * FROM RETRAITS WHERE no_article = ?";
	private final String SQLUPDATE = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
	private final String SQLDELETE = "DELETE FROM RETRAITS WHERE no_article = ? ";
	/**
	 * Insérer des données dans la base
	 * @param retrait
	 * @throws DALException
	 */
	public void insert(Retrait retrait) throws DALException {
		Connection cnx = JdbcConnexion.connect();
		try {
			PreparedStatement stmt = cnx.prepareStatement(SQLINSERT);
			stmt.setInt(1, retrait.getNoArticle());
			stmt.setString(2, retrait.getRue());
			stmt.setString(3, retrait.getCodePostal());
			stmt.setString(4, retrait.getVille());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Extraire les données de la base par l'id
	 * @param noArticle
	 * @return retrait
	 * @throws DALException
	 */
	public Retrait selectById(int noArticle) throws DALException {
		Connection cnx = JdbcConnexion.connect();
		Retrait retrait = null;
		try {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ID);
			stmt.setInt(1, noArticle);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if(rs.next()) {
				retrait = retraitResultSet(rs);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return retrait;
	}
	  public List<Retrait> selectAll() throws DALException {
	        return null;
	    }
	/**
	 * Mise a jour de la base de données
	 * @param retrait
	 * @throws DALException
	 */
	public void update(Retrait retrait) throws DALException{
		Connection cnx = JdbcConnexion.connect();
		try {
			PreparedStatement stmt = cnx.prepareStatement(SQLUPDATE);
			stmt.setString(1, retrait.getRue());
			stmt.setString(2, retrait.getCodePostal());
			stmt.setString(3, retrait.getVille());
			stmt.setInt(4, retrait.getNoArticle());
			stmt.executeUpdate();
			cnx.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * supprimer dans la base de données
	 * @param retrait
	 * @throws DALException
	 */
	public void delete(Retrait retrait) throws DALException{
		Connection cnx = JdbcConnexion.connect();
		try {
			PreparedStatement stmt = cnx.prepareStatement(SQLDELETE);
			stmt.setInt(1, retrait.getNoArticle());
            stmt.executeUpdate();
            cnx.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// instance à partir d’un ResultSet
	private Retrait retraitResultSet(ResultSet rs) throws SQLException {
        return new Retrait(
                rs.getInt("no_article"),
                rs.getString("rue"),
                rs.getString("code_postal"),
                rs.getString("ville")
                );
	}
}
