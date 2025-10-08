package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.UtenteDAO;
import com.Strumentimusicali.DAO.impl.UtenteDAOImpl;
import com.Strumentimusicali.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GestioneUtenteServlet", value = "/admin/gestione-utente")
public class GestioneUtenteServlet extends HttpServlet {
    private UtenteDAO utenteDAO;

    @Override
    public void init() {
        this.utenteDAO = new UtenteDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idUtente = Integer.parseInt(request.getParameter("utenteId"));
        String action = request.getParameter("action");

        // --- CORREZIONE QUI (1) ---
        // Usa il nuovo nome del metodo in italiano
        Utente utente = utenteDAO.trovaPerId(idUtente);
        // --------------------------

        if (utente != null && utente.getId() != 1) { // Protezione per l'admin principale
            switch (action) {
                case "blocca":
                    utente.setBloccato(true);
                    break;
                case "sblocca":
                    utente.setBloccato(false);
                    break;
                case "promuovi":
                    utente.setRuolo("admin");
                    break;
                case "demuovi":
                    utente.setRuolo("cliente");
                    break;
            }
            // --- CORREZIONE QUI (2) ---
            // Usa il nuovo nome del metodo in italiano
            utenteDAO.aggiorna(utente);
            // --------------------------
        }
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}