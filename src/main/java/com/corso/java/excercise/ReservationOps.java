package com.corso.java.excercise;

import com.corso.java.utilities.DBConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ReservationOps extends DBConfig {

    /**
     * Creates reservation table
     */
    public void create() {
        try {
            conn = this.startConnection();

            rp.read("operation.properties");
            String q = rp.getProperties().getProperty("createR");

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
     * Inserts a reservation in the table
     * @param r - reservation to insert
     * @throws IOException
     * @throws SQLException
     */
    public void insert(Reservation r) throws IOException, SQLException {

        Connection connection = this.startConnection(); //dalla utils DB

        rp.read("operation.properties");

        ps = connection.prepareStatement(rp.getProperties().getProperty("insertR"));

        java.sql.Date sqlDate = new java.sql.Date(r.getDate().getTime());

        ps.setString(1, r.getLastName());
        ps.setDate(2, sqlDate);
        ps.setInt(3, r.getNumberOfPeople());
        ps.setString(4, r.getCellphoneNumber());
        ps.setInt(5, r.getTableNumber());


        if(ps.executeUpdate() != 0)
            L.info("Aggiunto");
        else
            L.info("Non aggiunto");

        ps.clearParameters();
    }

    /**
     * Updates a certain reservation of the table
     * @param r
     * @throws IOException
     * @throws SQLException
     */
    public void update(Reservation r) throws IOException, SQLException {

        conn = this.startConnection();
        rp.read("operation.properties");

        ps = conn.prepareStatement(rp.getProperties().getProperty("updateR"));


        ps.setString(5, r.getLastName());
        ps.setDate(1, (Date) r.getDate());
        ps.setInt(2, r.getNumberOfPeople());
        ps.setString(3, r.getCellphoneNumber());
        ps.setInt(4, r.getTableNumber());


        if(ps.executeUpdate() != 0)
            L.info("Updated");
        else
            L.info("Not Updated");

        ps.clearParameters();
    }

    /**
     * Deletes a reservation found by last name
     * @throws IOException
     * @throws SQLException
     */
    public void delete() throws IOException, SQLException {

        Scanner sc = new Scanner(System.in);


        conn = this.startConnection();
        rp.read("operation.properties");

        ps = conn.prepareStatement(rp.getProperties().getProperty("deleteR"));

        System.out.println("Insert last name to delete the reservation: ");
        ps.setString(1, sc.nextLine());

        if(ps.executeUpdate() != 0)
            L.info("Deleted ");
        else
            L.info("Not Deleted");

        ps.clearParameters();

        this.closeAll();
    }

    /**
     * Shows all the reservation
     */
    public void showAll(){
        try {
            conn = this.startConnection();

            statement = conn.createStatement();

            rs = statement.executeQuery(rp.getProperties().getProperty("selectR"));

            this.printer(rs);
            rs.close();
        } catch (IOException | SQLException e) {
            e.getMessage();
        } finally {
            this.closeAll();
        }

    }

}
