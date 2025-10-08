package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.OrdineDAO;
import com.Strumentimusicali.DAO.impl.OrdineDAOImpl;
import com.Strumentimusicali.model.Ordine;
import com.Strumentimusicali.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DettaglioOrdineServlet", value = "/dettaglio-ordine")
public class DettaglioOrdineServlet extends HttpServlet {
    private OrdineDAO ordineDAO;

    @Override
    public void init() {
        this.ordineDAO = new OrdineDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");
        if (utenteLoggato == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            long idOrdine = Long.parseLong(request.getParameter("id"));
            Ordine ordine = ordineDAO.trovaPerId(idOrdine);
            if (ordine != null && ordine.getIdUtente() == utenteLoggato.getId()) {
                request.setAttribute("ordine", ordine);
                request.getRequestDispatcher("/jsp/dettaglio_ordine.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/storico-ordini");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/storico-ordini");
        }
    }
}