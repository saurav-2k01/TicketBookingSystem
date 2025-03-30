package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.UserDao;
import biz.dss.ticketbookingsystem.models.User;

import java.util.List;
import java.util.Optional;

public class UserJdbcDaoImpl implements UserDao {
    @Override
    public Optional<User> addUser(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByUserName(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> updateUser(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> deleteUser(String username) {
        return Optional.empty();
    }

    @Override
    public List<User> getUsers() {
        return List.of();
    }
}
