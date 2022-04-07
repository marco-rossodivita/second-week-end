package com.corso.java.excercise;

import com.corso.java.utilities.DBConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Operations extends DBConfig{


    RestTableOps rt = new RestTableOps();
    RestTable r = new RestTable();
    ReservationOps ro = new ReservationOps();
    Reservation res = new Reservation();


    /**
     * Creates a schema on MySQL Workbench
     */
    public void createSchema(){
        try {
            conn = this.startConnection();

            rp.read("operation.properties");
            String q = rp.getProperties().getProperty("createSchema");

            statement = conn.createStatement();
            statement.executeUpdate(q);
            L.info("Schema made");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            this.closeAll();
        }
    }

    /**
     * Method that requests reservation, considering the table availability
     * @param lastName - lastname of reservation
     * @param date - date of reservation
     * @param numberOfPeople - number of people considered in the reservation
     * @param cellphoneNumber - cellphone number of reservation
     * @return
     */
    public boolean requestReservation(String lastName, Date date, int numberOfPeople, String cellphoneNumber) throws SQLException, IOException, ParseException {

        int i = tableAvailability(date,numberOfPeople);

        if(i == 0){
            System.out.println("No table is available.");
            return false;
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("The table number " + i + " for " + numberOfPeople + " people is available on " + sdf.format(date));
            return true;
        }


    }


    /**
     * Method mainly used by the method requestReservation.
     * Verifica la disponibilità di almeno un tavolo di capienza = al numero di persone
     * per la data richiesta ed, in caso positivo, restituisce il numero tavolo di uno di
     * questi.
     *
     * @param date
     * @param numberOfPeople
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public int tableAvailability(Date date, int numberOfPeople) throws IOException, SQLException, ParseException {

        ArrayList<Integer> restTables = new ArrayList<>();

        Connection connection = this.startConnection();

        statement = connection.createStatement();

        ps = connection.prepareStatement(rp.getProperties().getProperty("select1"));

        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        ps.setInt(1, numberOfPeople);
        ps.setDate(2, sqlDate);

        rs = ps.executeQuery();


        while(rs.next()){
            restTables.add(rs.getInt(2));
        }

        if(restTables.isEmpty()) {
            return 0;
        } else {
            return restTables.get(2); //codice del primo tavolo disponibile quel giorno
        }


    }

    /**
     * Prints all the reservations on file
     * @throws SQLException
     * @throws IOException
     */
    public void printOnFile() throws SQLException, IOException {

            try {
                File x = new File("src/main/resources/reservations.txt");
                FileWriter fileWriter = new FileWriter(x, true);
                PrintWriter outputStream = new PrintWriter(fileWriter);
                outputStream.println(String.format("%-20s %-20s %-20s %-20s %-20s", "Last name", "Number of person", "Cellphone Number", "Date", "Table number"));

                conn = this.startConnection();

                statement = conn.createStatement();

                rs = statement.executeQuery(rp.getProperties().getProperty("selectR"));

                while(rs.next()){
                    outputStream.println(String.format("%-20s %-20s %-20s %-20s %-20s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }

                fileWriter.flush();
                fileWriter.close();

                rs.close();
            } catch (IOException | SQLException e) {
                e.getMessage();
            } finally {
                this.closeAll();
            }

    }

    //All the operations
    public void ops() throws ParseException, SQLException, IOException {
              Scanner sc = new Scanner(System.in);
              int x;
              do {
                  System.out.println("\nSelect operation: \n" +
                          "1 - Insert tables\n" +
                          "2 - Insert reservations\n" +
                          "3 - Update reservation\n" +
                          "4 - Update table\n" +
                          "5 - Delete reservation\n" +
                          "6 - Delete table\n" +
                          "7 - Request reservation\n" +
                          "8 - Print on file\n" +
                          "9 - Show all tables\n" +
                          "10 - Show all reservations\n" +
                          "-1 - Quit\n");
                  x = sc.nextInt();
                  createSchema();
                  switch (x) {
                      case 1:
                          rt.create();
                          rt.insert(r.read());
                          break;
                      case 2:
                          ro.create();
                          ro.insert(res.read());
                          break;
                      case 3:
                          ro.update(res.read());
                          break;
                      case 4:
                          rt.update(r.read());
                          break;
                      case 5:
                          ro.delete();
                          break;
                      case 6:
                          rt.delete();
                          break;
                      case 7:
                          res.read();
                          requestReservation(res.getLastName(),res.getDate(), res.getNumberOfPeople(), res.getCellphoneNumber());
                          break;
                      case 8:
                          printOnFile();
                          break;
                      case 9:
                          rt.showAll();
                          break;
                      case 10:
                          ro.showAll();
                          break;
                      case -1:
                          System.exit(0);
                  }
              } while (x != -1);

          }


}
