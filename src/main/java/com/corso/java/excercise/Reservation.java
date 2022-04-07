package com.corso.java.excercise;

import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class Reservation {

    private String lastName;
    private Date date;
    private int numberOfPeople;
    private String cellphoneNumber;
    private int tableNumber;

    //Reads a reservation from input
    public Reservation read() throws ParseException {

        Scanner sc = new Scanner(System.in);

        System.out.println("Insert last name: ");
        this.lastName = sc.nextLine();

        System.out.println("Insert data: ");
        String d = sc.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.date = sdf.parse(d);

        System.out.println("Insert number of people: ");
        String n = sc.nextLine();
        this.numberOfPeople = Integer.parseInt(n);

        System.out.println("Insert cellphone number: ");
        this.cellphoneNumber = sc.nextLine();

        System.out.println("Insert table number: ");
        String t = sc.nextLine();
        this.tableNumber = Integer.parseInt(t);

        return new Reservation(this.lastName, this.date, this.numberOfPeople, this.cellphoneNumber, this.tableNumber);

    }
}
