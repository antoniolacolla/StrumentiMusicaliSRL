<%@ page import="com.Strumentimusicali.model.Carrello" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
    Carrello carrello = (Carrello) session.getAttribute("carrello");
    if (carrello == null) {
        carrello = new Carrello();
        session.setAttribute("carrello", carrello);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Il Tuo Carrello</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container">
        <h1>Il Tuo Carrello</h1>
        <c:choose>
            <%-- CASO 1: IL CARRELLO È VUOTO --%>
            <c:when test="${empty carrello.getProdottiConQuantita()}">
                <div style="text-align: center; padding: 40px 0;">
                    <p style="font-size: 1.2em;">Il tuo carrello è vuoto.</p>
                    <div style="display: flex; justify-content: center; gap: 15px; margin-top: 20px;">
                        <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-primary">Vai al Catalogo</a>
                        <a href="${pageContext.request.contextPath}/" class="btn btn-success">Torna alla Home</a>
                    </div>
                </div>
            </c:when>

            <%-- CASO 2: IL CARRELLO CONTIENE PRODOTTI --%>
            <c:otherwise>
                <table>
                    <thead>
                    <tr><th>Prodotto</th><th>Quantità</th><th>Prezzo Unitario</th><th>Subtotale</th><th></th></tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${carrello.getProdottiConQuantita()}" var="entry">
                        <tr>
                            <td><strong>${entry.key.marca}</strong> ${entry.key.nome}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/carrello" method="POST" style="display: flex; align-items: center; gap: 10px;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="prodottoId" value="${entry.key.id}">
                                    <input type="number" name="quantita" value="${entry.value}" min="1" max="${entry.key.quantitaDisponibile}" style="width: 70px; padding: 8px;">
                                    <button type="submit" class="btn btn-primary" style="padding: 8px 12px;">Aggiorna</button>
                                </form>
                            </td>
                            <td>${entry.key.prezzo} €</td>
                            <td>${entry.key.prezzo * entry.value} €</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/carrello" method="POST" style="margin: 0;">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="prodottoId" value="${entry.key.id}">
                                    <button type="submit" class="btn btn-danger">Rimuovi</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr class="total-row"><td colspan="3">Totale</td><td colspan="2">${carrello.getTotale()} €</td></tr>
                    </tfoot>
                </table>

                <%-- ECCO I PULSANTI CHE APPAIONO QUANDO IL CARRELLO È PIENO --%>
                <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 30px;">
                    <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-secondary">‹ Continua lo Shopping</a>
                    <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success">Procedi al Checkout ›</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>