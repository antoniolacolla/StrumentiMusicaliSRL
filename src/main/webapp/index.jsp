<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Benvenuto su Strumenti Musicali</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<div class="hero-section">
    <h1>La Musica Inizia Qui</h1>
    <p>Esplora la nostra selezione dei migliori strumenti musicali per professionisti e amatori.</p>
    <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-primary">Scopri il Catalogo</a>
</div>
<main>
    <div class="container">
        <h2 style="text-align: center; margin-bottom: 40px;">Prodotti in Evidenza</h2>
        <div class="product-grid">
            <c:forEach items="${vetrina}" var="strumento">
                <div class="product-card">
                    <div class="product-card-image">
                        <a href="${pageContext.request.contextPath}/dettaglio?id=${strumento.id}">
                            <img src="${pageContext.request.contextPath}/${strumento.urlImmagine}" alt="${strumento.nome}">
                        </a>
                    </div>
                    <div class="product-card-content">
                        <div>
                            <a href="${pageContext.request.contextPath}/dettaglio?id=${strumento.id}" style="color: inherit; text-decoration: none;">
                                <h3>${strumento.marca} ${strumento.nome}</h3>
                            </a>
                            <p class="price">${strumento.prezzo} €</p>
                        </div>
                        <c:if test="${sessionScope.utenteLoggato.ruolo != 'admin' && strumento.quantitaDisponibile > 0}">
                            <form action="${pageContext.request.contextPath}/carrello" method="POST">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="prodottoId" value="${strumento.id}">
                                <button type="submit" class="btn btn-success">Aggiungi al Carrello</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>