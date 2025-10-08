<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Acquisto Confermato</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .confirmation-box {
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 30px;
            text-align: center;
            margin: 40px 0;
        }
        .confirmation-box h1 {
            color: #28a745;
        }
        .payment-instructions {
            margin-top: 30px;
            padding: 20px;
            border: 1px solid #007bff;
            background-color: #f0f8ff;
            border-radius: 5px;
            text-align: left;
        }
        .payment-instructions strong {
            font-size: 1.1em;
        }
    </style>
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>

<main>
    <div class="container">
        <div class="confirmation-box">
            <h1>Grazie per il tuo ordine!</h1>
            <p>Il tuo acquisto è stato confermato con successo.</p>
            <p><strong>Numero Ordine: #${ordineConfermato.id}</strong></p>
            <p>Riceverai a breve un'email di riepilogo.</p>

            <%-- NUOVA SEZIONE CHE APPARE SOLO IN CASO DI BONIFICO --%>
            <c:if test="${ordineConfermato.metodoPagamento == 'Bonifico Bancario'}">
                <div class="payment-instructions">
                    <h4>Istruzioni per il Bonifico Bancario</h4>
                    <p>Per completare l'ordine, effettua un bonifico alle seguenti coordinate:</p>
                    <p><strong>IBAN:</strong> IT60 X054 2811 1010 0000 0123 456</p>
                    <p><strong>Causale:</strong> Pagamento Ordine #${ordineConfermato.id}</p>
                    <hr>
                    <p class="text-muted" style="font-size: 0.9em;">
                        La merce verrà spedita appena avvenuta la ricezione del pagamento (solitamente 1-2 giorni lavorativi).
                    </p>
                </div>
            </c:if>

            <div style="margin-top: 30px;">
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Torna alla Home</a>
                <a href="${pageContext.request.contextPath}/storico-ordini" class="btn btn-secondary">Vedi i tuoi ordini</a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/jsp/partials/footer.jsp"/>
</body>
</html>