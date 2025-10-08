<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dettaglio Ordine #${ordine.id}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container">
        <c:if test="${not empty ordine}">
            <h1>Dettaglio Ordine #${ordine.id}</h1>
            <div class="summary">
                <p><strong>Stato:</strong> <span class="status status-${ordine.stato.toLowerCase().replace(' ', '-')}">${ordine.stato}</span></p>
                <p><strong>Totale:</strong> ${ordine.totale} €</p>
            </div>
            <h2>Prodotti Acquistati</h2>
            <table>
                <thead>
                <tr><th>Prodotto</th><th>Quantità</th><th>Prezzo Unitario</th><th>Subtotale</th></tr>
                </thead>
                <tbody>
                <c:forEach items="${ordine.prodottiAcquistati}" var="riga">
                    <tr>
                        <td><strong>${riga.key.marca}</strong> ${riga.key.nome}</td>
                        <td>${riga.value}</td>
                        <td>${riga.key.prezzo} €</td>
                        <td>${riga.key.prezzo * riga.value} €</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <div style="margin-top: 30px;"><a href="${pageContext.request.contextPath}/storico-ordini" class="btn btn-primary">‹ Torna allo Storico</a></div>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>