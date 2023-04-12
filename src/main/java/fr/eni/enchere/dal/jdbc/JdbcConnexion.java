package fr.eni.enchere.dal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class JdbcConnexion {
	
	static Connection connect() {
		
		Connection connexion=null;
		String nomUtilisateur = "sa";
		String mdp = "Pa$$w0rd";
		String urlDB = "jdbc:sqlserver://localhost:1433;database=ENI_ENCHERES;tlsVersion=TLSv1.2";
		String urlSQL = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		try {
			
			Class.forName(urlSQL);
			
			DriverManager.registerDriver(new SQLServerDriver());
			
			// Connection via url, nom et mdp
			connexion= DriverManager.getConnection(urlDB, nomUtilisateur, mdp);
			System.out.println("WORKED");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
//			
		} catch (SQLException e) {
			System.out.println("Stack trace : ");
			e.printStackTrace();
			System.out.println("Get message :  ");
			e.getMessage();
		}
		return connexion;
	}
}

