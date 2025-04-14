package biz.dss.ticketbookingsystem.utils;

import biz.dss.ticketbookingsystem.intefaces.Formatable;
import biz.dss.ticketbookingsystem.models.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Formatter {

    public static void formatTicket(Transaction transaction) {
        System.out.println("\n============================================================================================\n");
        System.out.printf("Train Number: %d          Train Name: %s%n", transaction.getTrain().getTrainNumber(), transaction.getTrain().getTrainName());
        System.out.printf("PNR: %d               Total Fare: %.2f%n", transaction.getPnr(), transaction.getTotalFare());
        System.out.printf("Date of Journey: %s%n", transaction.getDateOfJourney());
        System.out.printf("Booked By: %s%n", transaction.getUser().getUserName());
        System.out.printf("Source: %s          Destination: %s%n", transaction.getSource().getName(), transaction.getDestination().getName());
        System.out.printf("Cancelled: %s", transaction.isCancelled()?"yes":"No");
        tableTemplate(transaction.getPassengers());
    }

    public static <T extends Formatable> void tableTemplate(List<T> list) {
        if (list.isEmpty()) return;
        T firstObject = list.getFirst(); // @TODO handle exception
        StringBuilder fields = new StringBuilder();
        String title = firstObject.getDisplayableTitle();

        for (String field : firstObject.fieldsToDisplay()) {
            fields.append(field).append(multiplyChar(Formatable.MAX_CHAR_LIMIT - field.length(), ' '));
        }

        int halfSide = (((fields.length() - title.length()) / 2) + 1) - (Formatable.MAX_CHAR_LIMIT / 2);
        String titleBar = multiplyChar(halfSide, '=') + title + multiplyChar(halfSide, '=');

        System.out.println("\n" + titleBar);
        System.out.println(fields);
        System.out.println(multiplyChar(titleBar.length() - Formatable.EXTRA_CHAR, '_'));


        for (T item : list) {
            try {
                item.getFieldValues().forEach(x -> System.out.print((x) + multiplyChar(Formatable.MAX_CHAR_LIMIT - String.valueOf(x).length(), ' ')));
                System.out.println();
            } catch (NullPointerException e) {
            }

        }
        String finisher = multiplyChar(titleBar.length() - Formatable.EXTRA_CHAR, '=') + "\n";
        System.out.println(finisher);
    }

    public static String multiplyChar(int x, char ch) {
        String temp = "";

        for (int i = 0; i < x + Formatable.EXTRA_CHAR; i++) { // @TODO replace with intstream
            temp += ch;
        }


        return temp;
    }

    public static LocalDate formatDate(String date) {
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("d-M-yyyy");
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter format3 = DateTimeFormatter.ofPattern("yyyy-M-d");
        DateTimeFormatter format4 = DateTimeFormatter.ofPattern("yyyy/M/d");
        String pattern1 = "\\d{1,2}-\\d{1,2}-\\d{4}";
        String pattern2 = "\\d{1,2}/\\d{1,2}/\\d{4}";
        String pattern3 = "\\d{4}-\\d{1,2}-\\d{1,2}";
        String pattern4 = "\\d{4}/\\d{1,2}/\\d{1,2}";

        LocalDate formattedDate = null;

        try {
            if (Pattern.matches(pattern1, date)) {
                formattedDate = LocalDate.parse(date, format1);
            } else if (Pattern.matches(pattern2, date)) {
                formattedDate = LocalDate.parse(date, format2);
            } else if (Pattern.matches(pattern3, date)) {
                formattedDate = LocalDate.parse(date, format3);
            } else if (Pattern.matches(pattern4, date)) {
                formattedDate = LocalDate.parse(date, format4);
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        return formattedDate;
    }




}


