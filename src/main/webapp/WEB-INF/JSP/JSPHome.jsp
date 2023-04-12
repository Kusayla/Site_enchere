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
<link rel="stylesheet" href="style/home.css">
<title>accueil</title>
</head>
<body>
<c:out value="${ pseudo }"/>

<%@ include file="JSPHeader.jspf" %>
<h1>Page d'accueil</h1>
<table>
        <thead>
            <tr>
                
                <th>Enchere</th>
                <th>Prix</th>
                <th>Vendeur</th>
                <th>Date de fin</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${encheresDetailsList}" var="enchereDetails">
                <tr>
                	<td>
                		<c:if test="${connexionOK != null}"> 
                			<a href="${pageContext.request.contextPath}/DetailsEnchere?id=${enchereDetails.Id}">${enchereDetails.NomArticle}</a>
                		</c:if>
                		<c:if test="${connexionOK == null}"> 
                			<p>${enchereDetails.NomArticle}</p>
                		</c:if>
                	</td>                   
                	<td>${enchereDetails.Prix}</td>
                    <td>${enchereDetails.Vendeur}</td>
                    <td>${enchereDetails.DateDeFin}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
