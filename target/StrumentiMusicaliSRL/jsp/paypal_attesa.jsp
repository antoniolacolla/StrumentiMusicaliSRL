<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Procedi con PayPal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container" style="text-align: center;">
        <div id="sceltaPaypal">
            <h1>Completa il pagamento con PayPal</h1>
            <p>Clicca il pulsante qui sotto per accedere al tuo account PayPal e autorizzare il pagamento.</p>
            <div style="display: flex; flex-direction: column; gap: 10px; margin-top: 20px; max-width: 400px; margin-left: auto; margin-right: auto;">
                <button id="paypalBtn" class="btn btn-paypal" onclick="avviaPagamentoPayPal()">Paga con PayPal</button>
                <a href="${pageContext.request.contextPath}/checkout?step=payment" class="btn" style="background-color: #6c757d; color: white;">‹ Torna alla Scelta del Pagamento</a>
            </div>
        </div>
        <div id="attesa" style="display: none;">
            <div class="spinner"></div>
            <p><strong>Pagamento in corso...</strong> In attesa di conferma.</p>
        </div>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
<form id="confirmOrderForm" action="${pageContext.request.contextPath}/conferma-ordine" method="POST" style="display: none;"></form>
<script>
    function avviaPagamentoPayPal() {
        document.getElementById('sceltaPaypal').style.display = 'none';
        document.getElementById('attesa').style.display = 'block';
        window.open('https://www.paypal.com/signin', '_blank');
        setTimeout(function() { document.getElementById('confirmOrderForm').submit(); }, 10000);
    }
</script>
</body>
</html>