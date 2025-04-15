package biz.dss.ticketbookingsystem.utils;

public class SqlQueries {

    private SqlQueries(){}
    /**
     * User dao related queries.
     */
    public static final String ADD_USER_QUERY = "insert into \"user\" (id, name, username, age, gender, email, password, seat_number, user_type, is_logged_in) values (?, ?, ?, ?, ?, ?, ? , ?, ?, ?);";
    public static final String GET_USER_BY_USERNAME = "select * from \"user\" where username=?;";
    public static final String GET_USER_BY_USERID = "select * from \"user\" where id=?;";
    public static final String GET_USER_BY_EMAIL = "select * from \"user\" where email=?;";
    public static final String UPDATE_USER_BY_USERID = "update \"user\" set id=?, name=?, username=?, age=?, gender=?, email=?, password=?, seat_number=?, user_type=?, is_logged_in=? where id = ?";
    public static final String DELETE_USER_BY_USERNAME = "delete from \"user\" where username=?";
    public static final String GET_ALL_USERS = "select * from \"user\"";

    /**
     * Station dao related queries
     */
    public static final String ADD_STATION = "insert into \"station\" (id, name, shortname) values(?,?,?)";
    public static final String DELETE_STATION_BY_NAME = "delete from \"station\" where name=?;";
    public static final String GET_STATION_BY_SHORTNAME = "select * from \"station\" where shortname=?;";
    public static final String GET_STATION_BY_NAME = "select * from \"station\" where name=?;";
    public static final String GET_STATION_BY_ID = "select * from \"station\" where id=?;";
    public static final String UPDATE_STATION_BY_ID = "update \"station\" set id=?, name=?, shortname=? where id=?";
    public static final String GET_ALL_STATION = "select * from \"station\";";

    /**
     * Train dao related queries.
     */
    public static final String ADD_TRAIN = "insert into \"train\" (train_number, train_name) values(?,?);";
    public static final String DELETE_TRAIN = "delete from \"train\" where train_number = ?";
    public static final String UPDATE_TRAIN = "update \"train\" set train_number=?, train_name=?, source = ?, destination = ? where train_number = ?;";
    public static final String UPDATE_TRAIN_ROUTE = """
            insert into route(train_number, station_id, seq_number) values(?,?,?)
            on conflict(train_number, station_id) do update set train_number =?, station_id=?,seq_number=?;
            """;
    public static final String UPDATE_TRAIN_COACH = """
            insert into train_coach(train_number, coach_id) values (?,?)
            on conflict (train_number, coach_id) do update set train_number=?, coach_id=?;
            """;
    public static final String UPDATE_TRAIN_RUNNING_DAYS = """
            insert into train_running_days(train_number, running_day) values(?,?)
            on conflict (train_number, running_day) do update set train_number = ?, running_day = ?;
            """;
    public static final String GET_ALL_TRAINS = "select * from train;";
    public static final String GET_TRAIN_NUMBERS = "select train_number from train;";
    public static final String GET_TRAIN_COACH_LIST = "select coach* from;";
    public static final String ADD_COACH = """
            insert into coach(id, travelling_class, coach_name, total_seats,fare_factor)
            values(?,?,?,?,?)
            on conflict (id) do update
            set id = ?, travelling_class = ?, coach_name = ?, total_seats = ?, fare_factor = ?
            ;""";
    public static final String GET_TRAIN_BY_TRAIN_NUMBER = """
            select train.train_number,train.train_name,
                        coach.id as coach_id, coach.travelling_class,coach.coach_name, coach.total_seats,coach.fare_factor,
                        station.id as station_id, station.name as station_name, station.short_name, route.seq_number,
                        running_days.name as running_day
                        from train
                        left join train_running_days on train.train_number = train_running_days.train_number
                        left join running_days on running_days.id = train_running_days.running_day
                        left join route on train.train_number = route.train_number
                        left join station on station.id = route.station_id
                        left join train_coach on train.train_number = train_coach.train_number
                        left join coach on coach.id = train_coach.coach_id where train.train_number=?
            ;
            """;
    public static final String REMOVE_RUNNING_DAYS = "delete from train_running_days where train_number= ? and running_day=?";
    public static final String REMOVE_COACH = "delete from train_coach where train_number= ? and coach_id=?";
    public static final String REMOVE_STATION = "delete from route where train_number = ? and station_id=?";

