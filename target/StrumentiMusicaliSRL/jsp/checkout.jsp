<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .payment-option { padding: 15px; border: 1px solid #ced4da; border-radius: 5px; margin-bottom: 10px; cursor: pointer; display: flex; align-items: center; transition: background-color 0.2s ease, border-color 0.2s ease; }
        .payment-option:hover { background-color: #f8f9fa; }
        .payment-option input { margin-right: 15px; }
        .payment-details { display: none; padding: 20px; border: 1px solid #007bff; border-top: none; border-radius: 0 0 5px 5px; margin-top: -11px; margin-bottom: 20px; background-color: #f8f9fa; }
    </style>
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container form-container" style="max-width: 600px;">
        <h1>Checkout</h1>

        <%-- STEP 1: INDIRIZZO --%>
        <c:if test="${step == 'address'}">
            <h2>1. Dati di Spedizione</h2>
            <form action="${pageContext.request.contextPath}/checkout" method="POST">
                <input type="hidden" name="step" value="address">
                <div class="form-group"><label>Nome:</label><input type="text" name="nome" value="${sessionScope.nomeSpedizione}" required></div>
                <div class="form-group"><label>Cognome:</label><input type="text" name="cognome" value="${sessionScope.cognomeSpedizione}" required></div>
                <div class="form-group"><label>Indirizzo:</label><input type="text" name="indirizzo" value="${sessionScope.indirizzo}" required></div>
                <div class="form-group"><label>Città:</label><input type="text" name="citta" value="${sessionScope.citta}" required></div>
                <div class="form-group"><label>CAP:</label><input type="text" name="cap" value="${sessionScope.cap}" required></div>
                <div class="form-group"><label>Telefono:</label><input type="tel" name="telefono" value="${sessionScope.telefonoSpedizione}" required></div>
                <button type="submit" class="btn btn-primary" style="width:100%;">Vai al Pagamento ›</button>
            </form>
        </c:if>

        <%-- STEP 2: PAGAMENTO (UNIFICATO) --%>
        <c:if test="${step == 'payment'}">
            <h2>2. Metodo di Pagamento</h2>
            <form action="${pageContext.request.contextPath}/checkout" method="POST">
                <input type="hidden" name="step" value="payment">
                <label for="radioCarta" class="payment-option"><input type="radio" id="radioCarta" name="metodoPagamento" value="Carta di Credito" required> Carta di Credito</label>
                <div id="dettagliCarta" class="payment-details">
                    <h4>Inserisci i dati della tua carta (simulazione)</h4>
                    <div class="form-group"><label>Numero Carta:</label><input type="text" placeholder="1111222233334444" pattern="[0-9]{16}" maxlength="16"></div>
                    <div class="form-group"><label>Scadenza (MM/AA):</label><input type="text" placeholder="MM/AA" pattern="(0[1-9]|1[0-2])\/[0-9]{2}" maxlength="5"></div>
                    <div class="form-group"><label>CVC:</label><input type="text" placeholder="123" pattern="[0-9]{3}" maxlength="3"></div>
                </div>
                <label for="radioPaypal" class="payment-option"><input type="radio" id="radioPaypal" name="metodoPagamento" value="PayPal"> PayPal</label>
                <label for="radioBonifico" class="payment-option"><input type="radio" id="radioBonifico" name="metodoPagamento" value="Bonifico Bancario"> Bonifico Bancario</label>
                <div id="dettagliBonifico" class="payment-details">
                    <div class="payment-info" style="border-left: none; padding: 0;"><h4>Istruzioni per il Bonifico</h4><p>IBAN: <code>IT60 X054 2811 1010 0000 0123 456</code></p></div>
                </div>
                <button type="submit" class="btn btn-primary" style="width:100%; margin-top: 20px;">Continua ›</button>
            </form>
            <div style="margin-top: 15px; text-align: center;"><a href="${pageContext.request.contextPath}/checkout?step=address" class="btn" style="background-color: #6c757d; color: white;">‹ Torna alla Spedizione</a></div>
        </c:if>

        <%-- STEP 3: RIEPILOGO --%>
        <c:if test="${step == 'confirm'}">
            <h2>3. Riepilogo e Conferma</h2>
            <div class="summary">
                <h4>Prodotti:</h4>
                <ul><c:forEach items="${sessionScope.carrello.getProdottiConQuantita()}" var="entry"><li>${entry.value}x ${entry.key.nome}</li></c:forEach></ul><hr>
                <h4>Spedito a:</h4>
                <p>${sessionScope.nomeSpedizione} ${sessionScope.cognomeSpedizione}<br>${sessionScope.indirizzo}, ${sessionScope.citta}, ${sessionScope.cap}<br>Tel: ${sessionScope.telefonoSpedizione}</p><hr>
                <h4>Metodo di Pagamento:</h4>
                <p>${sessionScope.metodoPagamento}</p>
                <h3>Totale: ${sessionScope.carrello.getTotale()} €</h3>
            </div>
            <form action="${pageContext.request.contextPath}/conferma-ordine" method="POST" style="margin-top: 20px;"><button type="submit" class="btn btn-success" style="width:100%;">Conferma e Acquista Ora</button></form>
            <div style="margin-top: 15px; text-align: center;"><a href="${pageContext.request.contextPath}/checkout?step=payment" class="btn" style="background-color: #6c757d; color: white;">‹ Torna al Pagamento</a></div>
        </c:if>

        <div style="margin-top: 20px; border-top: 1px solid #dee2e6; padding-top: 20px; text-align: center;">
            <a href="${pageContext.request.contextPath}/jsp/carrello.jsp" class="btn" style="background-color: #dc3545; color: white;">Annulla e Torna al Carrello</a>
        </div>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const paymentRadios = document.querySelectorAll('input[name="metodoPagamento"]');
        const detailsDivs = {'radioCarta': document.getElementById('dettagliCarta'),'radioBonifico': document.getElementById('dettagliBonifico')};
        const allDetails = document.querySelectorAll('.payment-details');
        paymentRadios.forEach(radio => {
            radio.addEventListener('change', function() {
                allDetails.forEach(div => div.style.display = 'none');
                if (detailsDivs[this.id]) { detailsDivs[this.id].style.display = 'block'; }
            });
        });
    });
</script>
</body>
</html>