package fr.eni.enchere.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class deconnexion
 */
@WebServlet("/deconnexion")
public class deconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deconnexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		System.out.println("Demande de déconnexion");
		session.removeAttribute("utilisateurConnecte");
		System.out.println("  --> Suppression des informations de l'utilisateur précédemment déconnecté.");
		session.invalidate();
		System.out.println("  --> Invalidation de la session courante.");
		Cookie fingerprint = new Cookie("fingerprint", "");
		Cookie noUtilisateur = new Cookie("noUtilisateur", "");
		fingerprint.setMaxAge(0);
		noUtilisateur.setMaxAge(0);
		response.addCookie(fingerprint);
		System.out.println("  --> Ajout d'une consigne de suppression du cookie de connexion persistante fingerprint s'il avait été créé.");
		response.addCookie(noUtilisateur);
		System.out.println("  --> Ajout d'une consigne de suppression du cookie de connexion persistante noUtilisateur s'il avait été créé.");
		response.sendRedirect(request.getContextPath()+"/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
