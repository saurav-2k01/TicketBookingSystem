package biz.dss.ticketbookingsystem.utils;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Validator {

    public static Predicate<String> validateDateFormat = date -> Pattern.matches("\\d{1,2}/\\d{1,2}/\\d{4}", date);

    public static Predicate<LocalDate> dateIsAfterToday = date -> date.isAfter(LocalDate.now());

    public static Predicate<String> validateEmailFormat = email -> Pattern.matches("[a-zA-Z0-9.]+@[a-zA-Z]+\\.{1}[a-zA-Z]{2,10}", email);

    public static Predicate<String> validatePassword = password -> Pattern.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*./~?,}{()]).{8,64}", password);
}
