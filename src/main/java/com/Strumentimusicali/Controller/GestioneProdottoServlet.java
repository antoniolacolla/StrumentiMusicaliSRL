package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.DAO.impl.StrumentoDAOImpl;
import com.Strumentimusicali.model.Strumento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "GestioneProdottoServlet", value = "/admin/gestione-prodotto")
public class GestioneProdottoServlet extends HttpServlet {
    private StrumentoDAO strumentoDAO;

    @Override
    public void init() {
        this.strumentoDAO = new StrumentoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) { // Modalità modifica
            int id = Integer.parseInt(idParam);
            Strumento strumento = strumentoDAO.trovaPerId(id);
            request.setAttribute("prodotto", strumento);
        }
        // Se idParam è null, è la modalità aggiunta e non si passa nessun prodotto
        request.getRequestDispatcher("/jsp/form_prodotto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Strumento strumento = new Strumento();
        strumento.setNome(request.getParameter("nome"));
        strumento.setMarca(request.getParameter("marca"));
        strumento.setCategoria(request.getParameter("categoria"));
        strumento.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
        strumento.setQuantitaDisponibile(Integer.parseInt(request.getParameter("quantita")));
        strumento.setDescrizione(request.getParameter("descrizione"));
        strumento.setUrlImmagine(request.getParameter("url_immagine"));
        strumento.setAttivo(request.getParameter("attivo") != null);
        strumento.setInVetrina(request.getParameter("in_vetrina") != null);

        String idParam = request.getParameter("id");
        HttpSession session = request.getSession();

        if (idParam != null && !idParam.isEmpty()) { // Aggiornamento
            strumento.setId(Integer.parseInt(idParam));
            strumentoDAO.aggiorna(strumento);
            session.setAttribute("messaggio", "Prodotto aggiornato con successo!");
        } else { // Inserimento
            strumentoDAO.salva(strumento);
            session.setAttribute("messaggio", "Nuovo prodotto aggiunto con successo!");
        }

        session.setAttribute("tipoMessaggio", "successo");
        response.sendRedirect(request.getContextPath() + "/admin/catalogo");
    }
}