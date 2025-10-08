package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.OrdineDAO;
import com.Strumentimusicali.DAO.impl.OrdineDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminOrdiniServlet", value = "/admin/ordini")
public class AdminOrdiniServlet extends HttpServlet {
    private OrdineDAO ordineDAO;

    @Override
    public void init() {
        this.ordineDAO = new OrdineDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("listaOrdini", ordineDAO.trovaTutti());
        request.getRequestDispatcher("/jsp/admin_ordini.jsp").forward(request, response);
    }
}