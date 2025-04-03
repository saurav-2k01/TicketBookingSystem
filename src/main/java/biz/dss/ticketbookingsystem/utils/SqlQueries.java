package biz.dss.ticketbookingsystem.utils;

public class SqlQueries {
    /**
     * User dao related queries.
     */
    public static String addUserQuery = "insert into \"user\" (id, name, username, age, gender, email, password, seat_number, user_type, is_logged_in) values (?, ?, ?, ?, ?, ?, ? , ?, ?, ?);";
    public static String getUserByUserName = "select * from \"user\" where username=?;";
    public static String getUserById = "select * from \"user\" where id=?;";
    public static String getUserByEmail = "select * from \"user\" where email=?;";
    public static String updateUserById = "update \"user\" set id=?, name=?, username=?, age=?, gender=?, email=?, password=?, seat_number=?, user_type=?, is_logged_in=? where id = ?";
    public static String deleteUserbyUserName = "delete from \"user\" where username=?";
    public static String getAllUsers = "select * from \"user\"";

    /**
     *  Station dao related queries
     */
    public static String addStation = "insert into \"station\" (id, name, shortname) values(?,?,?)";
    public static String deleteStatoinbyName = "delete from \"station\" where name=?;";
    public static String getStationByShortName = "select * from \"station\" where shortname=?;";
    public static String getStationByName = "select * from \"station\" where name=?;";
    public static String getStationById = "select * from \"station\" where id=?;";
    public static String updateStationById = "update \"station\" set id=?, name=?, shortname=? where id=?";
    public static String getAllStations = "select * from \"station\";";



    /**
     *  Train dao related queries.
     */
    public static String addTrain = "insert into \"train\" (train_number, train_name,source, destination) values(?,?,?,?);";
//    public static String getTrainByTrainNumber = "select * from \"train\" where train_number = ?;";
    public static String deleteTrain = "delete from \"train\" where train_number = ?";
    public static String updateTrain = "update \"train\" set train_number=?, train_name=?, source = ?, destination = ?;";
    public static String updateTrainRoute = "update route set train_number=?, station_id=?, sequence_num=?";
    public static String updateTrainCoach = "update train_coach set train_number=?, coach_id=?;";
    public static String updateTrainRunningDays = "update train_coach set train_number=?, coach_id=?;";
    public static String getAllTrains = "select * from train;";
    public static String getTrainCoachList = "select coach* from;";
    public static String getTrainByTrainNumber = "select train.train_number,train.train_name, \n" +
            "coach.id as coach_id, coach.travelling_class,coach.coach_name, coach.total_seats, coach.available_seats, coach.fare_factor,\n" +
            "station.id as station_id, station.name as station_name, station.short_name, route.sequence_num,\n" +
            "running_days.name as running_day\n" +
            "from train\n" +
            "inner join train_running_days on train.train_number = train_running_days.train_number\n" +
            "inner join running_days on running_days.id = train_running_days.running_day\n" +
            "inner join route on train.train_number = route.train_number\n" +
            "inner join station on station.id = route.station_id\n" +
            "inner join train_coach on train.train_number = train_coach.train_number\n" +
            "inner join coach on coach.id = train_coach.coach_id where train.train_number=?;";

    public static String addTrainBooking = "insert into \"train_bookings\"(train_number, coach_id, available_seats, running_date)\n" +
            "values(?, ?,?,?);" ;
    public static  String getTrainBooking = "select * from train_bookings inner join coach on train_bookings.coach_id = coach.id where id = ?;";
    public static  String getAllTrainBooking = "select * from train_bookings inner join coach on train_bookings.coach_id = coach.id ;";

    public static String addTransaction = "insert into transaction (pnr, date_of_journey, total_fare, is_cancelled, user_id, train_number, source, destination)\n" +
            "values (?,?,?,?,?,?,?,?);";
    public static String updateTrainBooking = "update train_bookings set available_seats = ? where train_number=? and coach_id=? and running_date=?;";
    public static String addPassenger = "insert into \"user\" (id, name, gender, age,seat_number,  user_type) values (?,?,?,?,?,?)";
    public static String mapPassengerTransaction = "insert into transaction_passengers(pnr, passenger)\n" +
            "values(?,?);";
    public static String mapUserTransaction = "insert into \"user_transaction\" (user_id, pnr)\n" +
            "values (?,?);";

    public static String getTransaction = "select transaction.pnr, train_number, source, destination, total_fare, is_cancelled, \n" +
            "\"user\".id as \"user_id\", \n" +
            "\"user\".username as \"username\", \n" +
            "\"user\".age as \"user_age\", \n" +
            "\"user\".gender as \"user_gender\",\n" +
            "\"user\".email as \"user_email\", \n" +
            "\"user\".password as \"password\", \n" +
            "\"user\".seat_number as \"seat_number\", \n" +
            "\"user\".user_type as \"user_user_type\",\n" +
            "\"user\".is_logged_in as \"is_logged_in\",\n" +
            "u.id as \"passenger_id\", \n" +
            "u.name as \"passenger_name\", \n" +
            "u.age as \"passenger_age\", \n" +
            "u.gender as \"passenger_gender\", \n" +
            "u.seat_number as \"passenger_seat_number\", \n" +
            "u.user_type as \"passenger_user_type\"\n" +
            "from \"user\"\n" +
            "inner join user_transaction on \"user\".id = user_transaction.user_id\n" +
            "inner join \"transaction\" on user_transaction.pnr = \"transaction\".pnr\n" +
            "inner join transaction_passengers on \"transaction\".pnr = transaction_passengers.pnr\n" +
            "inner join \"user\" as u on transaction_passengers.passenger = u.id\n" +
            "where \"user\".id = 9713090;";

    //todo implement method for getting train , source and destination from transaction seperately.
}


