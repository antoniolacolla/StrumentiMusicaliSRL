package com.Strumentimusicali.DAO.impl;

import com.Strumentimusicali.DAO.ConnectionManager;
import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.model.Strumento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StrumentoDAOImpl implements StrumentoDAO {

    @Override
    public Strumento trovaPerId(int id) {
        String sql = "SELECT * FROM strumenti WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractStrumentoFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Strumento> trovaTutti() {
        String sql = "SELECT * FROM strumenti ORDER BY id";
        List<Strumento> strumenti = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                strumenti.add(extractStrumentoFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strumenti;
    }

    @Override
    public List<Strumento> trovaStrumentiPubblici() {
        String sql = "SELECT * FROM strumenti WHERE attivo = TRUE ORDER BY id";
        List<Strumento> strumenti = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                strumenti.add(extractStrumentoFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strumenti;
    }

    @Override
    public List<Strumento> trovaStrumentiInVetrina() {
        String sql = "SELECT * FROM strumenti WHERE attivo = TRUE AND in_vetrina = TRUE ORDER BY id";
        List<Strumento> strumenti = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                strumenti.add(extractStrumentoFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strumenti;
    }

    @Override
    public void salva(Strumento strumento) {
        String sql = "INSERT INTO strumenti (nome, marca, categoria, prezzo, descrizione, url_immagine, in_vetrina, quantita_disponibile, attivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, strumento.getNome());
            ps.setString(2, strumento.getMarca());
            ps.setString(3, strumento.getCategoria());
            ps.setDouble(4, strumento.getPrezzo());
            ps.setString(5, strumento.getDescrizione());
            ps.setString(6, strumento.getUrlImmagine());
            ps.setBoolean(7, strumento.isInVetrina());
            ps.setInt(8, strumento.getQuantitaDisponibile());
            ps.setBoolean(9, strumento.isAttivo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void aggiorna(Strumento strumento) {
        String sql = "UPDATE strumenti SET nome = ?, marca = ?, categoria = ?, prezzo = ?, descrizione = ?, url_immagine = ?, in_vetrina = ?, quantita_disponibile = ?, attivo = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, strumento.getNome());
            ps.setString(2, strumento.getMarca());
            ps.setString(3, strumento.getCategoria());
            ps.setDouble(4, strumento.getPrezzo());
            ps.setString(5, strumento.getDescrizione());
            ps.setString(6, strumento.getUrlImmagine());
            ps.setBoolean(7, strumento.isInVetrina());
            ps.setInt(8, strumento.getQuantitaDisponibile());
            ps.setBoolean(9, strumento.isAttivo());
            ps.setInt(10, strumento.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- METODO MANCANTE AGGIUNTO QUI ---
    @Override
    public void aggiornaQuantita(int idProdotto, int quantitaAcquistata) {
        String sql = "UPDATE strumenti SET quantita_disponibile = quantita_disponibile - ? WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantitaAcquistata);
            ps.setInt(2, idProdotto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Strumento extractStrumentoFromResultSet(ResultSet rs) throws SQLException {
        Strumento s = new Strumento();
        s.setId(rs.getInt("id"));
        s.setNome(rs.getString("nome"));
        s.setMarca(rs.getString("marca"));
        s.setCategoria(rs.getString("categoria"));
        s.setDescrizione(rs.getString("descrizione"));
        s.setPrezzo(rs.getDouble("prezzo"));
        s.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
        s.setUrlImmagine(rs.getString("url_immagine"));
        s.setInVetrina(rs.getBoolean("in_vetrina"));
        s.setAttivo(rs.getBoolean("attivo"));
        return s;
    }
}