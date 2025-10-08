package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.UtenteDAO;
import com.Strumentimusicali.DAO.impl.UtenteDAOImpl;
import com.Strumentimusicali.Util.PasswordUtil; // Assicurati che l'import sia presente
import com.Strumentimusicali.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "RegistrazioneServlet", value = "/registrazione")
public class RegistrazioneServlet extends HttpServlet {
    private UtenteDAO utenteDAO;

    @Override
    public void init() {
        this.utenteDAO = new UtenteDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/registrazione.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Controlla se l'email è già in uso
        if (utenteDAO.trovaPerEmail(email) != null) {
            request.setAttribute("errore", "Questa email è già registrata. Prova ad accedere.");
            request.getRequestDispatcher("/jsp/registrazione.jsp").forward(request, response);
            return;
        }

        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setEmail(email);

        // 1. Cripta (hash) la password prima di salvarla
        String hashedPassword = PasswordUtil.hashPassword(password);
        nuovoUtente.setPassword(hashedPassword);

        nuovoUtente.setRuolo("cliente");
        nuovoUtente.setBloccato(false);

        // 2. Salva l'utente con la password criptata
        utenteDAO.salva(nuovoUtente);

        // Effettua il login automatico e reindirizza alla home
        HttpSession session = request.getSession();
        session.setAttribute("utenteLoggato", nuovoUtente);
        response.sendRedirect(request.getContextPath() + "/");
    }
}