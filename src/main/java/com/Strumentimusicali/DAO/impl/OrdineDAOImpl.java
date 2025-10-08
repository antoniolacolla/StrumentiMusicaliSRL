package com.Strumentimusicali.DAO.impl;

import com.Strumentimusicali.DAO.ConnectionManager;
import com.Strumentimusicali.DAO.OrdineDAO;
import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.model.Ordine;
import com.Strumentimusicali.model.Strumento;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdineDAOImpl implements OrdineDAO {

    @Override
    public void salva(Ordine ordine) {
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false); // Inizio transazione

            String sqlOrdine = "INSERT INTO ordini (id_utente, data, stato, indirizzo, citta, cap, metodo_pagamento) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psOrdine = conn.prepareStatement(sqlOrdine, Statement.RETURN_GENERATED_KEYS)) {
                psOrdine.setInt(1, ordine.getIdUtente());
                psOrdine.setDate(2, Date.valueOf(ordine.getData()));
                psOrdine.setString(3, ordine.getStato());
                psOrdine.setString(4, ordine.getIndirizzo());
                psOrdine.setString(5, ordine.getCitta());
                psOrdine.setString(6, ordine.getCap());
                psOrdine.setString(7, ordine.getMetodoPagamento());
                psOrdine.executeUpdate();

                ResultSet chiaviGenerate = psOrdine.getGeneratedKeys();
                if (chiaviGenerate.next()) {
                    ordine.setId(chiaviGenerate.getLong(1));
                } else {
                    throw new SQLException("Creazione ordine fallita, nessun ID ottenuto.");
                }
            }

            String sqlDettagli = "INSERT INTO dettagli_ordine (id_ordine, id_strumento, quantita, prezzo_al_momento_acquisto) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psDettagli = conn.prepareStatement(sqlDettagli)) {
                for (Map.Entry<Strumento, Integer> riga : ordine.getProdottiAcquistati().entrySet()) {
                    psDettagli.setLong(1, ordine.getId());
                    psDettagli.setInt(2, riga.getKey().getId());
                    psDettagli.setInt(3, riga.getValue());
                    psDettagli.setDouble(4, riga.getKey().getPrezzo());
                    psDettagli.addBatch();
                }
                psDettagli.executeBatch();
            }

            conn.commit(); // Conferma transazione

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public Ordine trovaPerId(long idOrdine) {
        String sql = "SELECT * FROM ordini WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idOrdine);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return estraiOrdineDaResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ordine> trovaPerIdUtente(int idUtente) {
        String sql = "SELECT * FROM ordini WHERE id_utente = ? ORDER BY data DESC";
        List<Ordine> listaOrdini = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaOrdini.add(estraiOrdineDaResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaOrdini;
    }

    @Override
    public List<Ordine> trovaTutti() {
        String sql = "SELECT * FROM ordini ORDER BY data DESC";
        List<Ordine> listaOrdini = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listaOrdini.add(estraiOrdineDaResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaOrdini;
    }

    @Override
    public void aggiorna(Ordine ordine) {
        String sql = "UPDATE ordini SET stato = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ordine.getStato());
            ps.setLong(2, ordine.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Ordine estraiOrdineDaResultSet(ResultSet rs) throws SQLException {
        Ordine ordine = new Ordine();
        ordine.setId(rs.getLong("id"));
        ordine.setIdUtente(rs.getInt("id_utente"));
        ordine.setData(rs.getDate("data").toLocalDate());
        ordine.setStato(rs.getString("stato"));
        ordine.setIndirizzo(rs.getString("indirizzo"));
        ordine.setCitta(rs.getString("citta"));
        ordine.setCap(rs.getString("cap"));
        ordine.setMetodoPagamento(rs.getString("metodo_pagamento"));

        Map<Strumento, Integer> prodotti = trovaProdottiPerIdOrdine(ordine.getId());
        ordine.setProdottiAcquistati(prodotti);

        double totale = 0;
        for(Map.Entry<Strumento, Integer> riga : prodotti.entrySet()){
            totale += riga.getKey().getPrezzo() * riga.getValue();
        }
        ordine.setTotale(totale);

        return ordine;
    }

    private Map<Strumento, Integer> trovaProdottiPerIdOrdine(long idOrdine) {
        Map<Strumento, Integer> prodotti = new HashMap<>();
        String sql = "SELECT s.*, do.quantita, do.prezzo_al_momento_acquisto " +
                "FROM dettagli_ordine do JOIN strumenti s ON do.id_strumento = s.id " +
                "WHERE do.id_ordine = ?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idOrdine);
            ResultSet rs = ps.executeQuery();
            StrumentoDAOImpl strumentoDAOImpl = new StrumentoDAOImpl();
            while (rs.next()) {
                Strumento strumento = strumentoDAOImpl.extractStrumentoFromResultSet(rs);
                strumento.setPrezzo(rs.getDouble("prezzo_al_momento_acquisto"));
                int quantita = rs.getInt("quantita");
                prodotti.put(strumento, quantita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prodotti;
    }
}