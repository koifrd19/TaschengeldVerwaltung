package at.kaindorf.taschengeldverwaltung.database;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
     private static Database theInstance;

    private CachedConnection cachedConnection;

    private Connection connection;



//    Database Properties:
    private static String url = "jdbc:postgresql://localhost:5432/Taschengeldverwaltung";
    private static String driver = "org.postgresql.Driver";
    private static String username = "postgres";
    private static String password = "postgres";

    private Database() {
        try {
//            URL url1 = this.getClass().getClassLoader().getResource("database.properties");
//            loadProperties(url1);
            Class.forName(driver);
            connect();
            cachedConnection = new CachedConnection(connection);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Could not load Database-Driver ");
            System.err.println(driver);
            throw new RuntimeException(e);
        }
    }

    private static void loadProperties(URL url1){
        File propFile;
        try {
            propFile = new File(url1.toURI());
        } catch (URISyntaxException e) {
            System.err.println("Error during File creation");
            throw new RuntimeException(e);
        }
        InputStream is;
        try {
            is = new FileInputStream(propFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        java.util.Properties props = new java.util.Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = props.getProperty("url");
        driver = Properties.getProperty("driver");
        username = Properties.getProperty("username");
        password = Properties.getProperty("password");
    }

    public static Database getTheInstance(){
        if (theInstance == null){
            theInstance = new Database();
        }
        return theInstance;
    }


    private void connect() throws SQLException {
        connection = DriverManager.getConnection(url,username,password);
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    public Connection getConnection(){
        return connection;
    }

    public void releaseStatement(Statement statement){
        cachedConnection.releaseStatement(statement);
    }

    public Statement getStatement() throws SQLException {
        return cachedConnection.getStatement();
    }

    public static void main(String[] args) {
//        System.out.println(driver);

        Database db = getTheInstance();
        try {
            System.out.println(db.connection.isClosed());
            db.disconnect();
            System.out.println(db.connection.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
