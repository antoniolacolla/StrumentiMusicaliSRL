<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container form-container">
        <h2>Accedi al tuo Account</h2>
        <c:if test="${not empty errore}"><p class="error-message">${errore}</p></c:if>
        <form action="${pageContext.request.contextPath}/login" method="POST">
            <div class="form-group"><label for="email">Email:</label><input type="email" id="email" name="email" required></div>
            <div class="form-group"><label for="password">Password:</label><input type="password" id="password" name="password" required></div>
            <button type="submit" class="btn btn-primary" style="width: 100%;">Accedi</button>
        </form>
        <p style="margin-top: 20px;">Non hai un account? <a href="${pageContext.request.contextPath}/registrazione">Registrati ora</a></p>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>