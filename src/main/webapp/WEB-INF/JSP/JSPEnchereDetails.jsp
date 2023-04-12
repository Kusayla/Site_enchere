<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800&display=swap" rel="stylesheet">
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="style/detail.css">
<title>accueil</title>
</head>
<body>
<c:out value="${ pseudo }"/>

<%@ include file="JSPHeader.jspf" %>

<div class="mainDetail"><c:forEach items="${enchereDetails}" var="enchereDetails">
	<h1>Détail de l'enchere : ${enchereDetails.NomArticle}</h1>
<table>
        <thead>
            <tr>
                <th>Vendeur</th>
                <th>Date de fin</th>
                <th>Prix</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
            <%-- <c:forEach items="${enchereDetails}" var="enchereDetails"> --%>
                <tr>
               
                    <td>${enchereDetails.Vendeur}</td>
                    <td>${enchereDetails.DateDeFin}</td>
                    <td>${enchereDetails.Prix}</td>
                    <td>${enchereDetails.Description}</td>
                </tr>
            
        </tbody>
    </table>
    <div>
    <form method="post" action="DetailsEnchere">
    <input type="hidden" name="idEnchere" value="${enchereDetails.Id}">
    <input type="submit" value="Enchérir de 1" class="btn enchere-btn">
    </form>
</c:forEach>
</div>
	<p> <a href="${pageContext.request.contextPath}/">Retour</a></p>
</div>

</body>
</html>
