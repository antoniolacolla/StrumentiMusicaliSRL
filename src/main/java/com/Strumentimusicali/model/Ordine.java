package com.Strumentimusicali.model;

import java.time.LocalDate;
import java.util.Map;

public class Ordine {
    private long id;
    private int idUtente;
    private LocalDate data;
    private String stato; // "In elaborazione", "Spedito", "Consegnato"
    private double totale;
    private Map<Strumento, Integer> prodottiAcquistati;
    private String indirizzo;
    private String citta;
    private String cap;
    private String metodoPagamento;

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
    public double getTotale() { return totale; }
    public void setTotale(double totale) { this.totale = totale; }
    public Map<Strumento, Integer> getProdottiAcquistati() { return prodottiAcquistati; }
    public void setProdottiAcquistati(Map<Strumento, Integer> prodottiAcquistati) { this.prodottiAcquistati = prodottiAcquistati; }
    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }
    public String getCap() { return cap; }
    public void setCap(String cap) { this.cap = cap; }
    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
}