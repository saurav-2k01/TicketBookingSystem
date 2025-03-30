package biz.dss.ticketbookingsystem.dao;

import biz.dss.ticketbookingsystem.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> addUser(User user);

    Optional<User> getUserById(Integer id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUserName(String username);

    Optional<User> updateUser(User user);

    Optional<User> deleteUser(String username);

    List<User> getUsers();
}
