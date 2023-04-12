<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style/StyleSell.css">
    <link rel="stylesheet" href="style/main.css">
    
    <title>Créer une enchère - ENI-Enchères</title>
    
</head>
<%@ include file="JSPHeader.jspf" %>
<body>

<div class="main">
        <h2>Créer une enchère</h2>
        <form action="ServletSell" method="post">
            <label for="nomArticle">Nom de l'article :</label>
            <input type="text" id="nom" name="nomArticle" maxlength="30" required>
            <label for="noCategorie">Catégorie :</label>
            <select id="noCategorie" name="noCategorie">
                <option value="1">Informatique</option>
                <option value="2">Ameublement</option>
                <option value="3">Vêtement</option>
			  	<option value="3">Sport&Loisirs</option>
            <label for="description">Description :</label>
            <textarea id="description" name="description" maxlength="300" required></textarea>
            <label for="miseAPrix ">Prix de départ :</label>
            <input type="number" id="miseAPrix " name="miseAPrix" required>
            <label for="dateDebutEncheres">Date d'ouverture :</label>
            <input type="datetime-local" id="dateDebutEncheres" name="dateDebutEncheres" required>
            <label for="dateFinEncheres">Date de clôture :</label>
            <input type="datetime-local" id="dateFinEncheres" name="dateFinEncheres" required>
            <label for="modalites">Modalités du retrait :</label>
            <select id="modalites" name="modalites">
                <option value="adresse_utilisateur">Votre Adresse</option>
                <option value="point_relais">Point Relais</option>
                <option value="Envoie">Envoie à vos frais</option>
            </select>
             <!-- méthode pour upload -->
            <label for="photo">Télécharger une photo :</label>
            <input type="file" id="photo" name="photo">
            <br>
            <br>
            <!-- Boutton de création d'enchere -->
            <input type="submit" value="Créer l'enchère">
        </form>
    </div>
    <div class="footer">
        <p>ENI-Enchères &copy; 2023 - Aucun droit réservés.</p>
    </div>
</body>
</html>