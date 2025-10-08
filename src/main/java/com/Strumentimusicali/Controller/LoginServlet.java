package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.UtenteDAO;
import com.Strumentimusicali.DAO.impl.UtenteDAOImpl;
import com.Strumentimusicali.Util.PasswordUtil;
import com.Strumentimusicali.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private UtenteDAO utenteDAO;

    @Override
    public void init() {
        this.utenteDAO = new UtenteDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 1. Cerca l'utente nel database tramite email
        Utente utente = utenteDAO.trovaPerEmail(email);

        // 2. Calcola l'hash della password inserita nel form
        String hashedPasswordAttempt = PasswordUtil.hashPassword(password);

        // 3. Confronta l'hash calcolato con quello salvato nel database
        if (utente != null && utente.getPassword().equals(hashedPasswordAttempt)) {

            // Controlla se l'utente è bloccato
            if (utente.isBloccato()) {
                request.setAttribute("errore", "Il tuo account è stato bloccato.");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            } else {
                // Se le credenziali sono corrette e l'utente non è bloccato, crea la sessione
                HttpSession session = request.getSession();
                session.setAttribute("utenteLoggato", utente);
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else {
            // Se le credenziali non sono valide, mostra un errore
            request.setAttribute("errore", "Credenziali non valide. Riprova.");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }
}