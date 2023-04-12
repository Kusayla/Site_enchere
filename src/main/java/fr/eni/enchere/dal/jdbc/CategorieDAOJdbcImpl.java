package fr.eni.enchere.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.CodeErreurDAL;
import fr.eni.enchere.dal.DALException;
import fr.eni.enchere.dal.dao.DAOCategorie;

public class CategorieDAOJdbcImpl implements DAOCategorie{
	private final String SQLINSERT = "INSERT INTO CATEGORIES (libelle) VALUES (?)";
	private final String SELECT_BY_ID = "SELECT * FROM CATEGORIES WHERE no_categorie = ?";
	private final String SELECT_ALL = "SELECT * FROM CATEGORIES";

	
	/**
	 * Extraire les données de la base par l'id
	 * 
	 * @param id
	 * @return
	 * @throws DALException
	 */
	public Categorie selectById(int id) throws DALException {
		Connection cnx = JdbcConnexion.connect();
		Categorie categorie = null;
		try {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ID);
			stmt.setInt(1, id);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorie;
	}

	public List<Categorie> selectAll() throws DALException {
		Connection cnx = JdbcConnexion.connect();
		List<Categorie> categories = new ArrayList<>();
		try {
			Statement stmt = cnx.createStatement();
			stmt.execute(SELECT_ALL);
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				categories.add(new Categorie(rs.getInt("no_categorie"), rs.getString("libelle")));
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}
	/**
	 * Insérer des données dans la base
	 * 
	 * @param categorie
	 * @throws DALException
	 */
	public void insert(Categorie categorie) throws DALException {
		Connection cnx = JdbcConnexion.connect();
		try {
			PreparedStatement stmt = cnx.prepareStatement(SQLINSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, categorie.getLibelle());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				categorie.setNoCategorie(rs.getInt(1));
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	   @Override
	    public void update(Categorie categorie) throws DALException {

	    }

	    @Override
	    public void delete(Categorie categorie) throws DALException {

	    }
	    public boolean checkForUniqueCategorieLibelle(String libelleToCheck) throws DALException {
	        Connection cnx = JdbcConnexion.connect();
	        boolean isUnique = true;
	        try {
	            String CHECK_IF_LIBELLE_IS_UNIQUE = "SELECT * FROM CATEGORIES WHERE libelle LIKE ?";
	            PreparedStatement stmt = cnx.prepareStatement(CHECK_IF_LIBELLE_IS_UNIQUE);
	            stmt.setString(1, libelleToCheck);
	            stmt.execute();
	            ResultSet rs = stmt.getResultSet();
	            if (rs.next()) {
	                isUnique = false;
	            }
	            cnx.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            DALException dalException = new DALException();
	            dalException.addError(CodeErreurDAL.ERREUR_SQL_SELECT);
	            throw dalException;
	        }
	        return isUnique;
	    }
}
