<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Catalogo Prodotti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container">
        <h1>Il Nostro Catalogo</h1>
        <div class="product-grid">
            <c:forEach items="${catalogo}" var="strumento">
                <div class="product-card">
                    <a href="${pageContext.request.contextPath}/dettaglio?id=${strumento.id}" style="text-decoration: none; color: inherit;">
                        <div class="product-card-image">
                            <img src="${pageContext.request.contextPath}/${strumento.urlImmagine}" alt="${strumento.nome}">
                        </div>
                    </a>
                    <div class="product-card-content">
                        <div>
                            <a href="${pageContext.request.contextPath}/dettaglio?id=${strumento.id}" style="text-decoration: none; color: inherit;">
                                <h3>${strumento.marca} ${strumento.nome}</h3>
                            </a>
                            <p class="price">${strumento.prezzo} €</p>
                        </div>

                        <c:choose>
                            <c:when test="${sessionScope.utenteLoggato.ruolo != 'admin' && strumento.quantitaDisponibile > 0}">
                                <form action="${pageContext.request.contextPath}/carrello" method="POST" style="margin-top: 10px;">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="prodottoId" value="${strumento.id}">
                                    <button type="submit" class="btn btn-success">Aggiungi al Carrello</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-secondary" disabled style="margin-top: 10px; cursor: not-allowed;">Non Disponibile</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>