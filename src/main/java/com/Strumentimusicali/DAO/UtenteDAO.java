package com.Strumentimusicali.DAO;

import com.Strumentimusicali.model.Utente;
import java.util.List;

public interface UtenteDAO {
    Utente trovaPerId(int id);
    Utente trovaPerEmail(String email);
    List<Utente> trovaTutti();
    void salva(Utente utente);
    void aggiorna(Utente utente);
}