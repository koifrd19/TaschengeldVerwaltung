package at.kaindorf.taschengeldverwaltung.database;


import at.kaindorf.taschengeldverwaltung.pojos.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Access {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static Access theInstance;

    private static Database dbInstance;

    private Access() {
        dbInstance = Database.getTheInstance();
    }

    public static Access getTheInstance(){
        if (theInstance == null){
            theInstance = new Access();
        }
        return theInstance;
    }

    public List<VillagerPerson> getAllVillagers() throws SQLException {
        Statement statement = dbInstance.getStatement();
        List<VillagerPerson> villagerPeople = new ArrayList<>();

        String sqlString = "SELECT * FROM \"villager\" \"be\" INNER JOIN \"person\" \"pers\" ON be.\"villager_id\" = pers.\"person_id\"\n" +
                "                            INNER JOIN \"salutation\" \"anr\" ON pers.\"salutation_id\" = anr.\"salutation_id\";";

        ResultSet results = statement.executeQuery(sqlString);

        while (results.next()){
            villagerPeople.add(new VillagerPerson(results.getLong("villager_id"),
                    results.getString("firstname"),
                    results.getString("lastname"),
                    results.getString("title_before"),
                    results.getString("title_after"),
                    new Salutation(results.getLong("salutation_id"), results.getString("Bezeichnung")),
                    results.getString("short_sign"),
                    results.getDate("date_of_birth").toLocalDate(),
                    results.getDate("date_of_exit").toLocalDate(),
                    results.getString("note"),
                    new Person(results.getLong("trusted_person")),
                    new ArrayList<>()));
        }
        dbInstance.releaseStatement(statement);
        return villagerPeople;
    }

    public static void main(String[] args) {
        try {

            System.out.println(getTheInstance().getPersonOfTrustById(523L));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TrustedPerson getPersonOfTrustById(Long id) throws SQLException {
        Statement statement = dbInstance.getStatement();

        String sqlString = "SELECT *\n" +
                "FROM \"person\" p INNER JOIN \"Vertrauensperson\" vp ON p.\"person_id\" = vp.\"trusted_person_id\"\n" +
                "                INNER JOIN \"transmission_method\" v ON vp.\"transmission_method\" = v.\"transmission_method_id\"\n" +
                "                INNER JOIN \"Beziehung\" b ON vp.\"Beziehung\" = b.\"BeziehungsID\"" +
                "                INNER JOIN \"salutation\" a ON p.\"salutation_id\" = a.\"salutation_id\"" +
                "WHERE p.\"person_id\" = "+ id+ ";\n";

        ResultSet results = statement.executeQuery(sqlString);

        dbInstance.releaseStatement(statement);
        results.next();


           return new TrustedPerson(results.getLong("person_id"),
                    results.getString("firstname"),
                    results.getString("lastname"),
                    results.getString("title_before"),
                    results.getString("title_after"),
                    new Salutation(results.getLong("salutation_id"), results.getString(21)),
                    results.getString("EMail"),
                    results.getString("Telefonnummer"),
                    results.getString("Ort"),
                    results.getString("PLZ"),
                    results.getString("Strasse"),
                    results.getString("Hausnummer"),
                    new Relation(
                            results.getInt("BeziehungsID"),
                            results.getString(19)
                    ),
                    new TransmissionMethod(
                            results.getLong("transmission_method_id"),
                            results.getString(17)
                    ));
    }

    public VillagerPerson getVillagerById(Long id) throws SQLException {
        Statement statement = dbInstance.getStatement();

        String sqlString = "SELECT *\n" +
                "FROM \"person\" p INNER JOIN \"villager\" vp ON p.\"person_id\" = vp.\"trusted_person_id\"\n" +
                "                INNER JOIN \"transmission_method\" v ON vp.\"transmission_method\" = v.\"transmission_method_id\"\n" +
                "                INNER JOIN \"salutation\" a ON p.\"salutation_id\" = a.\"salutation_id\"" +
                "WHERE p.\"person_id\" = "+ id+ ";\n";

        ResultSet results = statement.executeQuery(sqlString);

        dbInstance.releaseStatement(statement);
        results.next();


        return new VillagerPerson(results.getLong("person_id"),
                results.getString("firstname"),
                results.getString("lastname"),
                results.getString("title_before"),
                results.getString("title_after"),
                new Salutation(results.getLong("salutation_id"), results.getString(21)),
                results.getString("short_sign"),
                results.getDate("date_of_birth").toLocalDate(),
                results.getDate("date_of_exit").toLocalDate(),
                results.getString("note"),
                new Person(results.getLong("person_id")), // TODO: change to person of trust
                new ArrayList<>()
                );
    }

    public Booking getBookingById(){
        return null;
    }

    public List<Booking> getVillagerBookingHistory(Long personId) throws SQLException {
        Statement statement = dbInstance.getStatement();
        List<Booking> bookings = new ArrayList<>();

        String sqlString = "SELECT *\n" +
                "FROM \"booking\" b\n" +
                "    INNER JOIN \"user\" ON b.\"user\" = \"user_id\"\n" +
                "    INNER JOIN \"purpose\" z ON b.\"purpose_id\" = z.\"purpose_id\"" +
                "WHERE \"villager_id\" = "+ personId+ ";";

        ResultSet results = statement.executeQuery(sqlString);

        while (results.next()){
            bookings.add(
                    new Booking(results.getLong("villager_id"),
                            results.getDate("date").toLocalDate()
                            .atTime(results.getTime("date").toLocalTime()),
                            results.getString("username"),
                            results.getFloat("amount"),
                            results.getLong("receipt_nr"),
                            results.getString("note"),
                            new Purpose(
                                    results.getLong("purpose_id"),
                                    results.getString("text"),
                                    results.getShort("multiplier"),
                                    results.getBoolean("Status"))
                            )
            );
        }
        dbInstance.releaseStatement(statement);
        return bookings;
    }

    public List<Booking> getAllVillagersBookingHistory(String sortedBy) throws SQLException {
//        sortedBy specifies the column to be sorted

        Statement statement = dbInstance.getStatement();
        List<Booking> bookings = new ArrayList<>();

        String sqlString = "SELECT *\n" +
                "FROM \"booking\" b\n" +
                "    INNER JOIN \"Benutzer\" ON b.\"user\" = \"user_id\"\n" +
                "    INNER JOIN \"purpose\" z ON b.\"purpose_id\" = z.\"purpose_id\"";

        ResultSet results = statement.executeQuery(sqlString);

        while (results.next()){
            bookings.add(
                    new Booking(results.getLong("villager_id"),
                            results.getDate("date").toLocalDate()
                            .atTime(results.getTime("date").toLocalTime()),
                            results.getString("username"),
                            results.getFloat("amount"),
                            results.getLong("receipt_nr"),
                            results.getString("note"),
                            new Purpose(
                                    results.getLong("purpose_id"),
                                    results.getString("text"),
                                    results.getShort("multiplier"),
                                    results.getBoolean("status"))
                    )
            );
        }
        dbInstance.releaseStatement(statement);

        switch (sortedBy.toLowerCase()){
            case "date" -> bookings.stream().sorted(Comparator.comparing(Booking::getDateTime));
            case "user" -> bookings.stream().sorted(Comparator.comparing(Booking::getUsername));
            default -> bookings.stream().sorted(Comparator.comparing(Booking::getReceiptNumber));
        }
        return bookings;
    }

    private Long getIdOfUser(String username) throws SQLException {
        Statement statement = dbInstance.getStatement();
        String sqlString = "SELECT \"user_id\"\n" +
                "FROM \"user\"\n" +
                "WHERE \"username\" = "+ username+";";

        ResultSet results = statement.executeQuery(sqlString);
        results.next();
        dbInstance.releaseStatement(statement);
        return results.getLong("user_id");
    }

    public void insertFastBooking(Long personId, Booking booking) throws SQLException {
        LocalDateTime dateOfBooking = booking.getDateTime();

        String sqlString = String.format("INSERT INTO public.\"booking\"(\n" +
                "    \"villager_id\", \"date\", \"purpose_id\", \"amount\", \"receipt_nr\", \"note\", \"user\")\n" +
                "VALUES (%d, '%s', %d, %f, %d, '%s', %s);", personId, dateOfBooking.format(DTF),
                booking.getPurpose().getId(),
                booking.getValue(),
                booking.getReceiptNumber(),
                booking.getNote(),
                getIdOfUser(booking.getUsername())
                );
        Statement statement = dbInstance.getStatement();
        statement.execute(sqlString);

        dbInstance.releaseStatement(statement);
    }

    public BalanceOverview getBalanceList() throws SQLException {

        String sqlString = "SELECT *\n" +
                "FROM \"booking\" b\n" +
                "    INNER JOIN \"person\" be ON b.\"villager_id\" = be.\"person_id\"\n" +
                "    INNER JOIN \"purpose\" z ON b.\"purpose_id\" = z.\"purpose_id\";";

        Statement statement = dbInstance.getStatement();

        ResultSet results = statement.executeQuery(sqlString);
        Map<Person, Double> balanceMap = new HashMap<>();


        while (results.next()){
            Person person = new Person(
                    results.getLong("villager_id"),
                    results.getString("firstname"),
                    results.getString("lastname"));

            if (balanceMap.containsKey(person)){
                Double balance = balanceMap.get(person);
                balance += (results.getDouble("amount") * results.getShort("multiplier"));
                balanceMap.put(person,balance);
                continue;
            }

            balanceMap.put(person, (results.getDouble("amount") * results.getShort("multiplier")));
        }
        dbInstance.releaseStatement(statement);

        List<Balance> balanceList = new ArrayList<>();

        for (Person person : balanceMap.keySet()){
            balanceList.add(new Balance(person.getId(), person.getFirstName(),
                    person.getLastName(), balanceMap.get(person)));
        }

        return new BalanceOverview(getOverallBalance(balanceList), balanceList);
    }

    private Double getOverallBalance(List<Balance> balanceList) {
        return balanceList.stream()
                .mapToDouble(b -> b.getBalance())
                .sum();
    }

    public BalanceOverview getAccountingJournal(Long personId) throws SQLException {
        String sqlString = "SELECT *\n" +
                "FROM \"booking\" b\n" +
                "    INNER JOIN \"person\" be ON b.\"villager_id\" = be.\"person_id\"\n" +
                "    INNER JOIN \"purpose\" z ON b.\"purpose_id\" = z.\"purpose_id\";";

        Statement statement = dbInstance.getStatement();

        ResultSet results = statement.executeQuery(sqlString);
        List<Balance> balanceList = new ArrayList<>();
        BalanceOverview balanceOverview = new BalanceOverview(0.0,0.0,0.0);

        while (results.next()){
            Double value = (results.getDouble("amount") * results.getShort("multiplier"));

            balanceList.add(new Balance(
                        results.getLong("villager_id"),
                        results.getString("firstname"),
                        results.getString("lastname"),
                        value,
                        results.getLong("receipt_nr"),
                        results.getDate("date").toLocalDate(),
                        results.getString("text")
            ));

            balanceOverview.setSum(balanceOverview.getSum()+value);
            if (value<0){
                balanceOverview.setExpenses(balanceOverview.getExpenses()+value);
                continue;
            }
            balanceOverview.setIncome(balanceOverview.getIncome()+value);
        }
        dbInstance.releaseStatement(statement);
        if (personId != null){
            balanceList = balanceList.stream()
                    .filter(balance -> balance.getVillagerId().equals(personId))
                    .collect(Collectors.toList());
        }
        balanceOverview.setBalanceList(balanceList);
        return balanceOverview;
    }





//    LocalDateTime ld = LocalDateTime.now();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        System.out.println(ld.format(dtf));
}
