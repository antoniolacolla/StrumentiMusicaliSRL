<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Storico Ordini</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>

<main>
    <div class="container">
        <h1>Il Tuo Storico Ordini</h1>

        <c:choose>
            <%-- Se la lista degli ordini è vuota, mostra un messaggio --%>
            <c:when test="${empty listaOrdini}">
                <div style="text-align: center; padding: 40px 0;">
                    <p>Non hai ancora effettuato nessun ordine.</p>
                    <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-primary" style="margin-top: 20px;">Inizia gli Acquisti</a>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary" style="margin-top: 20px;">Torna alla Home</a>
                </div>
            </c:when>

            <%-- Altrimenti, mostra la tabella con gli ordini --%>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>ID Ordine</th>
                        <th>Data</th>
                        <th>Totale</th>
                        <th>Stato</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listaOrdini}" var="ordine">
                        <tr>
                            <td>#${ordine.id}</td>
                            <td>${ordine.data}</td>
                            <td>${ordine.totale} €</td>
                            <td>
                                <span class="status status-${ordine.stato.toLowerCase().replace(' ', '-')}">${ordine.stato}</span>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/dettaglio-ordine?id=${ordine.id}" class="btn btn-primary">Vedi Dettagli</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div style="margin-top: 30px; text-align: center;">
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Torna alla Home</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>