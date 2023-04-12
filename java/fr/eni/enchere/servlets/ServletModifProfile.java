package fr.eni.enchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.BLLException;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DALException;

/**
 * Servlet implementation class ServletModifProfile
 */
@WebServlet("/modification-du-profil")
public class ServletModifProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("/WEB-INF/JSP/JSPModifProfile.jsp");
        rd.forward(request, response);  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur connexionOK = (Utilisateur) request.getSession().getAttribute("connexionOK");
		
		if(request.getParameter("enregistrer") !=null) {
			String pseudo = request.getParameter("pseudo");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("codePostal");
			String ville = request.getParameter("ville");
			String motDePasse = request.getParameter("motDePasse");
			
			 try {
				 	
				 	Utilisateur u = new Utilisateur(connexionOK.getNoUtilisateur(), pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, connexionOK.getCredit(), connexionOK.isAdministrateur());
				 	System.out.println("ServletModifProfil : tentative de modification de profil : " + u);
				 	UtilisateurManager.getInstance().updateUtilisateur(u);
				 } catch (DALException e) {
				 	e.printStackTrace();
				 } catch (BLLException e) {
				 	e.printStackTrace();
				 }
				 response.sendRedirect(request.getContextPath() + "/profil?noUtilisateur=" + connexionOK.getNoUtilisateur());
	}
	}
}
