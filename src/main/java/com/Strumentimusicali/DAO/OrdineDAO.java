package com.Strumentimusicali.DAO;

import com.Strumentimusicali.model.Ordine;
import java.util.List;

public interface OrdineDAO {
    void salva(Ordine ordine);
    Ordine trovaPerId(long idOrdine);
    List<Ordine> trovaPerIdUtente(int idUtente);
    List<Ordine> trovaTutti();
    void aggiorna(Ordine ordine);
}