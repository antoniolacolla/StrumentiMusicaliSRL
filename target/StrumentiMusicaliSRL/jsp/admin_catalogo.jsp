<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestione Catalogo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* --- STILI PAGINAZIONE MIGLIORATI --- */
        .pagination {
            display: flex;
            justify-content: flex-end; /* 1. Allineato a destra */
            margin-top: 50px; /* 3. Aumenta la distanza dalla tabella */
        }
        .pagination a {
            padding: 12px 20px; /* 2. Pulsanti più grandi */
            font-size: 16px; /* 2. Testo più grande */
        }
    </style>
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container">
        <h1>Gestione Catalogo Prodotti</h1>
        <div style="margin-bottom: 20px;">
            <a href="${pageContext.request.contextPath}/admin/gestione-prodotto" class="btn btn-success">Aggiungi Nuovo Prodotto</a>
        </div>

        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Marca</th>
                <th>Prezzo</th>
                <th>Q.tà</th>
                <th>Attivo</th>
                <th>Vetrina</th>
                <th>Azioni</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listaProdotti}" var="prodotto">
                <tr>
                    <td>${prodotto.id}</td>
                    <td>${prodotto.nome}</td>
                    <td>${prodotto.marca}</td>
                    <td>${prodotto.prezzo} €</td>
                    <td>${prodotto.quantitaDisponibile}</td>
                    <td>${prodotto.attivo ? 'Sì' : 'No'}</td>
                    <td>${prodotto.inVetrina ? 'Sì' : 'No'}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/gestione-prodotto?id=${prodotto.id}" class="btn btn-primary">Modifica</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%-- Controlli di paginazione con il nuovo stile --%>
        <div class="pagination">
            <c:if test="${paginaAttuale > 1}">
                <a href="catalogo?pagina=${paginaAttuale - 1}">&laquo; Precedente</a>
            </c:if>

            <c:forEach begin="1" end="${numeroPagine}" var="i">
                <a href="catalogo?pagina=${i}" class="${paginaAttuale == i ? 'active' : ''}">${i}</a>
            </c:forEach>

            <c:if test="${paginaAttuale < numeroPagine}">
                <a href="catalogo?pagina=${paginaAttuale + 1}">Successiva &raquo;</a>
            </c:if>
        </div>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>