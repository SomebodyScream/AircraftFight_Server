import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloForm
 */
@WebServlet("/authentication")
public class Authentication extends HttpServlet {

    class User{
        String passwd;
        String token;
        public User(String passwd,String token){
            this.passwd = passwd;
            this.token = token;
        }
    }

    private static final long serialVersionUID = 1L;

    private final HashMap<String,User> hashUser = new HashMap<String,User>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authentication() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset = utf-8");
//        String jsonStr = "{\"name\":\"fly\",\"type\":\"虫子\"}";
        String jsonStr = "";
        PrintWriter out = response.getWriter();
        String aut_mode = request.getParameter("aut_mode");
        String user = request.getParameter("user");
        String token = request.getParameter("token");

        //TEMP
        hashUser.put("test1",new User("2020","1970"));
        hashUser.put("test2",new User("2021","1971"));
        hashUser.put("test3",new User("2022","1972"));
        hashUser.put("test4",new User("2023","1973"));
        //TEMP FINISH

        if (aut_mode.equals("token")){
            if (have_user(user)){
                if (hashUser.get(user).token.equals(token)){
                    jsonStr= "{\"stat\":\"ac\"}";
                }else {
                    jsonStr= "{\"stat\":\"rf\"}";
                }
            }else{
                jsonStr= "{\"stat\":\"rf\"}";
            }
        }else{
            if (have_user(user)){
                if (hashUser.get(user).passwd.equals(token)){
                    jsonStr= "{\"stat\":\"ac\",\"token\":\""+hashUser.get(user).token+"\"}";
                }else {
                    jsonStr= "{\"stat\":\"rf\",\"token\":\""+"NOMATCH"+"\"}";
                }
            }else{
                jsonStr= "{\"stat\":\"rf\",\"token\":\""+"NOMATCH"+"\"}";
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

    private boolean have_user(String username){
        return hashUser.containsKey(username);
    }
}