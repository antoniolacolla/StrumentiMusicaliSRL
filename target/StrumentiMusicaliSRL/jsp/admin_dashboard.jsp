<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestione Utenti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container">
        <h1>Gestione Utenti</h1>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome Cognome</th>
                <th>Email</th>
                <th>Ruolo</th>
                <th>Ordini</th>
                <th>Stato</th>
                <th>Azioni</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listaUtenti}" var="utente">
                <tr>
                    <td>${utente.id}</td>
                    <td>${utente.nome} ${utente.cognome}</td>
                    <td>${utente.email}</td>
                    <td>${utente.ruolo}</td>
                    <td>${conteggioOrdini[utente.id]}</td>
                    <td>
                        <c:if test="${utente.bloccato}"><strong>Bloccato</strong></c:if>
                        <c:if test="${not utente.bloccato}">Attivo</c:if>
                    </td>
                    <td>
                            <%-- Impedisce azioni sull'utente loggato e sull'admin principale (ID 1) --%>
                        <c:if test="${utente.id != sessionScope.utenteLoggato.id && utente.id != 1}">
                            <div style="display: flex; gap: 5px;">
                                <form action="${pageContext.request.contextPath}/admin/gestione-utente" method="POST">
                                    <input type="hidden" name="utenteId" value="${utente.id}">
                                    <c:if test="${utente.bloccato}">
                                        <input type="hidden" name="action" value="sblocca">
                                        <button type="submit" class="btn btn-success">Sblocca</button>
                                    </c:if>
                                    <c:if test="${not utente.bloccato}">
                                        <input type="hidden" name="action" value="blocca">
                                        <button type="submit" class="btn btn-danger">Blocca</button>
                                    </c:if>
                                </form>
                                <form action="${pageContext.request.contextPath}/admin/gestione-utente" method="POST">
                                    <input type="hidden" name="utenteId" value="${utente.id}">
                                    <c:if test="${utente.ruolo == 'cliente'}">
                                        <input type="hidden" name="action" value="promuovi">
                                        <button type="submit" class="btn btn-primary">Promuovi</button>
                                    </c:if>
                                    <c:if test="${utente.ruolo == 'admin'}">
                                        <input type="hidden" name="action" value="demuovi">
                                        <button type="submit" class="btn btn-secondary">Demuovi</button>
                                    </c:if>
                                </form>
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div style="margin-top: 30px; border-top: 1px solid #dee2e6; padding-top: 20px;">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">‹ Vai alla Home Pubblica</a>
        </div>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>