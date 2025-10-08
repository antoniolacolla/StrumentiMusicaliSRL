<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dettaglio: ${strumento.marca} ${strumento.nome}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* Contenitore principale per il layout a due colonne */
        .product-detail-container {
            display: flex;
            gap: 40px; /* Spazio tra immagine e testo */
            margin-top: 20px;
        }

        /* Colonna dell'immagine a sinistra */
        .product-image-column {
            flex: 1; /* Occupa metà dello spazio */
            max-width: 50%;
        }

        .product-image-column img {
            width: 100%;
            height: auto;
            border-radius: 8px;
            border: 1px solid #ddd;
        }

        /* Colonna delle informazioni a destra */
        .product-info-column {
            flex: 1; /* Occupa l'altra metà dello spazio */
            display: flex;
            flex-direction: column;
        }

        .product-info-column .product-brand {
            font-size: 18px;
            color: #666;
            margin: 0;
        }

        .product-info-column .product-name {
            font-size: 32px;
            margin: 5px 0 15px 0;
            line-height: 1.2;
        }

        .product-info-column .product-price {
            font-size: 28px;
            font-weight: bold;
            color: #d9534f;
            margin-bottom: 20px;
        }

        .product-info-column .product-availability {
            font-size: 16px;
            color: #28a745;
            margin-bottom: 20px;
        }

        .add-to-cart-form {
            display: flex;
            gap: 15px;
            align-items: center;
            margin-bottom: 25px;
        }

        .add-to-cart-form input[type="number"] {
            width: 70px;
            padding: 10px;
            font-size: 16px;
        }

        .product-description {
            margin-top: 20px;
            border-top: 1px solid #eee;
            padding-top: 20px;
            line-height: 1.6;
        }
    </style>
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>

<main>
    <div class="container">

        <div class="product-detail-container">

            <div class="product-image-column">
                <img src="${pageContext.request.contextPath}/${strumento.urlImmagine}" alt="${strumento.nome}">
            </div>

            <div class="product-info-column">
                <h3 class="product-brand">${strumento.marca}</h3>
                <h1 class="product-name">${strumento.nome}</h1>
                <p class="product-price">${strumento.prezzo} €</p>

                <c:if test="${strumento.quantitaDisponibile > 0}">
                    <p class="product-availability">Disponibilità: ${strumento.quantitaDisponibile} pezzi</p>
                </c:if>

                <c:if test="${sessionScope.utenteLoggato.ruolo != 'admin' && strumento.quantitaDisponibile > 0}">
                    <form action="${pageContext.request.contextPath}/carrello" method="POST" class="add-to-cart-form">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="prodottoId" value="${strumento.id}">

                        <label for="quantita">Quantità:</label>
                        <input type="number" id="quantita" name="quantita" value="1" min="1" max="${strumento.quantitaDisponibile}">

                        <button type="submit" class="btn btn-success">Aggiungi al Carrello</button>
                    </form>
                </c:if>

                <c:if test="${strumento.quantitaDisponibile <= 0}">
                    <button type="button" class="btn btn-secondary" disabled>Prodotto non disponibile</button>
                </c:if>

                <div class="product-description">
                    <h3>Descrizione</h3>
                    <p>${strumento.descrizione}</p>
                </div>
            </div>

        </div>

        <div style="margin-top: 40px; text-align: center;">
            <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-secondary">‹ Torna al Catalogo</a>
        </div>

    </div>
</main>

<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>