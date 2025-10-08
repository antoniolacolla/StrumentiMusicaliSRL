package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.OrdineDAO;
import com.Strumentimusicali.DAO.impl.OrdineDAOImpl;
import com.Strumentimusicali.model.Ordine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GestioneOrdineServlet", value = "/admin/gestione-ordine")
public class GestioneOrdineServlet extends HttpServlet {
    private OrdineDAO ordineDAO;

    @Override
    public void init() {
        this.ordineDAO = new OrdineDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long idOrdine = Long.parseLong(request.getParameter("idOrdine"));
        String nuovoStato = request.getParameter("nuovoStato");

        Ordine ordine = ordineDAO.trovaPerId(idOrdine);
        if (ordine != null) {
            ordine.setStato(nuovoStato);
            ordineDAO.aggiorna(ordine);
        }

        response.sendRedirect(request.getContextPath() + "/admin/ordini");
    }
}