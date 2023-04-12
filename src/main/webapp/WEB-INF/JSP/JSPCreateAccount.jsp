<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription</title>
<link rel="stylesheet" type="text/css" href="style/main.css">
<link rel="stylesheet" type="text/css" href="style/registrationauthentification.css">
</head>
<body>
<%@ include file="JSPHeader.jspf" %>
<div class="mainRegistration">
	<div class="formWrapper">
		<p class="inscriptionTitle">Inscription</p>
			<form method="post" action="inscription">
			<% String errorMessage = (String) request.getAttribute("errorMessage");
				if (errorMessage != null) { %>
				<div class="errorMessage"><span class="textError"><%= errorMessage %></span></div>
				<% } %>
				<div>
					<label for="pseudo">Pseudo:</label>
					<input type="text" id="pseudo" name="pseudo" required>
				</div>	
				<div>
					<label for="motDePasse">Mot de passe :</label>
					<input type="password" id="motDePasse" name="motDePasse" required>
				</div>
				<div>
					<label for="email">Adresse e-mail :</label>
					<input type="text" id="email" name="email" required>
				</div>
				<div>
					<label for="pseudo">Prénom:</label>
					<input type="text" id="prenom" name="prenom" required>
				</div>
				<div>
					<label for="pseudo">Nom:</label>
					<input type="text" id="nom" name="nom" required>
				</div>
				<div>
					<label for="pseudo">Téléphone:</label>
					<input type="text" id="telephone" name="telephone" required>
				</div>
				<div>
					<label for="pseudo">Adresse:</label>
					<input type="text" id="rue" name="rue" required>
				</div>
				<div>
					<label for="pseudo">Code Postal:</label>
					<input type="text" id="codePostal" name="codePostal" required>
				</div>
				<div>
					<label for="pseudo">Ville:</label>
					<input type="text" id="ville" name="ville" required>
				</div>
				<div>
					<input class="buttonForm" type="submit" value="S'inscrire">
				</div>
			</form>
		</div>
	</div>	
</body>
</html>