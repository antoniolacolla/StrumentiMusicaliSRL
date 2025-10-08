<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Acquisto Confermato</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container" style="text-align: center;">
        <h1>Grazie per il tuo acquisto! ✅</h1>
        <p>Il tuo ordine è stato elaborato con successo e verrà spedito il prima possibile.</p>
        <c:if test="${not empty ordineConfermato}">
            <div class="summary">
                <h3>Riepilogo Ordine #${ordineConfermato.id}</h3>
                <p><strong>Data:</strong> ${ordineConfermato.data}</p>
                <p><strong>Spedito a:</strong> ${ordineConfermato.indirizzo}, ${ordineConfermato.citta}</p>
            </div>
        </c:if>
        <a href="${pageContext.request.contextPath}/" class="btn btn-primary" style="margin-top: 30px;">Torna alla Home</a>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>