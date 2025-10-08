package com.Strumentimusicali.Controller;

import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.DAO.impl.StrumentoDAOImpl;
import com.Strumentimusicali.model.Strumento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet(name = "GestioneProdottoServlet", value = "/admin/gestione-prodotto")
@MultipartConfig // Abilita la gestione di upload di file
public class GestioneProdottoServlet extends HttpServlet {
    private StrumentoDAO strumentoDAO;

    @Override
    public void init() {
        this.strumentoDAO = new StrumentoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String azione = request.getParameter("action");

        // Se l'azione è "modifica", carica i dati del prodotto esistente
        if ("modifica".equals(azione)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Strumento strumento = strumentoDAO.trovaPerId(id);
            request.setAttribute("strumento", strumento);
        }

        // Inoltra alla pagina del form (vuoto per un nuovo prodotto, pieno per una modifica)
        request.getRequestDispatcher("/jsp/form_prodotto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        boolean isEditing = idParam != null && !idParam.isEmpty();
        Strumento strumento;

        if (isEditing) {
            strumento = strumentoDAO.trovaPerId(Integer.parseInt(idParam));
        } else {
            strumento = new Strumento();
        }

        // Gestione dell'upload dell'immagine
        Part filePart = request.getPart("immagineFile");
        String nomeFile = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if (nomeFile != null && !nomeFile.isEmpty()) {
            String percorsoUpload = getServletContext().getRealPath("") + "images";
            File dirUpload = new File(percorsoUpload);
            if (!dirUpload.exists()) {
                dirUpload.mkdir();
            }
            String nomeFileUnico = UUID.randomUUID().toString() + "_" + nomeFile;
            filePart.write(percorsoUpload + File.separator + nomeFileUnico);
            strumento.setUrlImmagine("images/" + nomeFileUnico);
        }

        // Popola l'oggetto Strumento con i dati dal form
        strumento.setNome(request.getParameter("nome"));
        strumento.setMarca(request.getParameter("marca"));
        strumento.setCategoria(request.getParameter("categoria"));
        strumento.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
        strumento.setQuantitaDisponibile(Integer.parseInt(request.getParameter("quantita")));
        strumento.setDescrizione(request.getParameter("descrizione"));
        strumento.setInVetrina(request.getParameter("inVetrina") != null);
        strumento.setAttivo(request.getParameter("attivo") != null);

        // Salva o aggiorna il prodotto nel database
        if (isEditing) {
            strumentoDAO.aggiorna(strumento);
        } else {
            strumentoDAO.salva(strumento);
        }

        // Reindirizza alla pagina del catalogo admin
        response.sendRedirect(request.getContextPath() + "/admin/catalogo");
    }
}