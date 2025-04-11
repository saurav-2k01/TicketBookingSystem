package biz.dss.ticketbookingsystem.serviceimpl;

import biz.dss.ticketbookingsystem.dao.UserDao;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.service.AuthenticationService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDao userDao;
    private Response response;

    public AuthenticationServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    public Response authenticateUser(String userName, String password) {
        if (Objects.isNull(userName) || Objects.isNull(password)) {
            
            log.warn("Bad input provided: username or password is null");
            return new Response(FAILURE, "Bad input");
        }

        Optional<User> user = Optional.empty();
        try {
            user = userDao.getUserByUserName(userName);
        } catch (SQLException e) {
            log.error("Error occurred while getting user.", e);
        }

        if (user.isPresent()) {

            if (user.get().getPassword().equals(password)) {
                user.get().setIsLoggedIn(true);
                try {
                    userDao.updateUser(user.get());
                } catch (SQLException e) {
                    log.error("Error occurred while updating user.", e);
                }
                AuthenticatedUser authenticatedUser = AuthenticatedUser
                        .builder()
                        .id(user.get().getId())
                        .userName(user.get().getUserName())
                        .userType(user.get().getUserType())
                        .isLoggedIn(user.get().getIsLoggedIn()).build();
                response = new Response(authenticatedUser, SUCCESS, String.format("%s is now logged in.", userName));
                log.info("user '{}' is successfully logged in.", userName);
            } else {
                response = new Response(FAILURE, "Wrong credential. Login Failed");
                log.warn("Invalid password for username: '{}'", userName);
            }
        } else {
            response = new Response(FAILURE, String.format("User not found for username '%s'", userName));
            log.warn("User not found for username: '{}'", userName);
        }
        return response;
    }

    public Response logout(AuthenticatedUser authenticatedUser) {
        response = checkLogin(authenticatedUser);
        if(Boolean.FALSE.equals( response.isSuccess())){
            return response;
        }
        Optional<User> user = Optional.empty();
        try {
            user = userDao.getUserById(authenticatedUser.getId());
        } catch (SQLException e) {
            log.error("Error occurred while getting user.", e);
        }
        if (user.isPresent()) {
            user.get().setIsLoggedIn(false);
            try {
                userDao.updateUser(user.get());
            } catch (SQLException e) {
                log.error("Error occurred while updating user.", e);
            }
            authenticatedUser.setIsLoggedIn(false);
        }

        response = new Response(authenticatedUser, SUCCESS, "User is now logged out.");
        return response;
    }

    private Response checkLogin(AuthenticatedUser authenticatedUser) {
        if(Objects.isNull(authenticatedUser)){
            return new Response(FAILURE, "Authenticated user cannot be null.");
        }
        Optional<User> userByIdResponse = Optional.empty();
        try {
            userByIdResponse = userDao.getUserById(authenticatedUser.getId());
        } catch (SQLException e) {
            log.error("Error occurred while getting user.", e);
        }
        if(userByIdResponse.isPresent()){
            Boolean isLoggedIn = userByIdResponse.get().getIsLoggedIn();
            if(Boolean.TRUE.equals(isLoggedIn)){
                response = new Response(true, SUCCESS, String.format("%s is logged in.", authenticatedUser.getUserName()));
            }else{
                response = new Response(false, FAILURE, "User is not logged in.");
            }
        }else{
            response = new Response(FAILURE, "User not found.");
        }
        return response;
    }

    public Response getAuthenticatedUser(AuthenticatedUser authenticatedUser){
        response = checkLogin(authenticatedUser);
        if(Boolean.TRUE.equals(response.isSuccess())){
            Optional<User> user = Optional.empty();
            try {
                user = userDao.getUserById(authenticatedUser.getId());
            } catch (SQLException e) {
                log.error("Error occurred while getting user.", e);
            }
            response = user.map(value -> new Response(value, SUCCESS, value.getName())).orElseGet(() -> new Response(FAILURE, "user not found."));
        }else{
            response = new Response(FAILURE, "User is not logged in.");
        }
        return response;
    }
}
