package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.DAO.impl.StrumentoDAOImpl;
import com.Strumentimusicali.model.Carrello;
import com.Strumentimusicali.model.Strumento;
import com.Strumentimusicali.model.Utente;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        String action = request.getParameter("action");
        int idProdotto = Integer.parseInt(request.getParameter("prodottoId"));

        // CORREZIONE: Usa il metodo in italiano
        Strumento strumento = strumentoDAO.trovaPerId(idProdotto);

        if (strumento != null) {
            switch (action) {
                case "add":
                    int quantita = 1;
                    String quantitaParam = request.getParameter("quantita");
                    if (quantitaParam != null && !quantitaParam.isEmpty()) {
                        quantita = Integer.parseInt(quantitaParam);
                    }
                    carrello.aggiungiProdotto(strumento, quantita);
                    break;
                case "remove":
                    carrello.rimuoviProdotto(idProdotto);
                    break;
                case "update":
                    int nuovaQuantita = Integer.parseInt(request.getParameter("quantita"));
                    carrello.aggiornaQuantita(strumento, nuovaQuantita);
                    break;
            }
        }
        response.sendRedirect(request.getContextPath() + "/jsp/carrello.jsp");
    }
}