package at.kaindorf.taschengeldverwaltung.bl;

import at.kaindorf.taschengeldverwaltung.database.Access;

import java.sql.SQLException;

public class DatabaseRequestHandler {
    private static DatabaseRequestHandler instance;
    private static Access accessInstance;

    private DatabaseRequestHandler() {
        accessInstance = Access.getTheInstance();
    }

    public static DatabaseRequestHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseRequestHandler();
        }
        return instance;
    }

    public String exportSaldenliste() {
        return null;
    }

    public String exportDepotauskuenfte() {
        return null;
    }

    public String exportDepotauskuenfteNegativ() {
        return null;
    }

    public String exportDepotAuskunftEinzel() {
        return null;
    }

    public Access getAccessInstance() {return accessInstance;}

    public static void main(String[] args) {
        DatabaseRequestHandler handler = getInstance();
        try {
            handler.getAccessInstance().getAllVillagers().forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
