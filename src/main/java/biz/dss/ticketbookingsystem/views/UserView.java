//package biz.dss.ticketbookingsystem.views;
//
//import biz.dss.ticketbookingsystem.controller.UserController;
//import biz.dss.ticketbookingsystem.enums.Gender;
//import biz.dss.ticketbookingsystem.enums.TravellingClass;
//import biz.dss.ticketbookingsystem.models.*;
//import biz.dss.ticketbookingsystem.utils.*;
//import biz.dss.ticketbookingsystem.utils.Formatter;
//import biz.dss.ticketbookingsystem.valueobjects.AvailableSeats;
//import biz.dss.ticketbookingsystem.valueobjects.BookingDetail;
//import biz.dss.ticketbookingsystem.valueobjects.Credential;
//import biz.dss.ticketbookingsystem.valueobjects.TrainSearchDetail;
//
//import java.time.LocalDate;
//import java.util.*;
//
//import static biz.dss.ticketbookingsystem.utils.ResponseStatus.*;
//
//public class UserView {
//    private Scanner input = UtilClass.scanner;
//    private final UserController userController;
//    private final InputView inputView;
//    private Response response;
//
//    public UserView(UserController userController, InputView inputView) {
//        this.userController = userController;
//        this.inputView = inputView;
//    }
//
//    public void registerNewUser() {
//        String name = inputView.getStringInput("Name: ");
//        String userName = inputView.getStringInput("Username: ");
//        Integer age = inputView.getAge("Age: ", 100, 18);
//        Gender gender = inputView.getGender("Gender: ");
//        String email = inputView.getEmail("Email: ");
//        String password = inputView.getPassword("Password: ");
//
//
//        User user = User.builder().name(name).userName(userName).age(age).gender(gender).email(email).password(password).build();
//        response = userController.register(user);
//        System.out.println(response.getMessage());
//    }
//
//    public int loginUser() {
//        System.out.println("Enter username: ");
//        String userName = input.nextLine();
//
//        System.out.println("Enter password: ");
//        String password = input.nextLine();
//
//        Credential credential = new Credential(userName, password);
//        response = userController.login(credential);
//        Integer data = (Integer) (response.getData());
//        System.out.println(response.getMessage());
//        return data;
//    }
//
//    private List<Train> filterTrains(TrainSearchDetail trainSearchDetail) {
//        Response response = userController.searchTrains(trainSearchDetail);
//
//        List<Train> filteredTrains = null;
//        if (response.getStatus().equals(FAILURE)) {
//            System.out.println(response.getMessage());
//        } else {
//            filteredTrains = (List<Train>) (response.getData());
//        }
//        return filteredTrains;
//    }
//
//    public void searchTrains() {
//        TrainSearchDetail trainSearchDetail = getTrainSearchInput();
//        List<Train> trains = filterTrains(trainSearchDetail);
//        if(trains.isEmpty()){
//            System.out.printf("No trains found on %s from %s to %s", trainSearchDetail.getDate().toString(), trainSearchDetail.getSource().getName(), trainSearchDetail.getDestination().getName());
//            return;
//        }
//        Formatter.tableTemplate(trains);
//        while(true){
//            System.out.println("Enter 'q' to go to previous menu else enter train number to view available seats.");
//
//            String choice = input.nextLine();
//            if (choice.equalsIgnoreCase("q")){
//                return;
//            }
//
//            try{
//                int trainNumber = Integer.parseInt(choice);
//                if((Objects.isNull(trainSearchDetail.getDate()))){
//                    trainSearchDetail.setDate(getDateInput("Enter date of journey: "));
//                    Optional<Train> train = trains.stream().filter(t -> Objects.equals(t.getTrainNumber(), trainNumber)).findFirst();
//                    train.ifPresentOrElse(t->Formatter.tableTemplate(getAvailableSeatsWithFare(t, trainSearchDetail)), () -> System.out.println("Train number " + trainNumber + " not found.") );
//                }else{
//                    Optional<Train> train = trains.stream().filter(t -> Objects.equals(t.getTrainNumber(), trainNumber)).findFirst();
//                    train.ifPresentOrElse(t->Formatter.tableTemplate(getAvailableSeatsWithFare(t, trainSearchDetail)), () -> System.out.println("Train number " + trainNumber + " not found.") );
//                }
//                trainSearchDetail.setDate(null);
//            }catch (NumberFormatException e){
//                System.out.println("Enter a valid train number.");
//                continue;
//            }
//        }
//    }
//
//    public Response addPassenger(Passenger passenger) {
//        return null;
//    }
//
//    public void bookTicket() {
//        TrainSearchDetail trainSearchInput = getTrainSearchInput();
//        List<Train> trains = filterTrains(trainSearchInput);
//        Formatter.tableTemplate(trains);
//
//        System.out.println("Select your train: ");
//        int trainNumber = input.nextInt();
//        input.nextLine();
//        Train train = trains.stream().filter(t -> t.getTrainNumber() == trainNumber).toList().getFirst();
//
//        List<AvailableSeats> availableSeats = getAvailableSeatsWithFare(train, trainSearchInput);
//        Formatter.tableTemplate(availableSeats);
//        System.out.println("Select your travelling class: ");
//        int travellingClassIndex = input.nextInt();
//        input.nextLine();
//
//        TravellingClass travellingClass = availableSeats.get(travellingClassIndex - 1).getTravellingClass();
//        int availableSeat = availableSeats.stream().filter(x -> x.getTravellingClass().equals(travellingClass)).toList().getFirst().getAvailableSeats();
//        System.out.println("Enter number of passengers");
//        int numberOfPassenger = input.nextInt();
//        input.nextLine();
//
//        List<Passenger> passengers = null;
//        if (numberOfPassenger <= availableSeat) {
//            passengers = getPassengersList(numberOfPassenger);
//        }
//        BookingDetail bookingDetail = new BookingDetail(trainNumber, travellingClass, trainSearchInput.getSource(), trainSearchInput.getDestination(), trainSearchInput.getDate());
//        bookingDetail.setPassengerList(passengers);
//
//        Response bookingResponse = userController.bookTicket(bookingDetail);
//        if (bookingResponse.getStatus().equals(SUCCESS)) {
//            System.out.println(bookingResponse.getMessage());
//            ETicket data = (ETicket) (bookingResponse.getData());
//            Formatter.formatTicket(data.getTransaction());
//        } else {
//            System.out.println(bookingResponse.getMessage());
//        }
//    }
//
//    public void cancelTicket() {
//        System.out.println("Enter PNR: ");
//        int pnr = input.nextInt();
//        response = userController.cancelTicket(pnr);
//        System.out.println(response.getMessage());
//    }
//
//    public void displayTicket() {
//        System.out.println("Enter PNR: ");
//        int pnr = input.nextInt();
//        Response response = userController.getTicket(pnr);
//        if (response.getStatus().equals(SUCCESS)) {
//            ETicket data = (ETicket) (response.getData());
//            Formatter.formatTicket(data.getTransaction());
//        }
//    }
//
//    public void displayTickets() {
//        Response response = userController.getTickets();
//        if (response.getStatus().equals(SUCCESS)) {
//            List<Response> ticketsResponse = (List<Response>) (response.getData());
//            List<Transaction> tickets = ticketsResponse.stream().filter(r->r.getStatus().equals(SUCCESS)).map(r->(Transaction)(r.getData())).toList();
//            Formatter.tableTemplate(tickets);
//        } else {
//            System.out.println(response.getMessage());
//        }
//    }
//
//
//
//    public void logout() {
//        response = userController.logout();
//        System.out.println(response.getMessage());
//    }
//
//    public Response getCurrentUser() {
//        return null;
//    }
//
//    public Response checkLogin() {
//        return null;
//    }
//
//    private Station getStationInput(String inputMsg) {
//        while (true){
//            System.out.println(inputMsg);
//            String stationInput = input.nextLine();
//            Response response = userController.getStationByName(stationInput);
//            if (response.getStatus().equals(SUCCESS)) {
//                return (Station) (response.getData());
//            }else{
//                System.out.println(response.getMessage());
//            }
//        }
//    }
//
//    private LocalDate getDateInput(String inputMsg) {
//        System.out.println("Note: Enter the date in dd/mm/yyyy format.");
//        while(true){
//            System.out.println(inputMsg);
//            String dateInput = input.nextLine();
//            if (Validator.validateDateFormat.test(dateInput)){
//                LocalDate date = Formatter.formatDate(dateInput);
//                if(Validator.dateIsAfterToday.test(date)){
//                    return date;
//                }else{
//                    System.out.println("Enter date of tomorrow or onward.");
//                    continue;
//                }
//            }
//            System.out.println("Invalid Date format");
//        }
//    }
//
//    private TrainSearchDetail getTrainSearchInput() {
//        Station source = getStationInput("Enter Source: ");
//        Station destination = getStationInput("Enter destination: ");
//
//        System.out.println("Do you want to search train for specific date? (Y/N): ");
//        String choice = input.nextLine();
//        LocalDate date = null;
//
//        while(true){
//            if (choice.equalsIgnoreCase("Y")) {
//                date = getDateInput("Enter Date: ");
//                break;
//            } else if (choice.equalsIgnoreCase("N")) {
//                break;
//            } else{
//                System.out.println("Enter a valid input.");
//            }
//        }
//        return new TrainSearchDetail(source, destination, date);
//    }
//
//    private List<Passenger> getPassengersList(int numberOfPassengers) {
//        List<Passenger> passengersList = new ArrayList<>();
//        for (int i = 0; i < numberOfPassengers; i++) {
//            System.out.println("Enter Name: ");
//            String name = input.nextLine();
//            System.out.println("Enter Age: ");
//            int age = input.nextInt();
//            input.nextLine();
//            Passenger passenger = new Passenger(name, age);
//            passengersList.add(passenger);
//        }
//        return passengersList;
//    }
//
//    private List<AvailableSeats> getAvailableSeatsWithFare(Train train, TrainSearchDetail trainSearchDetail) {
//        FareCalculator fareCalculator = new StationBasedFareCalc();
//        response = userController.getAvailableSeats(train.getTrainNumber(), trainSearchDetail.getDate());
//
//        List<AvailableSeats> availableSeatsList = new ArrayList<>();
//        if (response.getStatus().equals(SUCCESS)) {
//            Map<TravellingClass, Integer> availableSeatsData = (Map<TravellingClass, Integer>) response.getData();
//            int count = 1;
//            for (Map.Entry<TravellingClass, Integer> travellingClassIntegerEntry : availableSeatsData.entrySet()) {
//                TravellingClass travellingClass = travellingClassIntegerEntry.getKey();
//                Integer availableSeat = travellingClassIntegerEntry.getValue();
//                AvailableSeats availableSeats1 = new AvailableSeats(count++, travellingClass, availableSeat, fareCalculator.calculateFare(train, travellingClass, trainSearchDetail.getSource(), trainSearchDetail.getDestination()));
//                availableSeatsList.add(availableSeats1);
//            }
//        } else {
//            System.out.println(response.getMessage());
//        }
//        return availableSeatsList;
//    }
//}
