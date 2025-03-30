package biz.dss.ticketbookingsystem.utils;

public class SqlQueries {
    public static String addUserQuery = "insert into \"user\" (id, name, username, age, gender, email, password, seat_number, user_type, is_logged_in)" +
            "values (?, ?, ?, ?, ?, ?, ? , ?, ?, ?);";

    public static String getUserByUserName = "select * from \"user\" where username=?;";
    public static String getUserById = "select * from \"user\" where id=?;";
    public static String updateUserById = "update \"user\" set id=?, name=?, username=?, age=?, gender=?, email=?, password=?, seat_number=?, user_type=?, is_logged_in=?" +
            "where id = ?";
}


