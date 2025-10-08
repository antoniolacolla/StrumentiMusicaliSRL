<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestione Ordini</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container">
        <h1>Gestione Ordini</h1>
        <table>
            <thead>
            <tr><th>ID Ordine</th><th>ID Utente</th><th>Data</th><th>Totale</th><th>Stato Attuale</th><th>Aggiorna Stato</th></tr>
            </thead>
            <tbody>
            <c:forEach items="${listaOrdini}" var="ordine">
                <tr>
                    <td>#${ordine.id}</td>
                    <td>${ordine.idUtente}</td>
                    <td>${ordine.data}</td>
                    <td>${ordine.totale} €</td>
                    <td><span class="status status-${ordine.stato.toLowerCase().replace(' ', '-')}">${ordine.stato}</span></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/gestione-ordine" method="POST">
                            <input type="hidden" name="idOrdine" value="${ordine.id}">
                            <select name="nuovoStato">
                                <option value="In elaborazione" ${ordine.stato == 'In elaborazione' ? 'selected' : ''}>In elaborazione</option>
                                <option value="Spedito" ${ordine.stato == 'Spedito' ? 'selected' : ''}>Spedito</option>
                                <option value="Consegnato" ${ordine.stato == 'Consegnato' ? 'selected' : ''}>Consegnato</option>
                            </select>
                            <button type="submit" class="btn btn-primary">Salva</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>