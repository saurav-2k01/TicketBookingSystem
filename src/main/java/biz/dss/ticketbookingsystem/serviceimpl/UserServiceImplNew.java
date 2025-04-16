package biz.dss.ticketbookingsystem.serviceimpl;

import biz.dss.ticketbookingsystem.dao.UserDao;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.service.AuthenticationService;
import biz.dss.ticketbookingsystem.service.UserService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static biz.dss.ticketbookingsystem.enums.UserType.ADMIN;
import static biz.dss.ticketbookingsystem.enums.UserType.REGISTERED_USER;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

@Slf4j
public class UserServiceImplNew implements UserService {
    private final UserDao userDao;
    private final AuthenticationService authenticationService;
    private Response response;

    public UserServiceImplNew(AuthenticationService authenticationService, UserDao userDao) {
        this.authenticationService = authenticationService;
        this.userDao = userDao;
    }

    public Response registerUser(User user) {
        if (Objects.isNull(user)) {
            return new Response(FAILURE, "User cannot be null.");
        }
        Optional<User> addedUser = Optional.empty();
        try {
            addedUser = userDao.addUser(user);
        } catch (SQLException e) {
            log.error("Error occurred while adding user.");
        }
        if (addedUser.isPresent()) {
            return response = new Response(user, SUCCESS, String.format("%s has been as registered as %s.", user.getName(), user.getUserType()));
        }
        return response = new Response(FAILURE, String.format("Failed to register %s as %s", user.getName(), user.getUserType()));
    }


    public Response getUserById(Integer id) {
        if (Objects.isNull(id)) {
            return new Response(FAILURE, "Id cannot be null.");
        }
        Optional<User> foundUser = Optional.empty();
        try {
            foundUser = userDao.getUserById(id);
        } catch (SQLException e) {
            log.error("Error occurred while getting user", e);
        }
        return foundUser.map(user -> response = new Response(user, SUCCESS, String.format("user found with name '%s'.", user.getName())))
                .orElseGet(() -> response = new Response(FAILURE, String.format("No user found for id '%d'.", id)));
    }


    public Response getUserByEmail(String email) {
        if (Objects.isNull(email)) {
            return new Response(FAILURE, "Email cannot be null.");
        }
        Optional<User> foundUser = Optional.empty();
        try {
            foundUser = userDao.getUserByEmail(email);
        } catch (SQLException e) {
            log.error("Error occurred while getting user", e);
        }
        return foundUser.map(user -> response = new Response(user, SUCCESS, String.format("user found with name '%s'.", user.getName())))
                .orElseGet(() -> response = new Response(FAILURE, String.format("No user found for email '%s'.", email)));
    }


    public Response getUserByUserName(String username) {
        if (Objects.isNull(username)) {
            return new Response(FAILURE, "Username cannot be null.");
        }
        Optional<User> foundUser = Optional.empty();
        try {
            foundUser = userDao.getUserByUserName(username);
        } catch (SQLException e) {
            log.error("Error occurred while getting user.", e);
        }
        return foundUser.map(user -> response = new Response(user, SUCCESS, String.format("user found with name '%s'.", user.getUserName())))
                .orElseGet(() -> response = new Response(FAILURE, String.format("No user found for username '%s'.", username)));
    }


    public Response deleteUser(AuthenticatedUser authenticatedUser, String username) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());

        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }
        if (Objects.isNull(username)) {
            return new Response(FAILURE, "Email cannot be null.");
        } else if (username.equals(user.getUserName()) && user.getUserType().equals(ADMIN)) {
            return new Response(FAILURE, "Admin cannot delete his own account.");
        }

        try {
            Optional<User> deletedUser = userDao.deleteUser(username);
            if (deletedUser.isPresent()) {
                response = new Response(deletedUser, SUCCESS, String.format("user '%s' was deleted.", deletedUser.get().getName()));
            } else {
                response = new Response(FAILURE, String.format("No user found for username '%s'.", username));
            }
        } catch (SQLException e) {
            log.error("Error occurred while deleting user.", e);
            response = new Response(FAILURE, String.format("No user found for username '%s'.", username));
        }
        return response;
    }


    public Response getUsers(AuthenticatedUser authenticatedUser) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }
        List<User> users = null;
        try {
            users = userDao.getUsers().stream().filter(u -> u.getUserType().equals(REGISTERED_USER)).toList();
        } catch (SQLException e) {
            log.error("Error occurred while getting users", e);
        }
        if (Objects.isNull(users)) {
            response = new Response(FAILURE, "no users found.");
        } else {
            response = new Response(users, SUCCESS, String.format("%d users found.", users.size()));
        }
        return response;
    }

    public Response getAllAdmins(AuthenticatedUser authenticatedUser) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }
        List<User> admins;
        try {
            admins = userDao.getUsers().stream().filter(u -> u.getUserType().equals(ADMIN)).toList();
            if (admins.isEmpty()) {
                response = new Response(admins, SUCCESS, "No admins found.");
            } else {
                response = new Response(admins, SUCCESS, String.format("%d admins found.", admins.size()));
            }
        } catch (SQLException e) {
            log.error("Error occurred while getting users.", e);
            response = new Response(FAILURE, "no admins found");
        }

        return response;
    }

    public Response userExists(String userName){
        try {
            Optional<User> userResp = userDao.getUserByUserName(userName);
            response = userResp.map(user->new Response(true, SUCCESS, String.format("User exists with the username: %s.", userName))).orElse(new Response(false, SUCCESS, String.format("No user exists with the username %s", userName)));
        } catch (SQLException e) {
            log.error("Error occurred while getting users.", e);
            response = new Response(false, FAILURE, String.format("Got error find user with the username %s", userName));
        }
        return response;
    }
}
