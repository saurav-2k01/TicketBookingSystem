package biz.dss.ticketbookingsystem.serviceimpl;

import biz.dss.ticketbookingsystem.dao.UserDao;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.service.AuthenticationService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserDao userDao;
    private Response response;

    public AuthenticationServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    public Response authenticateUser(String userName, String password) {
        if (Objects.isNull(userName) || Objects.isNull(password)) {
            logger.warn("Bad input provided: username or password is null");
            return new Response(FAILURE, "Bad input");
        }

        Optional<User> user = userDao.getUserByUserName(userName);

        if (user.isPresent()) {

            if (user.get().getPassword().equals(password)) {
                user.get().setIsLoggedIn(true);
                userDao.updateUser(user.get());
                AuthenticatedUser authenticatedUser = AuthenticatedUser
                        .builder()
                        .id(user.get().getId())
                        .userName(user.get().getUserName())
                        .userType(user.get().getUserType())
                        .isLoggedIn(user.get().getIsLoggedIn()).build();
                response = new Response(authenticatedUser, SUCCESS, String.format("%s is now logged in.", userName));
                logger.info("user '{}' is successfully logged in.", userName);
            } else {
                response = new Response(FAILURE, "Wrong credential. Login Failed");
                logger.warn("Invalid password for username: '{}'", userName);
            }
        } else {
            response = new Response(FAILURE, String.format("User not found for username '%s'", userName));
            logger.warn("User not found for username: '{}'", userName);
        }
        return response;
    }

    public Response logout(AuthenticatedUser authenticatedUser) {
        response = checkLogin(authenticatedUser);
        if(! response.isSuccess()){
            return response;
        }
        Optional<User> user = userDao.getUserById(authenticatedUser.getId());
        if (user.isPresent()) {
            user.get().setIsLoggedIn(false);
            userDao.updateUser(user.get());
            authenticatedUser.setIsLoggedIn(false);
        }

        response = new Response(authenticatedUser, SUCCESS, "User is now logged out.");
        return response;
    }

    private Response checkLogin(AuthenticatedUser authenticatedUser) {
        if(Objects.isNull(authenticatedUser)){
            return new Response(FAILURE, "Authenticated user cannot be null.");
        }
        Optional<User> userByIdResponse = userDao.getUserById(authenticatedUser.getId());
        if(userByIdResponse.isPresent()){
            Boolean isLoggedIn = userByIdResponse.get().getIsLoggedIn();
            if(isLoggedIn){
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
            Optional<User> user = userDao.getUserById(authenticatedUser.getId());
            response = user.map(value -> new Response(value, SUCCESS, value.getName())).orElseGet(() -> new Response(FAILURE, "user not found."));
        }else{
            response = new Response(FAILURE, "User is not logged in.");
        }
        return response;
    }
}
