package com.Strumentimusicali.DAO;

import com.Strumentimusicali.model.Strumento;
import java.util.List;

public interface StrumentoDAO {
    Strumento trovaPerId(int id);
    List<Strumento> trovaTutti();
    List<Strumento> trovaStrumentiInVetrina();
    void salva(Strumento strumento);
    void aggiorna(Strumento strumento);
    void aggiornaQuantita(int idProdotto, int quantitaAcquistata);
    List<Strumento> cercaStrumenti(String[] categorie, String[] marche, String ricercaLibera, String ordinamento, int pagina, int elementiPerPagina);
    int contaStrumenti(String[] categorie, String[] marche, String ricercaLibera);
    List<String> trovaTutteLeCategorie();
    List<String> trovaTuttiIMarchi();

    // NUOVI METODI PER LA PAGINAZIONE ADMIN
    List<Strumento> trovaTuttiPaginati(int pagina, int elementiPerPagina);
    int contaTuttiStrumenti();
}