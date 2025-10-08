package com.Strumentimusicali.Controller;

import com.Strumentimusicali.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CheckoutServlet", value = "/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utenteLoggato");

        // Controllo di sicurezza: L'utente deve esistere e non essere un admin
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if ("admin".equals(utente.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // Logica per navigare tra gli step
        String step = request.getParameter("step");
        if (step == null || step.isEmpty()) {
            step = "address"; // Step iniziale
        }

        request.setAttribute("step", step);
        request.getRequestDispatcher("/jsp/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utenteLoggato");

        // Controllo di sicurezza
        if (utente == null || "admin".equals(utente.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String step = request.getParameter("step");

        if ("address".equals(step)) {
            session.setAttribute("nomeSpedizione", request.getParameter("nome"));
            session.setAttribute("cognomeSpedizione", request.getParameter("cognome"));
            session.setAttribute("telefonoSpedizione", request.getParameter("telefono"));
            session.setAttribute("indirizzo", request.getParameter("indirizzo"));
            session.setAttribute("citta", request.getParameter("citta"));
            session.setAttribute("cap", request.getParameter("cap"));

            request.setAttribute("step", "payment");
            request.getRequestDispatcher("/jsp/checkout.jsp").forward(request, response);

        } else if ("payment".equals(step)) {
            String metodoPagamento = request.getParameter("metodoPagamento");
            session.setAttribute("metodoPagamento", metodoPagamento);

            if ("PayPal".equals(metodoPagamento)) {
                request.getRequestDispatcher("/jsp/paypal_attesa.jsp").forward(request, response);
            } else {
                request.setAttribute("step", "confirm");
                request.getRequestDispatcher("/jsp/checkout.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/jsp/carrello.jsp");
        }
    }
}