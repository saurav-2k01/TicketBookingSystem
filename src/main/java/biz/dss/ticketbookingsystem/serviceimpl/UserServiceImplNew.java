package biz.dss.ticketbookingsystem.serviceimpl;

import biz.dss.ticketbookingsystem.dao.UserDao;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.service.UserService;
import biz.dss.ticketbookingsystem.utils.Response;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static biz.dss.ticketbookingsystem.enums.UserType.ADMIN;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

public class UserServiceImplNew implements UserService {
    private final UserDao userDao;
    private Response response;

    public UserServiceImplNew(UserDao userDao) {
        this.userDao = userDao;
    }

    public Response registerUser(User user) {
        if (Objects.isNull(user)) {
            return new Response(FAILURE, "User cannot be null.");
        }
        Optional<User> addedUser = userDao.addUser(user);
        if (addedUser.isPresent()) {
            return response = new Response(user, SUCCESS, String.format("%s has been as registered user.", user.getName()));
        }
        return response = new Response(FAILURE, String.format("Failed to register %s as registered user.", user.getName()));
    }


    public Response getUserById(Integer id) {
        if (Objects.isNull(id)) {
            return new Response(FAILURE, "Id cannot be null.");
        }
        Optional<User> foundUser = userDao.getUserById(id);
        return foundUser.map(user -> response = new Response(user, SUCCESS, String.format("user found with name '%s'.", user.getName())))
                .orElseGet(() -> response = new Response(FAILURE, String.format("No user found for id '%d'.", id)));
    }


    public Response getUserByEmail(String email) {
        if (Objects.isNull(email)) {
            return new Response(FAILURE, "Email cannot be null.");
        }
        Optional<User> foundUser = userDao.getUserByEmail(email);
        return foundUser.map(user -> response = new Response(user, SUCCESS, String.format("user found with name '%s'.", user.getName())))
                .orElseGet(() -> response = new Response(FAILURE, String.format("No user found for email '%s'.", email)));
    }


    public Response getUserByUserName(String username) {
        if (Objects.isNull(username)) {
            return new Response(FAILURE, "Username cannot be null.");
        }
        Optional<User> foundUser = userDao.getUserByUserName(username);
        return foundUser.map(user -> response = new Response(user, SUCCESS, String.format("user found with name '%s'.", user.getUserName())))
                .orElseGet(() -> response = new Response(FAILURE, String.format("No user found for username '%s'.", username)));
    }


    public Response deleteUser(String username) {
        if (Objects.isNull(username)) {
            return new Response(FAILURE, "Email cannot be null.");
        }
        Optional<User> deletedUser = userDao.deleteUser(username);
        return deletedUser.map(user -> response = new Response(user, SUCCESS, String.format("user '%s' was deleted.", user.getName())))
                .orElseGet(() -> response = new Response(FAILURE, String.format("No user found for username '%s'.", username)));
    }


    public Response getUsers() {

        List<User> users = userDao.getUsers();
        if (Objects.isNull(users)) {
            response = new Response(FAILURE, "no users found.");
        } else {
            response = new Response(users, SUCCESS, String.format("%d users found.", users.size()));
        }
        return response;
    }

    public Response getAllAdmins() {

        List<User> admins = userDao.getUsers().stream().filter(user -> user.getUserType().equals(ADMIN)).toList();
        if (admins.isEmpty()) {
            response = new Response(admins, SUCCESS, "No admins found.");
        } else {
            response = new Response(admins, SUCCESS, String.format("%d admins found.", admins.size()));
        }
        return response;
}
}
