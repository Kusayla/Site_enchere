<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion</title>

<link rel="stylesheet" type="text/css" href="style/registrationauthentification.css">
<link rel="stylesheet" type="text/css" href="style/main.css">
</head>
<body>


<%@ include file="JSPHeader.jspf" %>
	<div class="mainRegistration">
		<div class="formWrapper">
			<p>Connexion</p>
			<form method="post" action="connexion">
				<label for="pseudo">Pseudo:</label>
				<input type="text" id=identifiant name="identifiant" required>
				<label for="motDePasse">Mot de passe :</label>
				<input type="password" id="motDePasse" name="motDePasse" required>
				<% String errorMessage = (String) request.getAttribute("errorMessage");
				if (errorMessage != null) { %>
				<div class="errorMessage"><span class="textError"><%= errorMessage %></span></div>
				<% } %>
				<a href="">Mot de passe oublié</a>
				<label for="seSouvenir">Se souvenir de moi :</label>
				<input type="checkbox" id="seSouvenir" name="seSouvenir">
				<input class="buttonForm" type="submit" value="Se connecter"><br>
				
			</form>
		</div>
	</div>		
</body>
</html>