import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MatchOpponent extends HttpServlet
{
//    private static RoomManager roomManager;

    @Override
    public void init() throws ServletException {
        System.out.println("test init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
//        System.out.println("doGet start");

//        HttpSession session = req.getSession(true);

        String user = req.getParameter("user");

//        if(session.isNew()){
//            user = req.getParameter("user");
//            session.setAttribute("user", user);
//        }
//        else{
//            user = (String) session.getAttribute("user");
//        }

//        System.out.println("user: " + user);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset = utf-8");

        PrintWriter out = resp.getWriter();

        synchronized (this)
        {
            RoomManager roomManager = RoomManager.getInstance();
            GameRoom room = roomManager.getRoomOfPlayer(user);
            if(room != null)
            {
                String roomId = room.getRoomId();
                String jsonData = "roomId:" + roomId;
                if(room.getState() == GameRoom.MATCHED) {
                    jsonData = jsonData + ", \"match_state\": true";
                }
                else{
                    jsonData = jsonData + ", \"match_state\": false";
                }

                jsonData = "{" + jsonData + "}";
                out.write(jsonData);
            }
            else if(!roomManager.hasAvailableRoom())
            {
                String roomId = roomManager.createRoom(user);
                roomManager.addRoomToQueue(roomId);

                String jsonData = "{\"roomId\":" + roomId + ", \"match_state\": false}";
                out.write(jsonData);
            }
            else
            {
                String roomId = roomManager.pollRoomFromQueue();
                roomManager.addPlayerToRoom(user, roomId);

                String jsonData = "{\"roomId\":" + roomId + ", \"match_state\": true}";
                out.write(jsonData);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    }

    @Override
    public void destroy() {
        System.out.println("on destroy");
    }
}