    /**
     * TrainBooking doa related queries.
     */
    public static final String ADD_TRAIN_BOOKING = "insert into \"train_bookings\"(train_number, coach_id, available_seats, running_date)\n" + "values(?, ?,?,?);";
    public static final String GET_TRAIN_BOOKING = "select * from train_bookings inner join coach on train_bookings.coach_id = coach.id where id = ?;";
    public static final String GET_ALL_TRAIN_BOOKING = "select * from train_bookings inner join coach on train_bookings.coach_id = coach.id ;";

    /**
     *  Transaction doa related queries
     */
    public static final String ADD_TRANSACTION = "insert into transaction (pnr, date_of_journey, total_fare, is_cancelled, user_id, train_number, source, destination)\n" + "values (?,?,?,?,?,?,?,?);";
    public static final String GET_ALL_PNR = "select pnr from transaction;";
    public static final String UPDATE_TRAIN_BOOKING = "update train_bookings set available_seats = ? where train_number=? and coach_id=? and running_date=?;";
    public static final String ADD_PASSENGER = "insert into \"user\" (id, name, gender, age,seat_number,  user_type) values (?,?,?,?,?,?)";
    public static final String MAP_PASSENGER_TRANSACTION = "insert into transaction_passengers(pnr, passenger)\n" + "values(?,?);";
    public static final String MAP_USER_TRANSACTIONS = "insert into \"user_transaction\" (user_id, pnr)\n" + "values (?,?);";
    public static final String CANCEL_TICKET = "update \"transaction\" set is_cancelled=true where pnr = ?;";
    public static final String GET_TRANSACTION = """
            select transaction.pnr, train_number, source, destination, total_fare, is_cancelled, date_of_journey,\s
            "user".id as "user_id",\s
            "user".username as "user_name",\s
            "user".username as "user_username",\s
            "user".age as "user_age",\s
            "user".gender as "user_gender",
            "user".email as "user_email",\s
            "user".password as "password",\s
            "user".seat_number as "seat_number",\s
            "user".user_type as "user_user_type",
            "user".is_logged_in as "is_logged_in",
            u.id as "passenger_id",\s
            u.name as "passenger_name",\s
            u.age as "passenger_age",\s
            u.gender as "passenger_gender",\s
            u.seat_number as "passenger_seat_number",\s
            u.user_type as "passenger_user_type"
            from "user"
            inner join user_transaction on "user".id = user_transaction.user_id
            inner join "transaction" on user_transaction.pnr = "transaction".pnr
            inner join transaction_passengers on "transaction".pnr = transaction_passengers.pnr
            inner join "user" as u on transaction_passengers.passenger = u.id
            where transaction.pnr = ?;
            """;
    public static final String GET_TRANSACTIONS = """
            select transaction.pnr, train_number, source, destination, total_fare, is_cancelled, date_of_journey,
            "user".id as "user_id",
            "user".username as "user_name",
            "user".username as "user_username",
            "user".age as "user_age",
            "user".gender as "user_gender",
            "user".email as "user_email",
            "user".password as "password",
            "user".seat_number as "seat_number",
            "user".user_type as "user_user_type",
            "user".is_logged_in as "is_logged_in",
            u.id as "passenger_id",
            u.name as "passenger_name",
            u.age as "passenger_age",
            u.gender as "passenger_gender",
            u.seat_number as "passenger_seat_number",
            u.user_type as "passenger_user_type"
            from "user"
            inner join user_transaction on "user".id = user_transaction.user_id
            inner join "transaction" on user_transaction.pnr = "transaction".pnr
            inner join transaction_passengers on "transaction".pnr = transaction_passengers.pnr
            inner join "user" as u on transaction_passengers.passenger = u.id
            where "user".id=?;
            """;
}
