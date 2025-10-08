<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <c:choose>
        <c:when test="${not empty strumento}"><title>Modifica Prodotto</title></c:when>
        <c:otherwise><title>Inserisci Prodotto</title></c:otherwise>
    </c:choose>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container form-container" style="max-width: 700px;">
        <c:choose>
            <c:when test="${not empty strumento}"><h1>Modifica Prodotto</h1></c:when>
            <c:otherwise><h1>Nuovo Prodotto</h1></c:otherwise>
        </c:choose>

        <form action="${pageContext.request.contextPath}/admin/gestione-prodotto" method="POST" enctype="multipart/form-data">
            <c:if test="${not empty strumento}"><input type="hidden" name="id" value="${strumento.id}"></c:if>

            <div class="form-group"><label>Nome:</label><input type="text" name="nome" value="${strumento.nome}" required></div>
            <div class="form-group"><label>Marca:</label><input type="text" name="marca" value="${strumento.marca}" required></div>
            <div class="form-group"><label>Categoria:</label><input type="text" name="categoria" value="${strumento.categoria}" required></div>
            <div class="form-group"><label>Prezzo:</label><input type="number" step="0.01" name="prezzo" value="${strumento.prezzo}" required></div>
            <div class="form-group"><label>Quantità Magazzino:</label><input type="number" name="quantita" value="${strumento.quantitaDisponibile}" required></div>

            <div class="form-group">
                <label>Immagine Prodotto:</label>
                <input type="file" name="immagineFile" accept="image/png, image/jpeg, image/jpg">
                <c:if test="${not empty strumento.urlImmagine}">
                    <div style="margin-top: 10px;"><p>Immagine attuale:</p><img src="${pageContext.request.contextPath}/${strumento.urlImmagine}" alt="Immagine attuale" style="max-width: 100px; border-radius: 5px;"></div>
                </c:if>
            </div>

            <div class="form-group"><label>Descrizione:</label><textarea name="descrizione" rows="5">${strumento.descrizione}</textarea></div>

            <div class="form-group-checkbox">
                <input type="checkbox" name="inVetrina" id="inVetrina" <c:if test="${strumento.inVetrina}">checked</c:if>>
                <label for="inVetrina">Mostra in vetrina</e-label>
            </div>
            <div class="form-group-checkbox">
                <input type="checkbox" name="attivo" id="attivo" <c:if test="${empty strumento || strumento.attivo}">checked</c:if>>
                <label for="attivo">Prodotto Attivo (visibile ai clienti)</label>
            </div>

            <div style="display: flex; gap: 10px; margin-top: 30px;">
                <button type="submit" class="btn btn-success" style="flex-grow: 2;">Salva Prodotto</button>
                <a href="${pageContext.request.contextPath}/admin/catalogo" class="btn btn-danger" style="flex-grow: 1;">Annulla</a>
            </div>
        </form>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>