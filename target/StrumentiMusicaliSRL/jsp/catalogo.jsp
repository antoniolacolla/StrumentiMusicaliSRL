<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Catalogo Prodotti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .catalog-container { display: flex; gap: 30px; }
        .filters { flex: 0 0 250px; }
        .product-grid-container { flex: 1; }
        .filter-group { margin-bottom: 25px; }
        .filter-group h3 { margin-bottom: 10px; border-bottom: 1px solid #ccc; padding-bottom: 5px; }
        .filter-group ul { list-style: none; padding: 0; }
        .filter-group li { margin-bottom: 8px; }
        .filter-group label { cursor: pointer; }
        #search-input {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            box-sizing: border-box;
        }

        /* --- STILI PAGINAZIONE MIGLIORATI --- */
        .pagination {
            display: flex;
            justify-content: flex-end; /* 1. Allineato a destra */
            margin-top: 50px; /* 3. Aumenta la distanza dai prodotti */
        }
        .pagination a {
            padding: 12px 20px; /* 2. Pulsanti più grandi */
            font-size: 16px; /* 2. Testo più grande */
        }
    </style>
</head>
<body>
<jsp:include page="/jsp/partials/header.jsp"/>
<main>
    <div class="container">
        <h1>Il Nostro Catalogo</h1>
        <div class="catalog-container">

            <aside class="filters">
                <%-- (Il codice dei filtri resta invariato) --%>
                <form action="catalogo" method="GET" id="filterForm">

                    <div class="filter-group">
                        <input type="text" id="search-input" name="ricerca" value="${ricercaAttuale}" placeholder="Cerca prodotto...">
                        <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 5px;">Cerca</button>
                    </div>

                    <div class="filter-group">
                        <h3>Ordina per</h3>
                        <select name="ordinamento" class="filter-change-listener" style="width:100%;">
                            <option value="default" ${empty ordinamentoAttuale or ordinamentoAttuale == 'default' ? 'selected' : ''}>Default</option>
                            <option value="prezzo_asc" ${ordinamentoAttuale == 'prezzo_asc' ? 'selected' : ''}>Prezzo crescente</option>
                            <option value="prezzo_desc" ${ordinamentoAttuale == 'prezzo_desc' ? 'selected' : ''}>Prezzo decrescente</option>
                        </select>
                    </div>

                    <div class="filter-group">
                        <h3>Categorie</h3>
                        <ul>
                            <c:forEach items="${categorie}" var="cat">
                                <li>
                                    <label>
                                        <input type="checkbox" name="categoria" value="${cat}" class="filter-change-listener"
                                               <c:if test="${categorieAttuali.contains(cat)}">checked</c:if>>
                                            ${cat}
                                    </label>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                    <div class="filter-group">
                        <h3>Marche</h3>
                        <ul>
                            <c:forEach items="${marche}" var="m">
                                <li>
                                    <label>
                                        <input type="checkbox" name="marca" value="${m}" class="filter-change-listener"
                                               <c:if test="${marcheAttuali.contains(m)}">checked</c:if>>
                                            ${m}
                                    </label>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </form>
            </aside>

            <div class="product-grid-container">
                <c:if test="${empty catalogo}">
                    <p>Nessun prodotto trovato con i criteri di ricerca selezionati.</p>
                </c:if>
                <div class="product-grid">
                    <%-- (Il codice della griglia prodotti resta invariato) --%>
                    <c:forEach items="${catalogo}" var="strumento">
                        <div class="product-card">
                            <a href="${pageContext.request.contextPath}/dettaglio?id=${strumento.id}"><div class="product-card-image"><img src="${pageContext.request.contextPath}/${strumento.urlImmagine}" alt="${strumento.nome}"></div></a>
                            <div class="product-card-content">
                                <a href="${pageContext.request.contextPath}/dettaglio?id=${strumento.id}"><h3>${strumento.marca} ${strumento.nome}</h3></a>
                                <p class="price">${strumento.prezzo} €</p>
                                <c:if test="${sessionScope.utenteLoggato.ruolo != 'admin' && strumento.quantitaDisponibile > 0}">
                                    <form action="${pageContext.request.contextPath}/carrello" method="POST" style="margin-top: 10px;">
                                        <input type="hidden" name="action" value="add"><input type="hidden" name="prodottoId" value="${strumento.id}">
                                        <button type="submit" class="btn btn-success">Aggiungi al Carrello</button>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <%-- CONTROLLI DI PAGINAZIONE --%>
                <div class="pagination">
                    <c:if test="${paginaAttuale > 1}">
                        <a href="catalogo?pagina=${paginaAttuale - 1}&${queryString}">&laquo; Precedente</a>
                    </c:if>

                    <c:forEach begin="1" end="${numeroPagine}" var="i">
                        <a href="catalogo?pagina=${i}&${queryString}" class="${paginaAttuale == i ? 'active' : ''}">${i}</a>
                    </c:forEach>

                    <c:if test="${paginaAttuale < numeroPagine}">
                        <a href="catalogo?pagina=${paginaAttuale + 1}&${queryString}">Successiva &raquo;</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/jsp/partials/footer.jsp"/>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const filterElements = document.querySelectorAll('.filter-change-listener');
        filterElements.forEach(function (element) {
            element.addEventListener('change', function () {
                document.getElementById('filterForm').submit();
            });
        });
    });
</script>
</body>
</html>