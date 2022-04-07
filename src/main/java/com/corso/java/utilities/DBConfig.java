package com.corso.java.utilities;

import java.io.IOException;
import java.sql.*;


public class DBConfig {

    public static LOG L = LOG.getInstance();
    public ReadProperties rp = new ReadProperties();
    public Statement statement = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    public Connection conn = null;

    /**
     * Constructor
     */
    public DBConfig(){}

    /**
     * @return la connessione
     * @throws IOException
     * @method startConnection
     * Inizializza una nuova connessione leggendo i parametri dal file 'application.properties'
     */
    public Connection startConnection() throws IOException {
        Connection conn = null;
        rp.read("application.properties");
        try {
            Class.forName(rp.getProperties().getProperty("db.mysql.url")).newInstance();
            conn = DriverManager.getConnection(rp.getProperties().getProperty("db.url"), rp.getProperties().getProperty("db.username"), rp.getProperties().getProperty("db.password"));
            L.info("Connection with database established");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * @method printer
     * stampa tutti i dati contenuti nel ResulSet passato come parametro
     * @param rs
     */
    public void printer(ResultSet rs) {
        try {
            ResultSetMetaData md = rs.getMetaData();

            while (rs.next()) {
                System.out.println("");
                for (int i = 1; i <= md.getColumnCount(); i++)
                    L.info(rs.getString(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeAll() {
        try {
            if(statement != null) statement.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}