package com.corso.java.excercise;

import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@Data @NoArgsConstructor @Builder @AllArgsConstructor @ToString
public class RestTable {

    private int number;
    private int capacity;

    /**
     * Reads a restaurant table from input
     * @return
     * @throws ParseException
     */
    public RestTable read() throws ParseException {

        Scanner sc = new Scanner(System.in);

        System.out.println("Insert table number: ");
        String n = sc.nextLine();
        this.number = Integer.parseInt(n);

        System.out.println("Insert table's capacity: ");
        String c = sc.nextLine();
        this.capacity = Integer.parseInt(c);


        return new RestTable(this.number, this.capacity);

    }
}
