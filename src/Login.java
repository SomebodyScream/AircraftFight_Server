import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloForm
 */
public class Login extends HttpServlet {

    class User{
        String passwd;
        String token;
        public User(String passwd,String token){
            this.passwd = passwd;
            this.token = token;
        }
    }

    private static final long serialVersionUID = 1L;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/authentication?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "Mysql15846507418,.";

    private String jsonStr = "{\"stat\":\"rf\"}";
    private String verifyCode = "";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset = utf-8");
        PrintWriter out = response.getWriter();
        String aut_mode = request.getParameter("aut_mode");
        String user = request.getParameter("user");
        verifyCode = request.getParameter("token");
        Connection conn = null;
        Statement stmt = null;

        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "select * from user where username='"+user+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                if (rs.getString("username").equals(user)){
                    matchUser(new User(rs.getString("password"),rs.getString("token")),aut_mode);
                }
            }
            rs.close();
            stmt.close();
            conn.close();
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

    private void matchUser(User correctUser,String mode){
        if (mode.equals("token")){
            if (correctUser.token.equals(verifyCode)){
                jsonStr= "{\"stat\":\"ac\"}";
            }else {
                jsonStr= "{\"stat\":\"rf\"}";
            }
        }else if (mode.equals("passwd")){
            if (correctUser.passwd.equals(verifyCode)){
                jsonStr= "{\"stat\":\"ac\",\"token\":\""+correctUser.token+"\"}";
            }else {
                jsonStr= "{\"stat\":\"rf\",\"token\":\""+"NOMATCH"+"\"}";
            }
        }else {
            jsonStr= "{\"stat\":\"uk\"}";
        }
    }
}