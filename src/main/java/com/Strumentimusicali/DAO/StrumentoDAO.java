package com.Strumentimusicali.DAO;

import com.Strumentimusicali.model.Strumento;
import java.util.List;

public interface StrumentoDAO {
    Strumento trovaPerId(int id);
    List<Strumento> trovaStrumentiPubblici();
    List<Strumento> trovaTutti();
    List<Strumento> trovaStrumentiInVetrina();
    void salva(Strumento strumento);
    void aggiorna(Strumento strumento);
    void aggiornaQuantita(int idProdotto, int quantitaAcquistata);
}