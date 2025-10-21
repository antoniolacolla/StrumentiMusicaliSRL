<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty prodotto ? 'Aggiungi Prodotto' : 'Modifica Prodotto'}</title>
    <%-- Assicurati di avere il link al tuo CSS --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <style>
        .form-layout { max-width: 800px; margin: 0 auto; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; font-weight: bold; }
        .form-group input[type="text"],
        .form-group input[type="number"],
        .form-group input[type="file"],
        .form-group textarea,
        .form-group select {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            border: 1px solid var(--border-color, #ccc);
            border-radius: 5px;
            box-sizing: border-box;
        }
        .form-group textarea { height: 150px; resize: vertical; }
        .form-group-checkbox { display: flex; align-items: center; gap: 10px; }

        .current-image-preview {
            font-weight: bold;
            margin-bottom: 15px;
        }
        .current-image-preview img {
            max-width: 200px;
            height: auto;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container">
        <h1>${empty prodotto ? 'Aggiungi Nuovo Prodotto' : 'Modifica Prodotto'}</h1>

        <%--
          IMPORTANTE: Aggiungi enctype="multipart/form-data"
          per abilitare l'upload di file.
        --%>
        <form action="${pageContext.request.contextPath}/admin/gestione-prodotto" method="POST" enctype="multipart/form-data" class="form-layout">
            <c:if test="${not empty prodotto}">
                <input type="hidden" name="id" value="${prodotto.id}">
            </c:if>

            <%-- (Campi nome, marca, categoria, prezzo, quantita) --%>
            <div class="form-group">
                <label for="nome">Nome Prodotto</label>
                <input type="text" id="nome" name="nome" value="${prodotto.nome}" required>
            </div>
            <div class="form-group">
                <label for="marca">Marca</label>
                <input type="text" id="marca" name="marca" value="${prodotto.marca}" required>
            </div>
            <div class="form-group">
                <label for="categoria">Categoria</label>
                <input type="text" id="categoria" name="categoria" value="${prodotto.categoria}" required>
            </div>
            <div class="form-group">
                <label for="prezzo">Prezzo (€)</label>
                <input type="number" id="prezzo" name="prezzo" value="${prodotto.prezzo}" step="0.01" required>
            </div>
            <div class="form-group">
                <label for="quantita">Quantità Disponibile</label>
                <input type="number" id="quantita" name="quantita" value="${prodotto.quantitaDisponibile}" required>
            </div>

            <%-- NUOVA SEZIONE IMMAGINE --%>
            <div class="form-group">
                <label for="immagineFile">Immagine Prodotto</label>

                <c:if test="${not empty prodotto.urlImmagine}">
                    <div class="current-image-preview">
                        Immagine attuale:
                        <img src="${pageContext.request.contextPath}/${prodotto.urlImmagine}" alt="Immagine prodotto">
                    </div>
                    <p>Carica un nuovo file solo se vuoi sostituire l'immagine attuale.</p>
                </c:if>

                <input type="file" id="immagineFile" name="immagineFile" accept="image/png, image/jpeg, image/jpg">
            </div>

            <%--
              Campo nascosto per conservare il percorso della vecchia immagine
              se non ne viene caricata una nuova.
            --%>
            <input type="hidden" name="urlImmagineEsistente" value="${prodotto.urlImmagine}">

            <%-- (Campo descrizione e checkbox) --%>
            <div class="form-group">
                <label for="descrizione">Descrizione</label>
                <textarea id="descrizione" name="descrizione" required>${prodotto.descrizione}</textarea>
            </div>
            <div class="form-group form-group-checkbox">
                <input type="checkbox" id="attivo" name="attivo" value="true" ${prodotto.attivo ? 'checked' : ''}>
                <label for="attivo">Prodotto Attivo (visibile nel catalogo)</label>
            </div>
            <div class="form-group form-group-checkbox">
                <input type="checkbox" id="in_vetrina" name="in_vetrina" value="true" ${prodotto.inVetrina ? 'checked' : ''}>
                <label for="in_vetrina">In Vetrina (mostra in homepage)</label>
            </div>

            <div style="margin-top: 30px;">
                <button type="submit" class="btn btn-success">Salva Prodotto</button>
                <a href="${pageContext.request.contextPath}/admin/catalogo" class="btn btn-secondary">Annulla</a>
            </div>
        </form>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>