package fr.eni.enchere.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bll.BLLException;
import fr.eni.enchere.bll.PasswordEncryption;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.CodeErreurDAL;
import fr.eni.enchere.dal.ConnectionProvider;
import fr.eni.enchere.dal.DALException;
import fr.eni.enchere.dal.dao.DAOUtilisateur;

/**
 * insérer une instance dans la base de donnée et recuperer l'id généré par la
 * base de donnée
 * 
 * @param  utilisateur Utilisateur
 * @throws DALException
 */
public class UtilisateurDAOJdbcImpl implements DAOUtilisateur {
	private final String SQLINSERT = "INSERT INTO UTILISATEURS " 
			+ "(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) " 
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String SQLUPDATE = "UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, "
			+ "rue=?, code_postal=?, ville=?, mot_de_passe=?, credit=?, administrateur=? where no_utilisateur=?";
	private final String SQLDELETE = "DELETE FROM UTILISATEURS WHERE no_utilisateur=?"
			+ "DELETE FROM UTILISATEURS WHERE pseudo=?";
	private final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur= ?";
	/**
	 * Insérer des données dans la base
	 * @param utilisateur
	 * @throws DALException
	 */
	
			public void insert(Utilisateur utilisateur) throws DALException {
		Connection cnx = JdbcConnexion.connect();
		PreparedStatement stmt;
		try {
			cnx.setAutoCommit(false);
			stmt=cnx.prepareStatement(SQLINSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			utilisateurPreparedStatement(utilisateur, stmt);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
                utilisateur.setNoUtilisateur(rs.getInt(1));
		}
			cnx.commit();
			cnx.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERREUR INSERTION UTILISATEUR");
		}	
	}

/**
 * Extraire les données de la base par l'id
 * @param id int
 * @return utilisateur
 * @throws SQLException
 */
public Utilisateur selectById(int noUtilisateur) throws DALException {
	Connection cnx = JdbcConnexion.connect();
	Utilisateur utilisateur = null;
	try {
		PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ID);
		stmt.setInt(1, noUtilisateur);
		stmt.execute();
		ResultSet rs = stmt.getResultSet();
		if(rs.next()) {
			utilisateur = utilisateurResultSet(rs);
		}
		cnx.commit();
		cnx.close();
	}catch (SQLException e) {
		e.printStackTrace();
		System.out.println("ERREUR SELECTION");
	}
	return utilisateur;
}
/**
 * 
 * Selectionner tout les utilisateurs de la base de données
 * @return Arraylist
 */
