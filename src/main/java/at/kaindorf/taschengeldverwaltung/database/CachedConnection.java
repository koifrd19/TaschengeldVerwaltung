package at.kaindorf.taschengeldverwaltung.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class CachedConnection {

    private LinkedList<Statement> statementQueue = new LinkedList<>();
    private Connection connection;

    public CachedConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() throws SQLException {
        if(connection == null){
            throw new RuntimeException("not connected to database - connection is null");
        }

        if(statementQueue.isEmpty()){
            return connection.createStatement();
        }
        else{
            return statementQueue.poll();
        }
    }

    public void releaseStatement(Statement statement){
        statementQueue.offer(statement);
    }

}
