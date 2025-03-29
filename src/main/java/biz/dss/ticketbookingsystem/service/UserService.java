package biz.dss.ticketbookingsystem.service;

import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.Response;

public interface UserService {

    Response registerUser(User user);

    Response getUserById(Integer id);

    Response getUserByEmail(String email);

    Response getUserByUserName(String username);

    Response deleteUser(String username);

    Response getUsers();

    Response getAllAdmins();

}