public List<Utilisateur> selectAll() throws DALException {
	Connection cnx = JdbcConnexion.connect();
	List<Utilisateur> liste = new ArrayList<>();
	try {
		Statement stmt = cnx.createStatement();
		String SELECT_ALL = "SELECT * FROM UTILISATEURS";
		stmt.execute(SELECT_ALL);
		ResultSet rs = stmt.getResultSet();
		while (rs.next()) {
			liste.add(utilisateurResultSet(rs));
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
 * @param u Utilisateur
 */
public void update(Utilisateur u) throws DALException {
	Connection cnx = JdbcConnexion.connect();
	PreparedStatement stmt = null;
	try {
		cnx.setAutoCommit(false);
		System.out.println("Lancement d'update utilisateur : " + u);
		stmt = cnx.prepareStatement(SQLUPDATE);
		stmt.setString(1, u.getPseudo());
		stmt.setString(2, u.getNom());
		stmt.setString(3, u.getPrenom());
		stmt.setString(4, u.getEmail());
		stmt.setString(5, u.getTelephone());
		stmt.setString(6, u.getRue());
		stmt.setString(7, u.getCodePostal());
		stmt.setString(8, u.getVille());
		stmt.setString(9, u.getMotDePasse());
		stmt.setInt(10, u.getCredit());
		stmt.setBoolean(11, u.isAdministrateur());
		stmt.setInt(12, u.getNoUtilisateur());
		System.out.println("UtilisateurDAOJdbcImpl > Update > stmt : " + stmt.toString());
		stmt.executeUpdate();
		cnx.commit();
	} catch (SQLException e) {
		e.printStackTrace();
		if (cnx != null) {
			try {
				cnx.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		throw new DALException();
	} finally {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (cnx != null) {
			try {
				cnx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
/**
 * supprimer dans la base de données
 * @param u
 * @throws DALException
 */
public void delete(Utilisateur u) throws DALException{
	Connection cnx = JdbcConnexion.connect();
	try {
		PreparedStatement stmt = cnx.prepareStatement(SQLDELETE);
		stmt.setInt(1, u.getNoUtilisateur());
		stmt.setString(2, u.getPseudo());
		stmt.executeUpdate();
		cnx.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

	//instruction utilisée par les demandes INSERT et UPDATE
	private void utilisateurPreparedStatement (Utilisateur u, PreparedStatement stmt) throws SQLException{
		stmt.setInt(1, u.getNoUtilisateur());
		stmt.setString(2, u.getPseudo());
		stmt.setString(3, u.getNom());
		stmt.setString(4, u.getPrenom());
		stmt.setString(5, u.getEmail());
		stmt.setString(6, u.getTelephone());
		stmt.setString(7, u.getRue());
		stmt.setString(8, u.getCodePostal());
		stmt.setString(9, u.getVille());
		stmt.setString(10, u.getMotDePasse());
		stmt.setInt(11, u.getCredit());
		stmt.setBoolean(12, u.isAdministrateur());
	}
	
	// instance à partir d’un ResultSet
	 private Utilisateur utilisateurResultSet(ResultSet rs) throws SQLException {
	        return new Utilisateur(
	                rs.getInt("no_utilisateur"),
	                rs.getString("pseudo"),
	                rs.getString("nom"),
	                rs.getString("prenom"),
	                rs.getString("email"),
	                rs.getString("telephone"),
	                rs.getString("rue"),
	                rs.getString("code_postal"),
	                rs.getString("ville"),
	                rs.getString("mot_de_passe"),
	                rs.getInt("credit"),
	                rs.getBoolean("administrateur")
	        );
	    }
	
	
	//verification de l'unicité du mail et du pseudo
    public boolean checkForUniquePseudoAndMail(String pseudo, String mail) throws DALException {
        Connection cnx = JdbcConnexion.connect();
        boolean isUnique = true;
        try {
            String CHECK_IF_PSEUDO_AND_MAIL_ALREADY_EXIST =
                    "SELECT * FROM UTILISATEURS " +
                    "WHERE pseudo LIKE ? OR email LIKE ?;";
            PreparedStatement stmt = cnx.prepareStatement(CHECK_IF_PSEUDO_AND_MAIL_ALREADY_EXIST);
            stmt.setString(1, pseudo);
            stmt.setString(2, mail); // Mail ?
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
    
//  verification de l'unicité du pseudo
    public boolean checkForUniquePseudo(String pseudo) throws DALException {
        Connection cnx = JdbcConnexion.connect();
        boolean isUnique = true;
        try {
            String CHECK_IF_PSEUDO_ALREADY_EXIST =
                    "SELECT * FROM UTILISATEURS " +
                            "WHERE pseudo LIKE ?;";
            PreparedStatement stmt = cnx.prepareStatement(CHECK_IF_PSEUDO_ALREADY_EXIST);
            stmt.setString(1, pseudo);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                isUnique = false;
            }
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(CodeErreurDAL.ERREUR_SQL_PSEUDO);
            throw dalException;
        }
        return isUnique;
    }
    
    //verification de l'unicité du mail
    public boolean checkForUniqueMail(String mail) throws DALException {
        Connection cnx = JdbcConnexion.connect();
        boolean isUnique = true;
        try {
            String CHECK_IF_MAIL_ALREADY_EXIST =
                    "SELECT * FROM UTILISATEURS " +
                            "WHERE email LIKE ?;";
            PreparedStatement stmt = cnx.prepareStatement(CHECK_IF_MAIL_ALREADY_EXIST);
            stmt.setString(1, mail);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                isUnique = false;
            }
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(CodeErreurDAL.ERREUR_SQL_MAIL);
            throw dalException;
        }
        return isUnique;
    }
    
    //Selection d'utilisateur par email
    @Override
    public Utilisateur selectUtilisateurByEmail(String email) throws DALException {
    	Connection cnx = JdbcConnexion.connect();
        Utilisateur utilisateur = null;
        try {
            String SELECT_BY_EMAIL = "SELECT * FROM UTILISATEURS WHERE email = ?";
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_EMAIL);
            stmt.setString(1, email);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                utilisateur = utilisateurResultSet(rs);
            }
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(CodeErreurDAL.ERREUR_SQL_SELECT);
            throw dalException;
        }
        return utilisateur;
}
    
    //Selection d'utilisateur par son pseudo
	@Override
	public Utilisateur selectUtilisateurByPseudo(String pseudo) throws DALException {
		Connection cnx = JdbcConnexion.connect();
        Utilisateur utilisateur = null;
        try {
            String SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = ?";
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_PSEUDO);
            stmt.setString(1, pseudo);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                utilisateur = utilisateurResultSet(rs);
            }
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(CodeErreurDAL.ERREUR_SQL_SELECT);
            throw dalException;
        }
        return utilisateur;
}
	
	public boolean verifMDP(Utilisateur utilisateur, String motDePasse) throws DALException, BLLException{
		Connection cnx = JdbcConnexion.connect();
		String motDePasseCrypte = PasswordEncryption.encryptPassword(motDePasse);
        try {
            String SELECT_MDP_BY_ID = "SELECT mot_de_passe FROM UTILISATEURS WHERE no_utilisateur = ?";
            PreparedStatement stmt = cnx.prepareStatement(SELECT_MDP_BY_ID);
            stmt.setInt(1, utilisateur.getNoUtilisateur());
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                String mdpDb = rs.getString("mot_de_passe");
                if (mdpDb.equals(motDePasseCrypte)) {
                    return true;
                }
            }
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(CodeErreurDAL.ERREUR_SQL_SELECT);
            throw dalException;
        }
        return false;
	}		
}




