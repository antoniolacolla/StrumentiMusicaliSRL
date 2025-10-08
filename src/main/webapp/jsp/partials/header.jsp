<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="header">
    <div class="logo">
        <a href="${pageContext.request.contextPath}/">StrumentiMusicali</a>
    </div>
    <div class="nav-links">
        <c:choose>
            <%-- SE L'UTENTE È LOGGATO --%>
            <c:when test="${not empty sessionScope.utenteLoggato}">
                <span class="user-info">Benvenuto, ${sessionScope.utenteLoggato.nome}!</span>

                <%-- LINK VISIBILI SOLO AGLI ADMIN --%>
                <c:if test="${sessionScope.utenteLoggato.ruolo == 'admin'}">
                    <a href="${pageContext.request.contextPath}/admin/dashboard">Gestione Utenti</a>
                    <a href="${pageContext.request.contextPath}/admin/catalogo">Gestione Catalogo</a>
                    <a href="${pageContext.request.contextPath}/admin/ordini">Gestione Ordini</a>
                </c:if>

                <%-- LINK VISIBILI SOLO AI CLIENTI --%>
                <c:if test="${sessionScope.utenteLoggato.ruolo == 'cliente'}">
                    <a href="${pageContext.request.contextPath}/storico-ordini">Miei Ordini</a>
                    <a href="${pageContext.request.contextPath}/jsp/carrello.jsp">Carrello</a>
                </c:if>

                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </c:when>

            <%-- SE L'UTENTE NON È LOGGATO --%>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login">Login</a>
                <a href="${pageContext.request.contextPath}/registrazione">Registrati</a>
                <%-- AGGIUNTO: Link al carrello anche per utenti non loggati --%>
                <a href="${pageContext.request.contextPath}/jsp/carrello.jsp">Carrello</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>