package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.UserDao;
import biz.dss.ticketbookingsystem.enums.Gender;
import biz.dss.ticketbookingsystem.enums.UserType;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.utils.SqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserJdbcDaoImpl implements UserDao {
    private final Connection connection = DbConnection.getConnection();

    @Override
    public Optional<User> addUser(User user) throws SQLException {

        int affectedRows;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ADD_USER_QUERY)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setString(5, user.getGender().toString());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setString(8, user.getSeatNumber());
            preparedStatement.setString(9, user.getUserType().toString());
            preparedStatement.setBoolean(10, false);
            affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                return Optional.of(user);
            }

            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserById(Integer id) throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_USER_BY_USERID)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int idRes = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String userName = resultSet.getString("username");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String seatNumber = resultSet.getString("seat_number");
                String userType = resultSet.getString("user_type");
                boolean isLoggedIn = resultSet.getBoolean("is_logged_in");
                User user = User.builder().id(idRes).name(name).userName(userName)
                        .age(age).gender(Gender.valueOf(gender.toUpperCase()))
                        .password(password).email(email).userType(UserType.valueOf(userType.toUpperCase()))
                        .seatNumber(seatNumber).isLoggedIn(isLoggedIn).build();
                return Optional.of(user);
            }

            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws SQLException {

        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String userName = resultSet.getString("username");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String emailRes = resultSet.getString("email");
                String password = resultSet.getString("password");
                String seatNumber = resultSet.getString("seat_number");
                String userType = resultSet.getString("user_type");
                boolean isLoggedIn = resultSet.getBoolean("is_logged_in");
                User user = User.builder().id(id).name(name).userName(userName)
                        .age(age).gender(Gender.valueOf(gender.toUpperCase()))
                        .password(password).email(emailRes).userType(UserType.valueOf(userType.toUpperCase()))
                        .seatNumber(seatNumber).isLoggedIn(isLoggedIn).build();
                return Optional.of(user);
            }

            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByUserName(String username) throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_USER_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String userName = resultSet.getString("username");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String seatNumber = resultSet.getString("seat_number");
                String userType = resultSet.getString("user_type");
                boolean isLoggedIn = resultSet.getBoolean("is_logged_in");
                User user = User.builder().id(id).name(name).userName(userName)
                        .age(age).gender(Gender.valueOf(gender.toUpperCase()))
                        .password(password).email(email).userType(UserType.valueOf(userType.toUpperCase()))
                        .seatNumber(seatNumber).isLoggedIn(isLoggedIn).build();
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> updateUser(User user) throws SQLException {
        int affectedRows;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_USER_BY_USERID)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setString(5, user.getGender().toString());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setString(8, user.getSeatNumber());
            preparedStatement.setString(9, user.getUserType().toString());
            preparedStatement.setBoolean(10, user.getIsLoggedIn());
            preparedStatement.setInt(11, user.getId());
            affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> deleteUser(String username) throws SQLException{
        Optional<User> userOpt = getUserByUserName(username);
        if(userOpt.isEmpty()) return Optional.empty();
        int affectedRows;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.DELETE_USER_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                return userOpt;
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_ALL_USERS)) {
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String userName = resultSet.getString("username");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String seatNumber = resultSet.getString("seat_number");
                String userType = resultSet.getString("user_type");
                boolean isLoggedIn = resultSet.getBoolean("is_logged_in");
                User user = User.builder().id(id).name(name).userName(userName)
                        .age(age).gender(Gender.valueOf(gender.toUpperCase()))
                        .password(password).email(email).userType(UserType.valueOf(userType.toUpperCase()))
                        .seatNumber(seatNumber).isLoggedIn(isLoggedIn).build();
                users.add(user);
            }
            return users;
        }
    }
}
