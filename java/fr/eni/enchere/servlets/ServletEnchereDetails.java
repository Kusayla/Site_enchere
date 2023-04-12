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
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DALException;

/**
 * Servlet implementation class ServletHome
 */
@WebServlet("/DetailsEnchere")
public class ServletEnchereDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// récuperation du détail de l'enchere
		String id = request.getParameter("id");
	    if(id != null) {
	        try {
	        	System.out.println("servletEnchere details ID : " + id); // pour test
	            Enchere enchere = EnchereManager.getSpecificEnchere(id);
	            List<Map<String, Object>> encheresDetailsList = new ArrayList<>();
	            Map<String, Object> enchereDetails = EnchereManager.afficheEnchereDetails(enchere);
	            encheresDetailsList.add(enchereDetails);
	            request.setAttribute("enchereDetails", encheresDetailsList);
	            request.getRequestDispatcher("/WEB-INF/JSP/JSPEnchereDetails.jsp").forward(request, response);
	        } catch (DALException | BLLException e) {
	            // Gérer les exceptions
	        }
	    }
		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur connexionOK = (Utilisateur) request.getSession().getAttribute("connexionOK");
		String idEnchere = request.getParameter("idEnchere");
		
	    if(idEnchere != null) {
	        	try {
					EnchereManager.encherir(idEnchere, 1, connexionOK);
				} catch (DALException e) {
					e.printStackTrace();
				}
	        }
	    response.sendRedirect(request.getContextPath());
	        }
	    }

