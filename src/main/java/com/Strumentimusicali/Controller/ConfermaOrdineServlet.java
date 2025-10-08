package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.OrdineDAO;
import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.DAO.impl.OrdineDAOImpl;
import com.Strumentimusicali.DAO.impl.StrumentoDAOImpl;
import com.Strumentimusicali.model.Carrello;
import com.Strumentimusicali.model.Ordine;
import com.Strumentimusicali.model.Strumento;
import com.Strumentimusicali.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@WebServlet(name = "ConfermaOrdineServlet", value = "/conferma-ordine")
public class ConfermaOrdineServlet extends HttpServlet {
    private StrumentoDAO strumentoDAO;
    private OrdineDAO ordineDAO;

    @Override
    public void init() {
        this.strumentoDAO = new StrumentoDAOImpl();
        this.ordineDAO = new OrdineDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

        if (carrello != null && !carrello.getProdottiConQuantita().isEmpty() && utenteLoggato != null) {
            // Crea un nuovo oggetto Ordine e lo popola con i dati
            Ordine ordine = new Ordine();
            ordine.setIdUtente(utenteLoggato.getId());
            ordine.setData(LocalDate.now());
            ordine.setProdottiAcquistati(carrello.getProdottiConQuantita());
            ordine.setIndirizzo((String) session.getAttribute("indirizzo"));
            ordine.setCitta((String) session.getAttribute("citta"));
            ordine.setCap((String) session.getAttribute("cap"));
            ordine.setMetodoPagamento((String) session.getAttribute("metodoPagamento"));
            ordine.setStato("In elaborazione"); // Stato iniziale obbligatorio

            // Salva l'ordine nel database
            ordineDAO.salva(ordine);

            // Aggiorna la quantità in magazzino per ogni prodotto acquistato
            for (Map.Entry<Strumento, Integer> riga : carrello.getProdottiConQuantita().entrySet()) {
                strumentoDAO.aggiornaQuantita(riga.getKey().getId(), riga.getValue());
            }

            // Pulisce il carrello e i dati di sessione del checkout
            carrello.svuota();
            session.removeAttribute("indirizzo");
            session.removeAttribute("citta");
            session.removeAttribute("cap");
            session.removeAttribute("metodoPagamento");

            // Inoltra alla pagina di conferma
            request.setAttribute("ordineConfermato", ordine);
            request.getRequestDispatcher("/jsp/acquisto_confermato.jsp").forward(request, response);
        } else {
            // Se non ci sono i dati necessari, torna al carrello
            response.sendRedirect(request.getContextPath() + "/jsp/carrello.jsp");
        }
    }
}