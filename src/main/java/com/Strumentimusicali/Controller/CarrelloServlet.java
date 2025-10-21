package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.DAO.impl.StrumentoDAOImpl;
import com.Strumentimusicali.model.Carrello;
import com.Strumentimusicali.model.Strumento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "CarrelloServlet", value = "/carrello")
public class CarrelloServlet extends HttpServlet {
    private StrumentoDAO strumentoDAO;

    @Override
    public void init() {
        this.strumentoDAO = new StrumentoDAOImpl();
    }

    // --- NUOVO METODO AGGIUNTO ---
    // Questo metodo gestisce le richieste GET, usate per VISUALIZZARE la pagina.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Il suo unico compito è inoltrare la richiesta alla pagina JSP del carrello.
        // I dati del carrello sono già nella sessione, quindi la JSP li troverà.
        request.getRequestDispatcher("/jsp/carrello.jsp").forward(request, response);
    }

    // Questo metodo gestisce le richieste POST, usate per MODIFICARE il carrello.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            switch (action) {
                case "add":
                    int prodottoIdAdd = Integer.parseInt(request.getParameter("prodottoId"));
                    int quantita = 1; // Di default aggiunge 1, potresti renderlo configurabile
                    Strumento strumentoToAdd = strumentoDAO.trovaPerId(prodottoIdAdd);
                    if (strumentoToAdd != null) {
                        carrello.aggiungiProdotto(strumentoToAdd, quantita);
                        session.setAttribute("messaggio", "Prodotto '" + strumentoToAdd.getNome() + "' aggiunto al carrello!");
                        session.setAttribute("tipoMessaggio", "successo");
                    }
                    response.sendRedirect(request.getContextPath() + "/catalogo");
                    break;

                case "update":
                    int prodottoIdUpdate = Integer.parseInt(request.getParameter("prodottoId"));
                    int nuovaQuantita = Integer.parseInt(request.getParameter("quantita"));
                    Strumento strumentoToUpdate = strumentoDAO.trovaPerId(prodottoIdUpdate);
                    if (strumentoToUpdate != null) {
                        carrello.aggiornaQuantita(strumentoToUpdate, nuovaQuantita);
                    }
                    response.sendRedirect(request.getContextPath() + "/carrello");
                    break;

                case "remove":
                    int prodottoIdRemove = Integer.parseInt(request.getParameter("prodottoId"));
                    carrello.rimuoviProdotto(prodottoIdRemove);
                    response.sendRedirect(request.getContextPath() + "/carrello");
                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/carrello");
        }
    }
}