import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class RecordDatabaseHelper
{
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/record?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASS = "Mysql15846507418,.";

    public static void addRecordToDatabase(String master, int mscore, String guest, int gscore){
        addRecordToDatabase(master, mscore, guest, gscore, (new Date()).toString());
    }

    public static void addRecordToDatabase(String master, int mscore, String guest, int gscore, String time)
    {
        Connection conn = null;
        Statement stmt = null;

        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql;
            sql ="INSERT INTO `multiple` (master,mscore,guest,gscore,time) VALUES ('"+master+"', '"+mscore+"', '"+guest+"', '"+gscore+"', '"+time+"');";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }
        catch(Exception se) {
            se.printStackTrace();
        }
        finally
        {
            try{
                if(stmt!=null) {
                    stmt.close();
                }
            }
            catch(SQLException ignored){
            }

            try{
                if(conn!=null) {
                    conn.close();
                }
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
