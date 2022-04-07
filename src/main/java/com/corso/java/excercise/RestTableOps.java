package com.corso.java.excercise;

import com.corso.java.utilities.DBConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class RestTableOps extends DBConfig {

    /**
     * Creates a restaurant table on MySql
     */
    public void create() {
        try {
            conn = this.startConnection();

            rp.read("operation.properties");
            String q = rp.getProperties().getProperty("createT");

            statement = conn.createStatement();
            statement.executeUpdate(q);
            L.info("table made");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            this.closeAll();
        }
    }

    /**
     * Inserts a certain restaurant table t in the MySQL Workbench table
     * @param t
     * @throws IOException
     * @throws SQLException
     */
    public void insert(RestTable t) throws IOException, SQLException {

        Connection connection = this.startConnection(); //dalla utils DB

        rp.read("operation.properties");

        ps = connection.prepareStatement(rp.getProperties().getProperty("insertT"));


        ps.setInt(1, t.getNumber());
        ps.setInt(2, t.getCapacity());


        if(ps.executeUpdate() != 0)
            L.info("Aggiunto");
        else
            L.info("Non aggiunto");

        ps.clearParameters();
    }

    /**
     * Updates a certain restaurant table in the MySQL table
     * @param o
     * @throws IOException
     * @throws SQLException
     */
    public void update(RestTable t) throws IOException, SQLException {

        conn = this.startConnection();
        rp.read("operation.properties");

        ps = conn.prepareStatement(rp.getProperties().getProperty("updateT"));


        ps.setInt(2, t.getNumber());
        ps.setInt(1, t.getCapacity());


        if(ps.executeUpdate() != 0)
            L.info("Updated");
        else
            L.info("Not Updated");

        ps.clearParameters();
    }

    /**
     * Deletes a table by it's identification number
     * @throws IOException
     * @throws SQLException
     */
    public void delete() throws IOException, SQLException {

        Scanner sc = new Scanner(System.in);

        conn = this.startConnection();
        rp.read("operation.properties");

        ps = conn.prepareStatement(rp.getProperties().getProperty("deleteT"));

        System.out.println("Insert table number to delete: ");
        ps.setInt(1, sc.nextInt());

        if(ps.executeUpdate() != 0)
            L.info("Deleted ");
        else
            L.info("Not Deleted");

        ps.clearParameters();

        this.closeAll();
    }

    /**
     * Shows all tables
     */
    public void showAll(){
        try {
            conn = this.startConnection();

            statement = conn.createStatement();

            rs = statement.executeQuery(rp.getProperties().getProperty("selectT"));

            this.printer(rs);
            rs.close();
        } catch (IOException | SQLException e) {
            e.getMessage();
        } finally {
            this.closeAll();
        }

    }
}
