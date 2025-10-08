<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dettaglio Ordine #${ordine.id}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .order-summary-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 30px;
        }
        .summary-item {
            flex: 1 1 200px; /* Flex-grow, flex-shrink, flex-basis */
        }
        .summary-item h3 {
            margin-top: 0;
            font-size: 16px;
            color: #555;
            border-bottom: 1px solid #eee;
            padding-bottom: 5px;
        }
        .summary-item p {
            font-size: 18px;
            margin: 5px 0 0 0;
        }
        .products-table {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>

<main>
    <div class="container">
        <h1>Dettaglio Ordine #${ordine.id}</h1>

        <div class="order-summary-container">
            <div class="summary-item">
                <h3>Data Acquisto</h3>
                <p>${ordine.data}</p>
            </div>
            <div class="summary-item">
                <h3>Stato Ordine</h3>
                <p>
                    <span class="status status-${ordine.stato.toLowerCase().replace(' ', '-')}">${ordine.stato}</span>
                </p>
            </div>
            <div class="summary-item">
                <h3>Metodo di Pagamento</h3>
                <p>${ordine.metodoPagamento}</p>
            </div>
            <div class="summary-item" style="flex-basis: 100%;">
                <h3>Indirizzo di Spedizione</h3>
                <p>
                    ${ordine.indirizzo}<br>
                    ${ordine.cap}, ${ordine.citta}
                </p>
            </div>
        </div>

        <h2 class="products-table">Prodotti Acquistati</h2>
        <table>
            <thead>
            <tr>
                <th>Prodotto</th>
                <th>Quantità</th>
                <th>Prezzo Unitario</th>
                <th>Subtotale</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${ordine.prodottiAcquistati}" var="entry">
                <tr>
                    <td>
                        <div style="display: flex; align-items: center;">
                            <img src="${pageContext.request.contextPath}/${entry.key.urlImmagine}" alt="${entry.key.nome}" width="60" style="margin-right: 15px;">
                            <div>
                                <strong>${entry.key.marca}</strong><br>
                                    ${entry.key.nome}
                            </div>
                        </div>
                    </td>
                    <td>${entry.value}</td>
                    <td>${entry.key.prezzo} €</td>
                    <td>${entry.key.prezzo * entry.value} €</td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="3" style="text-align: right;"><strong>Totale Ordine:</strong></td>
                <td><strong>${ordine.totale} €</strong></td>
            </tr>
            </tfoot>
        </table>

        <div style="margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/storico-ordini" class="btn btn-secondary">‹ Torna ai Tuoi Ordini</a>
        </div>

    </div>
</main>

<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>