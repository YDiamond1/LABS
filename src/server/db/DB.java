package Server.db;
import java.sql.*;
public class DB {
    public Connection connection = null;
    private String url = "jdbc:postgresql://%s:%d/%s";
    public volatile boolean OK=true;
    public DB(String _host, int _port, String _dbName) {
        url = String.format(url, _host, _port, _dbName);
    }



    public void connect(String _login, String _password) throws DBException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException | NullPointerException e) {
            throw new DBException("Could not find org.postgresql.Driver.");
        }

        if (connection == null)
            try {
                connection = DriverManager.getConnection(url, _login, _password);
            } catch (SQLException e) {
                throw new DBException("Wrong login/password: " + e.getMessage());
            }
    }

    public void close() throws DBException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DBException("Cannt close: " + e.getMessage());
        }
    }
/*
    public ResultSet request(String _pgSQLRequest, DBActionType _actionType) {
        ResultSet list =null;
        OK = false;
        try {
            switch (_actionType) {
                case GET: {
                    PreparedStatement statement = connection.prepareStatement(_pgSQLRequest);
                    statement.
                    OK = true;
                    return statement.executeQuery();
                }
                case UPDATE:{
                    PreparedStatement statement = connection.prepareStatement(_pgSQLRequest);
                    statement.executeUpdate();
                    OK = true;
                }
            }
        }catch (SQLException ex){
            System.out.println("DB isn't available: "+ex.getMessage());
        }
        return list;

    }*/
}
