package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.DAO.impl.StrumentoDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminCatalogoServlet", value = "/admin/catalogo")
public class AdminCatalogoServlet extends HttpServlet {
    private StrumentoDAO strumentoDAO;

    @Override
    public void init() {
        this.strumentoDAO = new StrumentoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // --- CORREZIONE QUI ---
        // Usa il nuovo nome del metodo in italiano
        request.setAttribute("listaProdotti", strumentoDAO.trovaTutti());
        // ---------------------

        request.getRequestDispatcher("/jsp/admin_catalogo.jsp").forward(request, response);
    }
}