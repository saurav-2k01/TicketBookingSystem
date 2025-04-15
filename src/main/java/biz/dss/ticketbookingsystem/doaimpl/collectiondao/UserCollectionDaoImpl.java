package biz.dss.ticketbookingsystem.doaimpl.collectiondao;

import biz.dss.ticketbookingsystem.dao.UserDao;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.PopulateSomeHardCodedData;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class UserCollectionDaoImpl implements UserDao {
    private final List<User> users = PopulateSomeHardCodedData.populateUsers();
    static UserDao userDao;

    @Override
    public Optional<User> addUser(User user) throws NullPointerException{
        if(Objects.isNull(user)){
            throw new NullPointerException();
        }

        boolean isAdded;

        if(users.contains(user)){
            isAdded = false;
        }else{
         isAdded = users.add(user);
        }
        return isAdded? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        if (Objects.isNull(id)){
            throw new NullPointerException();
        }
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public Optional<User> getUserByUserName(String userName) {
        if (Objects.isNull(userName)){
            throw new NullPointerException();
        }
        return users.stream().filter(user -> user.getUserName().equals(userName)).findFirst();
    }

    @Override
    public Optional<User> updateUser(User user) {
        if(Objects.isNull(user)){
            throw new NullPointerException();
        }
        Integer id = user.getId();
        Optional<User> trainResult = users.stream().filter(t -> t.getId().equals(id)).findFirst();
        if(trainResult.isPresent()){
            int index = users.indexOf(trainResult.get());
            users.set(index, user);
            return Optional.of(user);
        }else{
            return Optional.empty();
        }
    }

    public Optional<User> getUserByEmail(String email) {
        if (Objects.isNull(email)){
            throw new NullPointerException();
        }
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }



    @Override
    public Optional<User> deleteUser(String userName) {
        if (Objects.isNull(userName)){
            throw new NullPointerException();
        }
        Optional<User> foundUser = users.stream().filter(user -> user.getUserName().equals(userName)).findFirst();
        boolean isRemoved;
        isRemoved = foundUser.map(users::remove).orElse(false);
        return isRemoved? foundUser : Optional.empty();

    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
