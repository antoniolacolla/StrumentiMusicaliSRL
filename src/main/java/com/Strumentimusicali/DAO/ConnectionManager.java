package com.Strumentimusicali.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/strumenti_musicali_srl";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "antolac2002"; // <-- INSERISCI QUI LA TUA PASSWORD!

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL non trovato!", e);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}