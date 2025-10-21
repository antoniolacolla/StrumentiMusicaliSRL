<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Utilizziamo jsp:useBean e fmt:formatDate per ottenere dinamicamente l'anno corrente.
  Questo evita di dover aggiornare manualmente il copyright ogni anno.
--%>
<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />

<footer class="site-footer">
    <div class="footer-inner-container">
        <div class="footer-grid">
            <div class="footer-about">
                <h3 class="footer-logo">StrumentiMusicali</h3>
                <p>La tua destinazione per i migliori strumenti musicali. Qualita', passione e competenza al tuo servizio.</p>
                <div class="footer-socials">
                    <%--
                      Miglioramenti nei link:
                      - <c:url>: Gestisce correttamente il context path, è più pulito e sicuro di <%= pageContext.request.contextPath %>.
                      - rel="noopener noreferrer": Una best practice di sicurezza per i link che aprono una nuova scheda.
                      - aria-label: Migliora l'accessibilità per gli screen reader.
                    --%>
                    <a href="https://instagram.com/strumentimusicalisrl" target="_blank" rel="noopener noreferrer" aria-label="Seguici su Instagram">
                        <img src="<c:url value='/images/icon_instagram.png'/>" alt="Instagram">
                    </a>
                    <a href="https://facebook.com/strumentimusicalisrl" target="_blank" rel="noopener noreferrer" aria-label="Seguici su Facebook">
                        <img src="<c:url value='/images/icon_facebook.png'/>" alt="Facebook">
                    </a>
                    <a href="https://x.com/strumentisrl" target="_blank" rel="noopener noreferrer" aria-label="Seguici su X (ex Twitter)">
                        <img src="<c:url value='/images/icon_x.png'/>" alt="X">
                    </a>
                    <a href="https://www.google.com/maps/search/?api=1&query=Via+Giuseppe+Saragat+1+Ferrara" target="_blank" rel="noopener noreferrer" aria-label="Trovaci su Google Maps">
                        <img src="<c:url value='/images/icon_maps.png'/>" alt="Google Maps">
                    </a>
                </div>
            </div>
            <div class="footer-contact">
                <h4>Contattaci</h4>
                <%--
                  Il tag <address> è semanticamente più corretto per contenere
                  le informazioni di contatto.
                --%>
                <address>
                    <strong>Sede:</strong><br>
                    StrumentiMusicali S.R.L.<br>
                    c/o Universita' degli Studi di Ferrara<br>
                    Via Giuseppe Saragat, 1 - 44122 Ferrara
                </address>
                <p>
                    <strong>Email:</strong> <a href="mailto:supporto@strumentimusicali.it">supporto@strumentimusicali.it</a><br>
                    <strong>P. IVA:</strong> IT01234567890
                </p>
            </div>
        </div>
        <div class="footer-bottom">
            <%-- La variabile ${currentYear} contiene l'anno corrente, calcolato dinamicamente. --%>
            <p>© ${currentYear} StrumentiMusicali S.R.L. - Tutti i diritti riservati.</p>
        </div>
    </div>
</footer>