package fr.eni.enchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.BLLException;
import fr.eni.enchere.bll.PasswordEncryption;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.dal.DALException;

@WebServlet("/inscription")
public class ServletCreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("/WEB-INF/JSP/JSPCreateAccount.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String motDePasse = request.getParameter("motDePasse");
		// Vérification du pseudo
	    if (!pseudo.matches("^[a-zA-Z0-9]*$")) {
	        // Si le pseudo ne contient pas que des caractères alphanumériques,
	        // on renvoie une erreur 400 Bad Request
	    	request.setAttribute("errorMessage", "Le pseudo ne doit contenir que des caractères alphanumériques.");
	    	doGet(request, response);
	    } else {
	    	
	    	//Cryptage du mot de passe
//			String motDePasseCrypte = null;
//			try {
//				motDePasseCrypte = PasswordEncryption.encryptPassword(motDePasse);
//			} catch (BLLException e) {
//				e.printStackTrace();
//			}
				String email = request.getParameter("email");
				String prenom = request.getParameter("prenom");
				String nom = request.getParameter("nom");
				String telephone = request.getParameter("telephone");
				String rue = request.getParameter("rue");
				String codePostal = request.getParameter("codePostal");
				String ville = request.getParameter("ville");
		
			try {
					UtilisateurManager.getInstance().creerUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse);
				}
				catch (DALException e) {
					e.printStackTrace();
				} catch (BLLException e) {
					e.printStackTrace();
				}
			response.sendRedirect(request.getContextPath() + "/connexion");
			}
	    }	
}