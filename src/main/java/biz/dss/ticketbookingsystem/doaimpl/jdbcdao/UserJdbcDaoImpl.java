package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.UserDao;
import biz.dss.ticketbookingsystem.enums.Gender;
import biz.dss.ticketbookingsystem.enums.UserType;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.utils.SqlQueries;
import biz.dss.ticketbookingsystem.utils.UtilClass;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserJdbcDaoImpl implements UserDao {
    private Connection connection = DbConnection.getConnection();

    @Override
    public Optional<User> addUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.addUserQuery);
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setString(5, user.getGender().toString());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setString(8, user.getSeatNumber());
            preparedStatement.setString(9, user.getUserType().toString());
            preparedStatement.setBoolean(10, false);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                return Optional.of(user);
            }
        } catch (SQLException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getUserById);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id_ = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String userName = resultSet.getString("username");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String seatNumber = resultSet.getString("seat_number");
                String userType = resultSet.getString("user_type");
                boolean isLoggedIn = resultSet.getBoolean("is_logged_in");
                User user = User.builder().id(id_).name(name).userName(userName)
                        .age(age).gender(Gender.valueOf(gender.toUpperCase()))
                        .password(password).email(email).userType(UserType.valueOf(userType.toUpperCase()))
                        .seatNumber(seatNumber).isLoggedIn(isLoggedIn).build();
                return Optional.of(user);
            }
        } catch (SQLException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getUserByEmail);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String userName = resultSet.getString("username");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String email_ = resultSet.getString("email");
                String password = resultSet.getString("password");
                String seatNumber = resultSet.getString("seat_number");
                String userType = resultSet.getString("user_type");
                boolean isLoggedIn = resultSet.getBoolean("is_logged_in");
                User user = User.builder().id(id).name(name).userName(userName)
                        .age(age).gender(Gender.valueOf(gender.toUpperCase()))
                        .password(password).email(email_).userType(UserType.valueOf(userType.toUpperCase()))
                        .seatNumber(seatNumber).isLoggedIn(isLoggedIn).build();
                return Optional.of(user);
            }
        } catch (SQLException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByUserName(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getUserByUserName);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
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
                return Optional.of(user);
            }
        } catch (SQLException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> updateUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateUserById);
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
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                return Optional.of(user);
            }
        } catch (SQLException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> deleteUser(String username) {
        Optional<User> userOpt = getUserByUserName(username);
        if(userOpt.isEmpty()) return Optional.empty();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.deleteUserbyUserName);
            preparedStatement.setString(1, username);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                return userOpt;
            }
        } catch (SQLException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getAllUsers);
            ResultSet resultSet = preparedStatement.executeQuery();
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
        } catch (SQLException | NullPointerException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
}
