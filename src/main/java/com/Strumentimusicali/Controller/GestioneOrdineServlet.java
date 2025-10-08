package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.OrdineDAO;
import com.Strumentimusicali.DAO.impl.OrdineDAOImpl;
import com.Strumentimusicali.model.Ordine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        try {
            long idOrdine = Long.parseLong(request.getParameter("idOrdine"));
            String nuovoStato = request.getParameter("nuovoStato");

            Ordine ordine = ordineDAO.trovaPerId(idOrdine);

            if (ordine != null) {
                ordine.setStato(nuovoStato);
                ordineDAO.aggiorna(ordine);

                // Imposta un messaggio di successo nella sessione
                HttpSession session = request.getSession();
                session.setAttribute("messaggio", "Stato dell'ordine #" + idOrdine + " aggiornato con successo.");
                session.setAttribute("tipoMessaggio", "successo");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("messaggio", "Errore durante l'aggiornamento dell'ordine: ID non valido.");
            session.setAttribute("tipoMessaggio", "errore");
        }

        // Reindirizza l'admin alla pagina con la lista aggiornata
        response.sendRedirect(request.getContextPath() + "/admin/ordini");
    }
}