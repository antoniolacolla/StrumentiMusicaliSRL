package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.OrdineDAO;
import com.Strumentimusicali.DAO.UtenteDAO;
import com.Strumentimusicali.DAO.impl.OrdineDAOImpl;
import com.Strumentimusicali.DAO.impl.UtenteDAOImpl;
import com.Strumentimusicali.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminDashboardServlet", value = "/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private UtenteDAO utenteDAO;
    private OrdineDAO ordineDAO;

    @Override
    public void init() {
        this.utenteDAO = new UtenteDAOImpl();
        this.ordineDAO = new OrdineDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CORREZIONE: Usa i nuovi nomi dei metodi in italiano
        List<Utente> listaUtenti = utenteDAO.trovaTutti();

        Map<Integer, Integer> conteggioOrdini = new HashMap<>();
        for (Utente utente : listaUtenti) {
            // CORREZIONE: Usa i nuovi nomi dei metodi in italiano
            conteggioOrdini.put(utente.getId(), ordineDAO.trovaPerIdUtente(utente.getId()).size());
        }

        request.setAttribute("listaUtenti", listaUtenti);
        request.setAttribute("conteggioOrdini", conteggioOrdini);
        request.getRequestDispatcher("/jsp/admin_dashboard.jsp").forward(request, response);
    }
}