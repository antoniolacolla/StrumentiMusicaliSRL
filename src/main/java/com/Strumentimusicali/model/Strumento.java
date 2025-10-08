package com.Strumentimusicali.model;

import java.util.Objects;

public class Strumento {
    private int id;
    private String nome;
    private String marca;
    private String categoria;
    private String descrizione;
    private double prezzo;
    private int quantitaDisponibile;
    private String urlImmagine;
    private boolean inVetrina;
    private boolean attivo;

    // Getters e Setters... (invariati)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
    public int getQuantitaDisponibile() { return quantitaDisponibile; }
    public void setQuantitaDisponibile(int quantitaDisponibile) { this.quantitaDisponibile = quantitaDisponibile; }
    public String getUrlImmagine() { return urlImmagine; }
    public void setUrlImmagine(String urlImmagine) { this.urlImmagine = urlImmagine; }
    public boolean isInVetrina() { return inVetrina; }
    public void setInVetrina(boolean inVetrina) { this.inVetrina = inVetrina; }
    public boolean isAttivo() { return attivo; }
    public void setAttivo(boolean attivo) { this.attivo = attivo; }

    // --- NUOVI METODI FONDAMENTALI ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Strumento strumento = (Strumento) o;
        return id == strumento.id; // Due strumenti sono uguali se hanno lo stesso ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // L'hashCode si basa solo sull'ID
    }
}