<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrazione</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container form-container">
        <h2>Crea un Nuovo Account</h2>
        <c:if test="${not empty errore}"><p class="error-message">${errore}</p></c:if>
        <form action="${pageContext.request.contextPath}/registrazione" method="POST">
            <div class="form-group"><label for="nome">Nome:</label><input type="text" id="nome" name="nome" required></div>
            <div class="form-group"><label for="cognome">Cognome:</label><input type="text" id="cognome" name="cognome" required></div>
            <div class="form-group"><label for="email">Email:</label><input type="email" id="email" name="email" required></div>
            <div class="form-group"><label for="password">Password:</label><input type="password" id="password" name="password" required></div>
            <button type="submit" class="btn btn-primary" style="width: 100%;">Registrati</button>
        </form>
        <p style="margin-top: 20px;">Hai già un account? <a href="${pageContext.request.contextPath}/login">Accedi</a></p>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>