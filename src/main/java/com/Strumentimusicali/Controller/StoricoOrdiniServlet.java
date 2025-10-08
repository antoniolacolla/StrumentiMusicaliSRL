package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.OrdineDAO;
import com.Strumentimusicali.DAO.impl.OrdineDAOImpl;
import com.Strumentimusicali.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StoricoOrdiniServlet", value = "/storico-ordini")
public class StoricoOrdiniServlet extends HttpServlet {
    private OrdineDAO ordineDAO;

    @Override
    public void init() {
        this.ordineDAO = new OrdineDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");

        // Se l'utente è loggato, recupera i suoi ordini
        if (utenteLoggato != null) {
            request.setAttribute("listaOrdini", ordineDAO.trovaPerIdUtente(utenteLoggato.getId()));
        }

        // Inoltra la richiesta alla pagina JSP
        request.getRequestDispatcher("/jsp/storico_ordini.jsp").forward(request, response);
    }
}