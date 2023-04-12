<%@ page import="fr.eni.enchere.bo.Utilisateur" %>
<%@ page import="fr.eni.enchere.bll.UtilisateurManager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profil utilisateur</title>
<link rel="stylesheet" type="text/css" href="style/main.css">
<link rel="stylesheet" type="text/css" href="style/profile.css">
</head>
<body>
<%@ include file="JSPHeader.jspf" %>
	<div class="mainProfile">
		<h2>Profil de l'utilisateur ${ utilisateur.getPseudo() }</h2>
			<div class="catWrapper">
			<div class="itemsWrapper">
				<h3>Identité</h3>
				<p><span class="label">Prénom :</span> ${ utilisateur.getPrenom() }</p>
				<p><span class="label">Nom :</span> ${ utilisateur.getNom() }</p>
			</div>
			<div class="itemsWrapper">
				<h3>Moyens de contact</h3>
				<p><span class="label">Email :</span> ${ utilisateur.getEmail() }</p>
				<p><span class="label">Numéro de téléphone :</span> ${ utilisateur.getTelephone() }</p>
			</div>
			<div class="itemsWrapper">
				<h3>Adresse</h3>
				<p><span class="label">Rue :</span> ${ utilisateur.getRue() }</p>
				<p><span class="label">Code Postal :</span> ${ utilisateur.getCodePostal() }</p>
				<p><span class="label">Ville :</span> ${ utilisateur.getVille() }</p>
			</div>		
		</div>
		<div>
			 <c:if test="${ connexionOK !=null && connexionOK.getNoUtilisateur() == utilisateur.getNoUtilisateur() }">
			 	<a href="modification-du-profil"><button type="button" > Modifier mon profil </button></a>
			 </c:if>
		</div>
		
	</div>
	
</body>
</html>