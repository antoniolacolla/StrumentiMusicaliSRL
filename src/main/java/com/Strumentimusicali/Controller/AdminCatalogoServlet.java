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
    private static final int ELEMENTI_PER_PAGINA = 10;

    @Override
    public void init() {
        this.strumentoDAO = new StrumentoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int paginaAttuale = 1;
        String paginaParam = request.getParameter("pagina");
        if (paginaParam != null && paginaParam.matches("\\d+")) {
            paginaAttuale = Integer.parseInt(paginaParam);
        }

        request.setAttribute("listaProdotti", strumentoDAO.trovaTuttiPaginati(paginaAttuale, ELEMENTI_PER_PAGINA));
        int totaleProdotti = strumentoDAO.contaTuttiStrumenti();
        int numeroPagine = (int) Math.ceil((double) totaleProdotti / ELEMENTI_PER_PAGINA);

        request.setAttribute("numeroPagine", numeroPagine);
        request.setAttribute("paginaAttuale", paginaAttuale);

        request.getRequestDispatcher("/jsp/admin_catalogo.jsp").forward(request, response);
    }
}