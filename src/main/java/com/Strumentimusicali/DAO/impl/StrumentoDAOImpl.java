package com.Strumentimusicali.DAO.impl;

import com.Strumentimusicali.DAO.ConnectionManager;
import com.Strumentimusicali.DAO.StrumentoDAO;
import com.Strumentimusicali.model.Strumento;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @Override
    public List<String> trovaTutteLeCategorie() {
        List<String> categorie = new ArrayList<>();
        String sql = "SELECT DISTINCT categoria FROM strumenti WHERE attivo = TRUE ORDER BY categoria";
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categorie.add(rs.getString("categoria"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorie;
    }

    @Override
    public List<String> trovaTuttiIMarchi() {
        List<String> marchi = new ArrayList<>();
        String sql = "SELECT DISTINCT marca FROM strumenti WHERE attivo = TRUE ORDER BY marca";
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                marchi.add(rs.getString("marca"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marchi;
    }

    @Override
    public int contaStrumenti(String[] categorie, String[] marche, String ricercaLibera) {
        List<Object> parametri = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM strumenti WHERE attivo = TRUE");
        appendFilters(sql, parametri, categorie, marche, ricercaLibera);

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Strumento> cercaStrumenti(String[] categorie, String[] marche, String ricercaLibera, String ordinamento, int pagina, int elementiPerPagina) {
        List<Strumento> strumenti = new ArrayList<>();
        List<Object> parametri = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM strumenti WHERE attivo = TRUE");
        appendFilters(sql, parametri, categorie, marche, ricercaLibera);

        if ("prezzo_asc".equals(ordinamento)) {
            sql.append(" ORDER BY prezzo ASC");
        } else if ("prezzo_desc".equals(ordinamento)) {
            sql.append(" ORDER BY prezzo DESC");
        } else {
            sql.append(" ORDER BY id");
        }

        sql.append(" LIMIT ? OFFSET ?");
        parametri.add(elementiPerPagina);
        parametri.add((pagina - 1) * elementiPerPagina);

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    strumenti.add(extractStrumentoFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strumenti;
    }

    @Override
    public int contaTuttiStrumenti() {
        String sql = "SELECT COUNT(*) FROM strumenti";
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Strumento> trovaTuttiPaginati(int pagina, int elementiPerPagina) {
        List<Strumento> strumenti = new ArrayList<>();
        String sql = "SELECT * FROM strumenti ORDER BY id LIMIT ? OFFSET ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, elementiPerPagina);
            ps.setInt(2, (pagina - 1) * elementiPerPagina);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    strumenti.add(extractStrumentoFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strumenti;
    }

    private void appendFilters(StringBuilder sql, List<Object> parametri, String[] categorie, String[] marche, String ricercaLibera) {
        if (categorie != null && categorie.length > 0) {
            sql.append(" AND categoria IN (").append(String.join(",", Collections.nCopies(categorie.length, "?"))).append(")");
            parametri.addAll(Arrays.asList(categorie));
        }
        if (marche != null && marche.length > 0) {
            sql.append(" AND marca IN (").append(String.join(",", Collections.nCopies(marche.length, "?"))).append(")");
            parametri.addAll(Arrays.asList(marche));
        }
        if (ricercaLibera != null && !ricercaLibera.isEmpty()) {
            sql.append(" AND (nome LIKE ? OR descrizione LIKE ? OR marca LIKE ?)");
            String searchTerm = "%" + ricercaLibera + "%";
            parametri.add(searchTerm);
            parametri.add(searchTerm);
            parametri.add(searchTerm);
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