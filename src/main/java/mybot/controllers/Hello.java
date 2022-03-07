package mybot.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hello {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        LocalDateTime parse = LocalDateTime.parse("03/04 12:00", formatter);
        System.out.println(parse);
//        String date = "16-08-2018 12:10";
//        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//        LocalDateTime parse = LocalDateTime.parse(date, formatter1);
//        System.out.println("VALUE1="+parse);
    }
}
