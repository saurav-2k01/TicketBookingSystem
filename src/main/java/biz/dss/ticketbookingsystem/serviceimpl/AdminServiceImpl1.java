//package biz.dss.ticketbookingsystem.serviceimpl;
//
//import biz.dss.ticketbookingsystem.dao.AdminDao;
//import biz.dss.ticketbookingsystem.dao.BookingDao;
//import biz.dss.ticketbookingsystem.dao.TrainDao;
//import biz.dss.ticketbookingsystem.dao.UserDao;
//import biz.dss.ticketbookingsystem.models.Admin;
//import biz.dss.ticketbookingsystem.models.Transaction;
//import biz.dss.ticketbookingsystem.models.User;
//import biz.dss.ticketbookingsystem.utils.Response;
//import biz.dss.ticketbookingsystem.valueobjects.Credential;
//
//import java.util.Map;
//import java.util.Objects;
//import java.util.Optional;
//
//import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
//import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;
//
//
//public class AdminServiceImpl1 {
//    //    private Map<String, Admin> admins = AdminDao.getAdmins();
////    private static Map<Integer, Transaction> transactions = BookingDao.getTransactions();
////    private static Map<String, User> users = UserDao.getUsers();
//    private final AdminDao adminDao;
//    private final BookingDao bookingDao;
//    private final UserDao userDao;
//    private final TrainDao trainDao;
//    private Admin currentAdmin;
//    private Response response;
//
//    public AdminServiceImpl1(AdminDao adminDao, BookingDao bookingDao, UserDao userDao, TrainDao trainDao) {
//        this.adminDao = adminDao;
//        this.bookingDao = bookingDao;
//        this.userDao = userDao;
//        this.trainDao = trainDao;
//    }
//
//    public Response registerAdmin(User admin) {
//        Optional<User> user = userDao.addUser(admin);
//
//        if (user.isPresent()) {
//            response = new Response(true, SUCCESS, "New admin was added successfully.");
//        } else {
//            response = new Response(false, FAILURE, "Adding admin was unsuccessful.");
//        }
//        return response;
//    }
//
////    public Response login(Credential credential) {
////        User admin = userDao.getUser().get(credential.getUsername());
////
////        if (Objects.isNull(admin)) {
//////            throw new UserNotFoundException("Admin not found.");
////            response = new Response(FAILURE, "Admin not found");
////            return response;
////        }
////
////        if (admin.getPassword().equals(credential.getPassword())) {
////            admin.setLoggedIn(true);
////            currentAdmin = admin;
////            response = new Response(1, SUCCESS, "Admin logged in successfully.");
////            return response;
////        } else {
//////            throw new WrongCredentialExceptions("Either admin's username is wrong or admin's password is wrong.");
////            response = new Response(FAILURE, "Wrong Credentials.");
////            return response;
////        }
////    }
//
//    public Response getUsers() {
//        if(checkLogin().getStatus().equals(FAILURE)){
//            return checkLogin();
//        }
//
//        Map<String, User> users = userDao.getUsers();
//        if(Objects.isNull(users)){
//            response = new Response(FAILURE, "No Users found.");
//        }else{
//            response = new Response(users, SUCCESS, "list of users.");
//        }
//        return response;
//    }
//
//    public Response getTransactions() {
//        if(checkLogin().getStatus().equals(FAILURE)){
//            return checkLogin();
//        }
//
//        Map<Integer, Transaction> transactions = bookingDao.getTransactions();
//        if (Objects.isNull(transactions)){
//            response = new Response(FAILURE, "No transactions Found.");
//        }else{
//            response = new Response(transactions, SUCCESS, "list of transactions.");
//        }
//        return response;
//    }
//
//    public Response getTransaction(int pnr){
//        if(checkLogin().getStatus().equals(FAILURE)){
//            return checkLogin();
//        }
//
//        Transaction transaction = bookingDao.getTransactions().get(pnr);
//        if (Objects.isNull(transaction)) {
//            response = new Response(FAILURE, "No Such transaction found.");
//        }else{
//            response = new Response(transaction, SUCCESS, "Transaction found.");
//        }
//        return response;
//    }
//
//    public Response logout() {
//        if(checkLogin().getStatus().equals(FAILURE)){
//            return checkLogin();
//        }
//
//        currentAdmin.setLoggedIn(false);
//        currentAdmin = null;
//        response = new Response(SUCCESS, "Admin is now logged out.");
//        return response;
//    }
//
//    public Response deleteAdmin(String userName){
//        if(checkLogin().getStatus().equals(FAILURE)){
//            return checkLogin();
//        }
//
//        if (currentAdmin != null && currentAdmin.isLoggedIn() && !userName.equals(currentAdmin.getUserName())) {
//            boolean isDeleted = adminDao.removeAdmin(userName);
//            if (isDeleted) {
////                return true;
//                response = new Response(true, SUCCESS, "Admin has been deleted.");
//            } else {
////                throw new UserNotFoundException("Admin not found.");
//                response = new Response(false, FAILURE, "Deletion of admin was unsuccessful.");
//            }
//        }else{
//            response = new Response(FAILURE, "Admin cannot delete his/her account while logged in.");
//        }
//        return response;
//    }
//
////    public void blockUser(String userName) throws UserIsAlreadyBlockedException, UserNotFoundException {
////        User user = userDao.getUsers().get(userName);
////        if (Objects.isNull(user)) {
////            throw new UserNotFoundException();
////        }
////        if (!user.getIsActive()) {
////            throw new UserIsAlreadyBlockedException();
////        }
////        user.setActive(false);
////    }
////
////    public void unBlockUser(String userName) throws UserIsAlreadyUnBlockedException, UserNotFoundException {
////        User user = userDao.getUsers().get(userName);
////        if (Objects.isNull(user)) {
////            throw new UserNotFoundException();
////        }
////        if (user.getIsActive()) {
////            throw new UserIsAlreadyUnBlockedException();
////        }
////        user.setActive(true);
////    }
//
////    public Map<Integer, Train> getTrains() {
////        return trainDao.getTrains();
////    }
//
//    /**
//     * @return Admin currentAdmin.
//     */
//    public Response getCurrentAdmin() {
//        if(checkLogin().getStatus().equals(FAILURE)){
//            return checkLogin();
//        }
//
//        if(Objects.isNull(currentAdmin)){
//            response = new Response(FAILURE, "There is no current admin.");
//        }else{
//            response = new Response(SUCCESS, "Current admin.");
//        }
//        return response;
//    }
//
//    public Response checkLogin() {
//        if (currentAdmin == null || ! currentAdmin.isLoggedIn()) {
//            response = new Response(false,FAILURE,"Login Required.");
//        }else{
//            response = new Response(true, SUCCESS, "Admin is Logged in.");
//        }
//        return response;
//    }
//}
