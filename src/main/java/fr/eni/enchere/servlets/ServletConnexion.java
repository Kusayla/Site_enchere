package fr.eni.enchere.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.BLLException;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DALException;


/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/connexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/JSPConnexion.jsp");
        rd.forward(request, response);  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String identifiant = request.getParameter("identifiant");
		String motDePasse = request.getParameter("motDePasse");
		
		try {
		    Utilisateur utilisateur = UtilisateurManager.getInstance().connexionUtilisateur(identifiant, motDePasse);
		    if(utilisateur != null) {
		    session.setAttribute("connexionOK",utilisateur);
		    	try {
		    		// On récupère le numéro utilisateur et on le stock dans un cookie
		    		Cookie cookieNoUtilisateur = new Cookie("noUtilisateur", String.valueOf(utilisateur.getNoUtilisateur()));
		    		// On va créer une fingerprint qui va comporter dans son header le user agent de l'utilisateur (OS, navigateur, moteur de recherche)
		    		// Toujours dans le header on va enregistrer l'encodage compris par le client et la langue utilisée)
		    		// Ensuite on va créer une chaine de caractère avec toutes ces infos qu'on va hash 
		     		String fingerprint = UtilisateurManager.getInstance().getCookie(request, utilisateur);
		     		System.out.println(fingerprint);
		 			Cookie cookieFingerprint = new Cookie("fingerprint", fingerprint);
		 			cookieNoUtilisateur.setMaxAge(3600*24*30);
		 			cookieFingerprint.setMaxAge(3600*24*30);
		 			
		 			//On va stocker le no d'utilisateur et la fingerprint dans la réponse renvoyée 
		 			response.addCookie(cookieNoUtilisateur);
		 			response.addCookie(cookieFingerprint);
		    	} catch (NoSuchAlgorithmException e) {
		    		e.printStackTrace();
		    	}
		    	response.sendRedirect(request.getContextPath()+"/"); 
		    } else {
		        // L'utilisateur n'a pas été trouvé dans la base de données, renvoie une erreur.
		    	System.out.print("utilisateur non trouvé");
		        request.setAttribute("errorMessage", "L'identifiant ou le mot de passe est incorrect.");
		        doGet(request, response);
		    }
		} catch (BLLException e) {
			request.setAttribute("erreur", e.getListErrorCodes().get(0));
			doGet(request, response);
		} catch (DALException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}	
	}
}	
