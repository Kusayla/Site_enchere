<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modification du profil</title>
<link rel="stylesheet" type="text/css" href="style/main.css">
<link rel="stylesheet" type="text/css" href="style/modifprofile.css">
</head>
<body>
<%@ include file="JSPHeader.jspf" %>

<div class="modifprofilMain">
<h2>Modifications à apporter au profil :</h2>

<form action="modification-du-profil" method="post">
	<div class="catWrapper">
		<h3>Identifiants :</h3>
		<div>
			<p>Pseudo :</p>
			<input type="text" name="pseudo" value="${connexionOK.getPseudo()}">
		</div>
		<div>
			<p>Adresse mail :</p>
			<input type="text" name="email" value="${connexionOK.getEmail()}">
		</div>
		<div>
			<p>Mot de passe :</p>
			<input input type="password" name="motDePasse" required>
		</div>
	</div>
	<div class="catWrapper">
		<h3>Identité :</h3>
		<div class="itemsWrapper">
			<p>Prénom :</p>
			<input type="text" name="prenom" value="${connexionOK.getPrenom()}">
		</div>
		<div class="itemsWrapper">
			<p>Nom :</p>
			<input type="text" name="nom" value="${connexionOK.getNom()}">
		</div>
		<div class="itemsWrapper">
			<p>Téléphone :</p>
			<input type="text" name="telephone" value="${connexionOK.getTelephone()}">
		</div>
	</div>
	<div class="catWrapper">
		<h3>Adresse :</h3>
		<div class="itemsWrapper">
			<p>Rue :</p>
			<input type="text" name="rue" value="${connexionOK.getRue()}">
		</div>
		<div class="itemsWrapper">
			<p>Code Postal :</p>
			<input type="text" name="codePostal" value="${connexionOK.getCodePostal()}">
		</div>
		<div class="itemsWrapper">
			<p>Ville :</p>
			<input type="text" name="ville" value="${connexionOK.getVille()}">
		</div>
	</div>
		 <button name="enregistrer" value="enregistrer" type="submit">Enregistrer les modifications</button>
</form>
</div>

</body>
</html>
