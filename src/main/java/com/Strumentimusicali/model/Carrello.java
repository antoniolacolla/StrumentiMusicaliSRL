package com.Strumentimusicali.model;

import java.util.HashMap;
import java.util.Map;

public class Carrello {
    private final Map<Strumento, Integer> prodotti;

    public Carrello() {
        this.prodotti = new HashMap<>();
    }

    public void aggiungiProdotto(Strumento strumento, int quantita) {
        // Se il prodotto è già nel carrello, somma la nuova quantità a quella esistente.
        // Grazie a equals() e hashCode(), questo funziona correttamente.
        this.prodotti.merge(strumento, quantita, Integer::sum);
    }

    public void rimuoviProdotto(int idProdotto) {
        // Rimuove il prodotto basandosi sull'ID
        this.prodotti.keySet().removeIf(strumento -> strumento.getId() == idProdotto);
    }

    public void aggiornaQuantita(Strumento strumento, int nuovaQuantita) {
        if (nuovaQuantita > 0) {
            // Sostituisce semplicemente la vecchia quantità con la nuova
            this.prodotti.put(strumento, nuovaQuantita);
        } else {
            // Se la quantità è 0 o meno, rimuove il prodotto
            rimuoviProdotto(strumento.getId());
        }
    }

    public Map<Strumento, Integer> getProdottiConQuantita() {
        return this.prodotti;
    }

    public double getTotale() {
        return prodotti.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrezzo() * entry.getValue())
                .sum();
    }

    public void svuota() {
        this.prodotti.clear();
    }
}