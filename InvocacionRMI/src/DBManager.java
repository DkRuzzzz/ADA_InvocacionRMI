import java.sql.*;
/**
 * DBManager: Singleton pattern
 *
 *
 **/
public final class DBManager {
    private static DBManager _instance = null;
    private Connection _con = null;
    public DBManager() {
//Connect to Ms Access
        //_con = getMsAccessConnection();
//Connect to MySQL
        _con = getMySQLConnection();
    }
    //Thread safe instatiate method
    public static synchronized DBManager getInstance() {
        if (_instance == null) {
            _instance = new DBManager();
        }
        return _instance;
    }
    public Connection getConnection() {
        return _con;
    }
    /**
     * Connection to MySQL Database
     */
    private static Connection getMySQLConnection() {
        Connection con = null;
        try {
            //String strCon = "jdbc:mysql://127.0.0.1:3306/Province?user=root&password=Chuchogilio33%";

            String url = "jdbc:mysql://127.0.0.1:3306/Province?serverTimezone=UTC";
            String user = "root";
            String password = "Chuchogilio33%";

            con = DriverManager.getConnection(url,user,password);
        } catch (SQLException se) {
            System.out.println(se);
        }
        return con;
    }
    /**
     * Connection to Microsoft Access
     */
    private static Connection getMsAccessConnection() {
        Connection con = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con=DriverManager.getConnection("jdbc:ucanaccess://D:/Programacion/mars/RMIServerSide/Province.mdb");
        } catch (Exception se) {
            System.out.println(se);
        }
        return con;
    }
}