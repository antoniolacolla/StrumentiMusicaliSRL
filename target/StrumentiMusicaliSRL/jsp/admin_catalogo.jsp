<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestione Catalogo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container">
        <div style="display:flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h1>Gestione Catalogo</h1>
            <a href="${pageContext.request.contextPath}/admin/gestione-prodotto" class="btn btn-success">Aggiungi Prodotto</a>
        </div>
        <table>
            <thead>
            <tr><th>ID</th><th>Prodotto</th><th>Prezzo</th><th>Q.tà</th><th>Stato</th><th>In Vetrina</th><th></th></tr>
            </thead>
            <tbody>
            <c:forEach items="${listaProdotti}" var="p">
                <tr>
                    <td>${p.id}</td>
                    <td><strong>${p.marca}</strong> ${p.nome}</td>
                    <td>${p.prezzo} €</td>
                    <td>${p.quantitaDisponibile}</td>
                    <td>
                        <c:if test="${p.attivo}"><span class="status status-consegnato">Attivo</span></c:if>
                        <c:if test="${not p.attivo}"><span class="status status-in-elaborazione">Nascosto</span></c:if>
                    </td>
                    <td>${p.inVetrina ? 'Sì' : 'No'}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/gestione-prodotto?action=modifica&id=${p.id}" class="btn btn-primary">Modifica</a>
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