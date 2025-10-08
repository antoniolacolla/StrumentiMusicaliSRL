<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dettaglio: ${strumento.nome}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container">
        <c:if test="${not empty strumento}">
            <div class="product-detail-layout">
                <div class="product-image-large">
                    <img src="${pageContext.request.contextPath}/${strumento.urlImmagine}" alt="Immagine di ${strumento.nome}">
                </div>
                <div class="product-details">
                    <h1>${strumento.marca} - ${strumento.nome}</h1>
                    <p><strong>Categoria:</strong> ${strumento.categoria}</p>
                    <p class="price">${strumento.prezzo} €</p>
                    <hr>
                    <h2>Descrizione</h2>
                    <p>${strumento.descrizione}</p> <%-- <-- ECCO LA RIGA CHE MANCAVA --%>
                    <hr>
                    <p><strong>Disponibilità:</strong> ${strumento.quantitaDisponibile} pezzi</p>
                    <c:if test="${sessionScope.utenteLoggato.ruolo != 'admin' && strumento.quantitaDisponibile > 0}">
                        <form action="${pageContext.request.contextPath}/carrello" method="POST" style="margin-top: 20px; display: flex; align-items: center; gap: 15px;">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="prodottoId" value="${strumento.id}">
                            <div class="form-group" style="margin: 0;">
                                <label for="quantita" style="font-weight: bold;">Quantità:</label>
                                <input type="number" id="quantita" name="quantita" value="1" min="1" max="${strumento.quantitaDisponibile}" style="width: 80px; padding: 10px;">
                            </div>
                            <button type="submit" class="btn btn-success">Aggiungi al Carrello</button>
                        </form>
                    </c:if>
                </div>
            </div>
            <div style="margin-top: 40px;">
                <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-primary">‹ Torna al Catalogo</a>
            </div>
        </c:if>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>