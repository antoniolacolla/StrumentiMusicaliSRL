package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.DAO.impl.StrumentoDAOImpl;
import com.Strumentimusicali.model.Strumento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CatalogoServlet", value = "/catalogo")
public class CatalogoServlet extends HttpServlet {
    private StrumentoDAO strumentoDAO;
    private static final int ELEMENTI_PER_PAGINA = 12;

    @Override
    public void init() {
        this.strumentoDAO = new StrumentoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] categorie = request.getParameterValues("categoria");
        String[] marche = request.getParameterValues("marca");
        String ricerca = request.getParameter("ricerca");
        String ordinamento = request.getParameter("ordinamento");

        int paginaAttuale = 1;
        String paginaParam = request.getParameter("pagina");
        if (paginaParam != null && paginaParam.matches("\\d+")) {
            paginaAttuale = Integer.parseInt(paginaParam);
        }

        List<Strumento> catalogo = strumentoDAO.cercaStrumenti(categorie, marche, ricerca, ordinamento, paginaAttuale, ELEMENTI_PER_PAGINA);
        int totaleStrumenti = strumentoDAO.contaStrumenti(categorie, marche, ricerca);
        int numeroPagine = (int) Math.ceil((double) totaleStrumenti / ELEMENTI_PER_PAGINA);

        // Costruisce la query string per mantenere i filtri nei link della paginazione
        StringBuilder queryString = new StringBuilder();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (!entry.getKey().equals("pagina")) {
                for (String value : entry.getValue()) {
                    if (queryString.length() > 0) {
                        queryString.append("&");
                    }
                    queryString.append(entry.getKey()).append("=").append(value);
                }
            }
        }

        request.setAttribute("catalogo", catalogo);
        request.setAttribute("numeroPagine", numeroPagine);
        request.setAttribute("paginaAttuale", paginaAttuale);
        request.setAttribute("queryString", queryString.toString());

        request.setAttribute("categorie", strumentoDAO.trovaTutteLeCategorie());
        request.setAttribute("marche", strumentoDAO.trovaTuttiIMarchi());
        request.setAttribute("categorieAttuali", categorie != null ? Arrays.asList(categorie) : Collections.emptyList());
        request.setAttribute("marcheAttuali", marche != null ? Arrays.asList(marche) : Collections.emptyList());
        request.setAttribute("ricercaAttuale", ricerca);
        request.setAttribute("ordinamentoAttuale", ordinamento);

        request.getRequestDispatcher("/jsp/catalogo.jsp").forward(request, response);
    }
}