<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
// Supprimer le cookie "pseudo"
Cookie cookie = new Cookie("pseudo", "");
cookie.setMaxAge(0); // Définir la durée de vie du cookie à 0 pour le supprimer
response.addCookie(cookie); // Envoyer le cookie au navigateur

// Rediriger l'utilisateur vers la page d'accueil
response.sendRedirect("ServletSell?action=home");
%>

</body>
</html>