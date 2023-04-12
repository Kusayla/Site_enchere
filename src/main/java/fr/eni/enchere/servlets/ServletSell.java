package fr.eni.enchere.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bll.BLLException;
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.dal.DALException;


@WebServlet("/ServletSell")
public class ServletSell extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ArticleVenduManager manager;
    
    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    public ServletSell() {
        super();
        manager = new ArticleVenduManager();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/JSP/JSPSell.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 String nom_article = request.getParameter("nomArticle");
    	    String description = request.getParameter("description");
    	    String dateDebutEncheresStr = request.getParameter("dateDebutEncheres");
    	    String dateFinEncheresStr = request.getParameter("dateFinEncheres");
    	    String miseAPrixStr = request.getParameter("miseAPrix");
    	    String prixDeVenteStr = request.getParameter("prixDeVente");
    	    String noUtilisateurStr = getCookieValue(request, "noUtilisateur");
    	    String noCategorieStr = request.getParameter("noCategorie");
    	    String etat_vente = "EC";
    	    final EnchereManager enchereManager;

    	    int prix_initial = miseAPrixStr != null ? Integer.parseInt(miseAPrixStr) : 0;
    	    int prix_vente = prixDeVenteStr != null ? Integer.parseInt(prixDeVenteStr) : 0;
    	    int no_utilisateur = noUtilisateurStr != null ? Integer.parseInt(noUtilisateurStr) : 0;
    	    int no_categorie = noCategorieStr != null ? Integer.parseInt(noCategorieStr) : 0;
    	    

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        java.util.Date dateDebutEncheres = null;
        java.util.Date dateFinEncheres = null;
        
        System.out.println("dateDebutEncheresStr: " + dateDebutEncheresStr);
        System.out.println("dateFinEncheresStr: " + dateFinEncheresStr);
        

        try {
            if (dateDebutEncheresStr != null && dateFinEncheresStr != null) {
                dateDebutEncheres = sdf.parse(dateDebutEncheresStr);
                dateFinEncheres = sdf.parse(dateFinEncheresStr);
            } else {
                throw new ParseException("Les dates fournies sont invalides.", 0);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("erreur", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/erreur.jsp").forward(request, response);
            return; // Ajoutez cette ligne pour arrêter l'exécution de la méthode en cas d'erreur
        }

        // Utilisez les objets dateDebutEncheres et dateFinEncheres seulement s'ils ne sont pas null
        if (dateDebutEncheres != null && dateFinEncheres != null) {
            // Votre code existant pour travailler avec les dates
        }

        // Convertir java.util.Date en java.sql.Date avant de créer un nouvel ArticleVendu

        Timestamp date_debut_encheres = new Timestamp(dateDebutEncheres.getTime());
        Timestamp date_fin_encheres = new Timestamp(dateFinEncheres.getTime());

        
        
        System.out.println("dateDebutEncheresSql: " + date_debut_encheres);
        System.out.println("dateFinEncheresSql: " + date_fin_encheres);
        // Créez l'objet ArticleVendu
        
        try {
            // Insérez l'objet ArticleVendu dans la base de données
            ArticleVendu article = manager.creerArticleVendu(nom_article, description, date_debut_encheres, date_fin_encheres,
            		prix_initial, prix_vente, no_utilisateur, no_categorie, etat_vente);
            System.out.println("servletSell" + article);
            
//            Thread.sleep(3000);
            int no_article = article.getNoArticle();
            EnchereManager.creationEnchere(date_fin_encheres, prix_initial, no_article, no_utilisateur);

            request.setAttribute("article", article);
            //request.getRequestDispatcher("/WEB-INF/JSP/JSPHome.jsp").forward(request, response);
            response.sendRedirect(request.getContextPath()+"/");
        } catch (BLLException | DALException e) {
            e.printStackTrace();
            request.setAttribute("erreur", e.getMessage());
//            request.getRequestDispatcher("/WEB-INF/JSP/erreur.jsp").forward(request, response);
//        } catch (InterruptedException e) {
//			e.printStackTrace();
		}
    }
}    