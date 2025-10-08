package com.Strumentimusicali.model;

import java.util.HashMap;
import java.util.Map;

public class Carrello {
    private final Map<Strumento, Integer> prodottiConQuantita = new HashMap<>();

    public void aggiungiProdotto(Strumento strumento, int quantita) {
        // NUOVA LOGICA: Se il prodotto è già nel carrello, somma la nuova quantità a quella esistente.
        prodottiConQuantita.merge(strumento, quantita, Integer::sum);
    }

    public void rimuoviProdotto(int prodottoId) {
        prodottiConQuantita.keySet().removeIf(strumento -> strumento.getId() == prodottoId);
    }

    // NUOVO METODO: Per aggiornare la quantità di un prodotto a un valore specifico.
    public void aggiornaQuantita(Strumento strumento, int nuovaQuantita) {
        if (nuovaQuantita > 0) {
            prodottiConQuantita.put(strumento, nuovaQuantita);
        } else {
            // Se la quantità è 0 o meno, rimuovi il prodotto.
            rimuoviProdotto(strumento.getId());
        }
    }

    public void svuota() {
        prodottiConQuantita.clear();
    }

    public Map<Strumento, Integer> getProdottiConQuantita() {
        return prodottiConQuantita;
    }

    public double getTotale() {
        return prodottiConQuantita.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrezzo() * entry.getValue())
                .sum();
    }
}