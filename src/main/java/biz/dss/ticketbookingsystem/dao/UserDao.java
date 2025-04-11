package biz.dss.ticketbookingsystem.dao;

import biz.dss.ticketbookingsystem.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> addUser(User user) throws SQLException;

    Optional<User> getUserById(Integer id) throws SQLException;

    Optional<User> getUserByEmail(String email) throws SQLException;

    Optional<User> getUserByUserName(String username) throws SQLException;

    Optional<User> updateUser(User user) throws SQLException;

    Optional<User> deleteUser(String username) throws SQLException;

    List<User> getUsers() throws SQLException;
}
