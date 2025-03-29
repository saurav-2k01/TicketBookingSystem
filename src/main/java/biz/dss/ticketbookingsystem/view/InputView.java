package biz.dss.ticketbookingsystem.view;

import biz.dss.ticketbookingsystem.controller.StationController;
import biz.dss.ticketbookingsystem.controller.UserController;
import biz.dss.ticketbookingsystem.enums.Gender;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.Formatter;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.utils.UtilClass;
import biz.dss.ticketbookingsystem.utils.Validator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static biz.dss.ticketbookingsystem.enums.UserType.PASSENGER;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

public class InputView {
    private UserController userController;
    private StationController stationController;
    private final Scanner input = UtilClass.scanner;

    public InputView(UserController userController, StationController stationController) {
        this.userController = userController;
        this.stationController = stationController;
    }
//    name
//    username
//    email
//    password
//    date
//    station
//    age
//    choice
//    totalseats
//    farefactor
//    running day

    public String getName(String inputMsg) {
        while (1 > 0) {
            try {
                System.out.print(inputMsg);
                String name = input.nextLine();
                if (name.length() < 2 || name.length() > 25) {
                    System.out.println("Enter a valid name, the name should be minimum 3 character and maximum 25 character.");
                } else {
                    return name;
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Enter a valid choice.");
            }
        }
    }

    public Gender getGender(String inputMsg) {
        while (1 > 0) {
            try {
                Arrays.stream(Gender.values()).forEach(g -> System.out.printf("[%d] %s%n", g.ordinal() + 1, g));

                Integer genderChoice = getIntegerInput(inputMsg);
                if ((genderChoice > 0 && (genderChoice - 1) < Gender.values().length)) {
                    Optional<Gender> gender = Arrays.stream(Gender.values()).filter(gender1 -> gender1.ordinal() == genderChoice - 1).findFirst();
                    if (gender.isPresent()) {
                        return gender.get();
                    }
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Enter a valid choice");
            }
        }
    }

    public Integer getAge(String inputMsg, int max, int min) {
        while (true) {
            int ageInput = getIntegerInput(inputMsg);
            if (ageInput < min) {
                System.out.printf("Person should be older than or equivalent to %d years.%n", min);
                continue;
            }
            if (ageInput > max) {
                System.out.printf("Person should be younger than or equivalent to %d years .%n", max);
                continue;
            }
            return ageInput;
        }
    }

    public String getEmail(String inputMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                String userInput = input.nextLine();
                if (userInput.isBlank()) {
                    System.out.println("Don't enter blank input.");
                    continue;
                }
                if (Boolean.FALSE.equals(Validator.validateEmailFormat.test(userInput))) {
                    System.out.println("Enter a valid email.");
                    continue;
                }
                return userInput;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid input.");
            }
        }
    }

    public String getPassword(String inputMsg) {
        System.out.println("Enter '0' anytime to go back to previous menu.");
        System.out.println("Note: The password must contain 8 to 64 characters, at least one uppercase letter, one lowercase letter, one digit, and one special character.");
        while (true) {
            System.out.print(inputMsg);
            String userInput = input.nextLine();
            if (userInput.equals("0")) return "0";

            if (Validator.validatePassword.test(userInput)) {
                return userInput;
            }
            System.out.println("Enter a valid password");
        }
    }

    public LocalDate getDate(String inputMsg) {
        System.out.println("Note: Enter the date in dd/mm/yyyy format.");
        while (true) {
            System.out.println(inputMsg);
            String dateInput = input.nextLine();
            if (Validator.validateDateFormat.test(dateInput)) {
                LocalDate date = Formatter.formatDate(dateInput);
                if (Validator.dateIsAfterToday.test(date)) {
                    return date;
                } else {
                    System.out.println("Enter date of tomorrow or onward.");
                    continue;
                }
            }
            System.out.println("Invalid Date format");
        }
    }

    public Station getStation(String inputMsg) {
        while (true) {
            System.out.print(inputMsg);
            String stationInput = input.nextLine();
            Response response = stationController.getStationByName(stationInput);
            if (response.getStatus().equals(SUCCESS)) {
                Station station = (Station) (response.getData());
                System.out.printf("%s has been selected.%n", station.getName());
                return station;
            } else {
                System.out.println(response.getMessage());
            }
        }
    }

    public DayOfWeek getRunningDay(String inputMsg) {
        while(1>0){
            try{
                System.out.println(inputMsg);
                String dayOfTheWeek = input.nextLine();
                DayOfWeek day = DayOfWeek.valueOf(dayOfTheWeek.toUpperCase());
                return day;
            } catch (Exception e) {
                System.out.println("Enter a valid input.");
            }
        }
    }

    public Integer getChoice(String inputMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                Integer choice = input.nextInt();
                input.nextLine();
                return choice;
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Enter a valid choice.");
                input.nextLine();
            }
        }

    }

    public String getStringInput(String inputMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                String userInput = input.nextLine();

                if (userInput.isBlank()) {
                    System.out.println("Don't enter blank input.");
                    continue;
                }

                return userInput;
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Enter a valid input.");
            }
        }
    }

    public Integer getIntegerInput(String inputMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                Integer userInput = input.nextInt();
                input.nextLine();
                return userInput;
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Enter a valid input.");
                input.nextLine();
            }
        }
    }

    public Double getDoubleInput(String inputMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                Double userInput = input.nextDouble();
                input.nextLine();
                return userInput;
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Enter a valid input.");
            }
        }
    }

    public List<User> getPassengersList() {
        System.out.println("Enter number of passengers");
        int numberOfPassenger = input.nextInt();
        input.nextLine();
        List<User> passengersList = new ArrayList<>();
        for (int i = 0; i < numberOfPassenger; i++) {
            System.out.printf("============Passenger-%d============%n", i+1);
            String name = getName("Name: ");

            int age = getAge("Age", 100, 0);
            Gender gender = getGender("Gender: ");
            User passenger = User.builder().name(name).age(age).gender(gender).userType(PASSENGER).build();
            passengersList.add(passenger);
            System.out.printf("Added Passenger-%d%n", i+1);
        }
        return passengersList;
    }

    public TravellingClass getTravellingClass(String inputMsg, List<TravellingClass> travellingClasses){

        while (1 > 0) {
            try {
//                travellingClasses.forEach(t-> System.out.printf("[%d] %s%n", t.ordinal()+1, t));

                Integer travellingClassChoice = getIntegerInput(inputMsg);
                if ((travellingClassChoice > 0 && (travellingClassChoice - 1) < travellingClasses.size())) {
                    TravellingClass travellingClass = travellingClasses.get(travellingClassChoice-1);
                    if (Boolean.FALSE.equals(Objects.isNull(travellingClass))) {
                        return travellingClass;
                    }
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Enter a valid choice");
            }
        }
    }


}
