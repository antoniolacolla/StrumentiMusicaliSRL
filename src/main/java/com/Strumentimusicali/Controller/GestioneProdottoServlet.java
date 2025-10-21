package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.DAO.impl.StrumentoDAOImpl;
import com.Strumentimusicali.model.Strumento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig; // IMPORTANTE: Aggiungi questa
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part; // IMPORTANTE: Aggiungi questa

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

// AGGIUNGI @MultipartConfig per abilitare l'upload di file
@WebServlet(name = "GestioneProdottoServlet", value = "/admin/gestione-prodotto")
@MultipartConfig
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
        strumento.setAttivo(request.getParameter("attivo") != null);
        strumento.setInVetrina(request.getParameter("in_vetrina") != null);

        String idParam = request.getParameter("id");
        HttpSession session = request.getSession();

        // --- NUOVA GESTIONE UPLOAD IMMAGINE ---

        Part filePart = request.getPart("immagineFile"); // Recupera il file dal form
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Nome del file
        String urlImmagineDaSalvare = "";

        if (fileName != null && !fileName.isEmpty()) {
            // Un nuovo file è stato caricato

            // 1. Definisci dove salvare il file sul server
            // Questo salva nella cartella /images della tua webapp *in esecuzione*
            String uploadPath = getServletContext().getRealPath("/images");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 2. Salva il file
            filePart.write(uploadPath + File.separator + fileName);

            // 3. Questo è il percorso che salveremo nel database
            urlImmagineDaSalvare = "images/" + fileName;

        } else {
            // Nessun nuovo file caricato, riutilizza quello vecchio (se esiste)
            urlImmagineDaSalvare = request.getParameter("urlImmagineEsistente");
        }

        strumento.setUrlImmagine(urlImmagineDaSalvare);

        // --- FINE GESTIONE IMMAGINE ---

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