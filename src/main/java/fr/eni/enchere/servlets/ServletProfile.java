package fr.eni.enchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DALException;

/**
 * Servlet implementation class ServletProfile
 */
@WebServlet("/profil")
public class ServletProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("noUtilisateur") == null) {
			System.out.print("no utilisateur manquant");
		} else {
		int noUtilisateur = Integer.parseInt(request.getParameter("noUtilisateur"));
		System.out.println(noUtilisateur);
		try {
			Utilisateur utilisateur = UtilisateurManager.getInstance().selectById(noUtilisateur);
			request.setAttribute("utilisateur", utilisateur);
		} catch (DALException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("/WEB-INF/JSP/JSPProfile.jsp");
        rd.forward(request, response);  
	}
}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
