package com.Strumentimusicali.DAO.impl;

import com.Strumentimusicali.DAO.ConnectionManager;
import com.Strumentimusicali.DAO.UtenteDAO;
import com.Strumentimusicali.model.Utente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAOImpl implements UtenteDAO {

    @Override
    public Utente trovaPerId(int id) {
        String sql = "SELECT * FROM utenti WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return estraiUtenteDaResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utente trovaPerEmail(String email) {
        String sql = "SELECT * FROM utenti WHERE email = ?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return estraiUtenteDaResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Utente> trovaTutti() {
        String sql = "SELECT * FROM utenti ORDER BY id";
        List<Utente> listaUtenti = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listaUtenti.add(estraiUtenteDaResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaUtenti;
    }

    @Override
    public void salva(Utente utente) {
        String sql = "INSERT INTO utenti (nome, cognome, email, password, ruolo, bloccato) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getPassword());
            ps.setString(5, utente.getRuolo());
            ps.setBoolean(6, utente.isBloccato());
            ps.executeUpdate();
            try (ResultSet chiaviGenerate = ps.getGeneratedKeys()) {
                if (chiaviGenerate.next()) {
                    utente.setId(chiaviGenerate.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void aggiorna(Utente utente) {
        String sql = "UPDATE utenti SET nome = ?, cognome = ?, email = ?, password = ?, ruolo = ?, bloccato = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getPassword());
            ps.setString(5, utente.getRuolo());
            ps.setBoolean(6, utente.isBloccato());
            ps.setInt(7, utente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Utente estraiUtenteDaResultSet(ResultSet rs) throws SQLException {
        Utente utente = new Utente();
        utente.setId(rs.getInt("id"));
        utente.setNome(rs.getString("nome"));
        utente.setCognome(rs.getString("cognome"));
        utente.setEmail(rs.getString("email"));
        utente.setPassword(rs.getString("password"));
        utente.setRuolo(rs.getString("ruolo"));
        utente.setBloccato(rs.getBoolean("bloccato"));
        return utente;
    }
}