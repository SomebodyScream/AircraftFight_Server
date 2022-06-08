import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class HelloForm
 */
public class Register extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/authentication?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASS = "Mysql15846507418,.";

    private String jsonStr = "";
    private String passwd = "";
    private String token = "";

    public Register() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset = utf-8");
        PrintWriter out = response.getWriter();
        String user = request.getParameter("user");
        passwd = request.getParameter("passwd");
        Connection conn = null;
        Statement stmt = null;

        //Calculate token
        DateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSSS");
        String date = dateFormat.format(new Date(System.currentTimeMillis()));
        token = subStrByStrAndLen(date,9)+subStrByStrAndLen(user,1);

        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "select * from user where username='"+user+"'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                jsonStr= "{\"stat\":\"rp\"}";
            }else{
                sql ="INSERT INTO `user` (username,password,token) VALUES ('"+user+"', '"+passwd+"', '"+token+"');";
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
                jsonStr= "{\"stat\":\"ac\",\"token\":\""+token+"\"}";
            }
        } catch(Exception se) {
            se.printStackTrace();
        } finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException ignored){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        try {
            out.write(jsonStr);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    // 处理 POST 方法请求的方法
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public static String subStrByStrAndLen(String str, int len) {
        return null != str ? str.substring(0, Math.min(str.length(), len)) : null;
    }
}