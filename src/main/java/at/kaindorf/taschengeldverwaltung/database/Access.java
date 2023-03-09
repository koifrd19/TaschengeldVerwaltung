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

        String sqlString = "SELECT * FROM \"Bewohner\" \"be\" INNER JOIN \"Person\" \"pers\" ON be.\"BewohnerID\" = pers.\"PersonID\"\n" +
                "                            INNER JOIN \"Anrede\" \"anr\" ON pers.\"AnredeID\" = anr.\"AnredeID\";";

        ResultSet results = statement.executeQuery(sqlString);

        while (results.next()){
            villagerPeople.add(new VillagerPerson(results.getLong("BewohnerID"),
                    results.getString("Vorname"),
                    results.getString("Nachname"),
                    results.getString("TitelVor"),
                    results.getString("TitelNach"),
                    new Salutation(results.getLong("AnredeID"), results.getString("Bezeichnung")),
                    results.getString("Kurzzeichen"),
                    results.getDate("Geburtsdatum").toLocalDate(),
                    results.getDate("Austrittsdatum").toLocalDate(),
                    results.getString("Bemerkung"),
                    new Person(results.getLong("Vertrauensperson")),
                    new ArrayList<>()));
        }
        dbInstance.releaseStatement(statement);
        return villagerPeople;
    }

    public TrustedPerson getPersonOfTrustById(Long id) throws SQLException {
        Statement statement = dbInstance.getStatement();

        String sqlString = "SELECT *\n" +
                "FROM \"Person\" p INNER JOIN \"Vertrauensperson\" vp ON p.\"PersonID\" = vp.\"VertrauenspersonID\"\n" +
                "                INNER JOIN \"Versandart\" v ON vp.\"Versandart\" = v.\"VersandartID\"\n" +
                "                INNER JOIN \"Beziehung\" b ON vp.\"Beziehung\" = b.\"BeziehungsID\"" +
                "                INNER JOIN \"Anrede\" a ON p.\"AnredeID\" = a.\"AnredeID\"" +
                "WHERE p.\"PersonID\" = "+ id+ ";\n";

        ResultSet results = statement.executeQuery(sqlString);

        dbInstance.releaseStatement(statement);
        results.next();


           return new TrustedPerson(results.getLong("PersonID"),
                    results.getString("Vorname"),
                    results.getString("Nachname"),
                    results.getString("TitelVor"),
                    results.getString("TitelNach"),
                    new Salutation(results.getLong("AnredeID"), results.getString(21)),
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
                            results.getLong("VersandartID"),
                            results.getString(17)
                    ));
    }

    public VillagerPerson getVillagerById(Long id) throws SQLException {
        Statement statement = dbInstance.getStatement();

        String sqlString = "SELECT *\n" +
                "FROM \"Person\" p INNER JOIN \"Bewohner\" vp ON p.\"PersonID\" = vp.\"VertrauenspersonID\"\n" +
                "                INNER JOIN \"Versandart\" v ON vp.\"Versandart\" = v.\"VersandartID\"\n" +
                "                INNER JOIN \"Anrede\" a ON p.\"AnredeID\" = a.\"AnredeID\"" +
                "WHERE p.\"PersonID\" = "+ id+ ";\n";

        ResultSet results = statement.executeQuery(sqlString);

        dbInstance.releaseStatement(statement);
        results.next();


        return new VillagerPerson(results.getLong("PersonID"),
                results.getString("Vorname"),
                results.getString("Nachname"),
                results.getString("TitelVor"),
                results.getString("TitelNach"),
                new Salutation(results.getLong("AnredeID"), results.getString(21)),
                results.getString("short_sign"),
                results.getDate("date_of_birth").toLocalDate(),
                results.getDate("date_of_exit").toLocalDate(),
                results.getString("note"),
                new Person(results.getLong("person_id")),
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
                "FROM \"Buchung\" b\n" +
                "    INNER JOIN \"Benutzer\" ON b.\"User\" = \"BenutzerID\"\n" +
                "    INNER JOIN \"Zweck\" z ON b.\"ZweckID\" = z.\"ZweckID\"" +
                "WHERE \"BewohnerID\" = "+ personId+ ";";

        ResultSet results = statement.executeQuery(sqlString);

        while (results.next()){
            bookings.add(
                    new Booking(results.getLong("BewohnerID"),
                            results.getDate("Datum").toLocalDate()
                            .atTime(results.getTime("Datum").toLocalTime()),
                            results.getString("Benutzername"),
                            results.getFloat("Betrag"),
                            results.getLong("Belegnummer"),
                            results.getString("Anmerkung"),
                            new Purpose(
                                    results.getLong("ZweckID"),
                                    results.getString("Text"),
                                    results.getShort("Mulitplikator"),
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
                "FROM \"Buchung\" b\n" +
                "    INNER JOIN \"Benutzer\" ON b.\"User\" = \"BenutzerID\"\n" +
                "    INNER JOIN \"Zweck\" z ON b.\"ZweckID\" = z.\"ZweckID\"";

        ResultSet results = statement.executeQuery(sqlString);

        while (results.next()){
            bookings.add(
                    new Booking(results.getLong("BewohnerID"),
                            results.getDate("Datum").toLocalDate()
                            .atTime(results.getTime("Datum").toLocalTime()),
                            results.getString("Benutzername"),
                            results.getFloat("Betrag"),
                            results.getLong("BelegNr"),
                            results.getString("Anmerkung"),
                            new Purpose(
                                    results.getLong("ZweckID"),
                                    results.getString("Text"),
                                    results.getShort("Multiplikator"),
                                    results.getBoolean("Status"))
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
        String sqlString = "SELECT \"BenutzerID\"\n" +
                "FROM \"Benutzer\"\n" +
                "WHERE \"Benutzername\" = "+ username+";";

        ResultSet results = statement.executeQuery(sqlString);
        results.next();
        dbInstance.releaseStatement(statement);
        return results.getLong("BenutzerID");
    }

    public void insertFastBooking(Long personId, Booking booking) throws SQLException {
        LocalDateTime dateOfBooking = booking.getDateTime();

        String sqlString = String.format("INSERT INTO public.\"Buchung\"(\n" +
                "    \"BewohnerID\", \"Datum\", \"ZweckID\", \"Betrag\", \"BelegNr\", \"Anmerkung\", \"User\")\n" +
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
                "FROM \"Buchung\" b\n" +
                "    INNER JOIN \"Person\" be ON b.\"BewohnerID\" = be.\"PersonID\"\n" +
                "    INNER JOIN \"Zweck\" z ON b.\"ZweckID\" = z.\"ZweckID\";";

        Statement statement = dbInstance.getStatement();

        ResultSet results = statement.executeQuery(sqlString);
        Map<Person, Double> balanceMap = new HashMap<>();


        while (results.next()){
            Person person = new Person(
                    results.getLong("BewohnerID"),
                    results.getString("Vorname"),
                    results.getString("Nachname"));

            if (balanceMap.containsKey(person)){
                Double balance = balanceMap.get(person);
                balance += (results.getDouble("Betrag") * results.getShort("Multiplikator"));
                balanceMap.put(person,balance);
                continue;
            }

            balanceMap.put(person, (results.getDouble("Betrag") * results.getShort("Multiplikator")));
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
                "FROM \"Buchung\" b\n" +
                "    INNER JOIN \"Person\" be ON b.\"BewohnerID\" = be.\"PersonID\"\n" +
                "    INNER JOIN \"Zweck\" z ON b.\"ZweckID\" = z.\"ZweckID\";";

        Statement statement = dbInstance.getStatement();

        ResultSet results = statement.executeQuery(sqlString);
        List<Balance> balanceList = new ArrayList<>();
        BalanceOverview balanceOverview = new BalanceOverview(0.0,0.0,0.0);

        while (results.next()){
            Double value = (results.getDouble("Betrag") * results.getShort("Multiplikator"));

            balanceList.add(new Balance(
                        results.getLong("BewohnerID"),
                        results.getString("Vorname"),
                        results.getString("Nachname"),
                        value,
                        results.getLong("BelegNr"),
                        results.getDate("Datum").toLocalDate(),
                        results.getString("Text")
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


    public static void main(String[] args) {
        short kurz = -1;
        try {
//            getTheInstance().insertFastBooking(62L,new Booking(62L,LocalDateTime.now(),"'admin'", 99.99f,11L, "", new Purpose(5L, "", kurz, true)));
//            getTheInstance().getBalanceList().forEach(System.out::println);
            System.out.println(getTheInstance().getBalanceList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

//    public static void main(String[] args) {
//        try {
//            getTheInstance().getAllVillagersBookingHistory("date").forEach(System.out::println);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }



//    LocalDateTime ld = LocalDateTime.now();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        System.out.println(ld.format(dtf));
}
