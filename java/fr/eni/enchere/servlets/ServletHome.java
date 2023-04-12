package fr.eni.enchere.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bll.BLLException;
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.dal.DALException;

/**
 * Servlet implementation class ServletHome
 */
@WebServlet("")
public class ServletHome extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		// récuperation de la liste des encheres
//		
		try {
	        List<Enchere> encheres = EnchereManager.getAllEncheres();
	        List<Map<String, Object>> encheresDetailsList = new ArrayList<>();
	        for (Enchere enchere : encheres) {
	        	Map<String, Object> enchereDetails = EnchereManager.afficheEnchereDetails(enchere);
	            encheresDetailsList.add(enchereDetails);
	        	
	        }
	        System.out.println("test servlet : " + encheresDetailsList);
	        request.setAttribute("encheresDetailsList", encheresDetailsList);
	        request.getRequestDispatcher("/WEB-INF/JSP/JSPHome.jsp").forward(request, response);
	    } catch (DALException | BLLException e) {
	        // Gérer les exceptions
	    }
		}
}
