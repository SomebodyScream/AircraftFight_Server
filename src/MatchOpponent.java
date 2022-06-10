import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MatchOpponent extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String user = req.getParameter("user");

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset = utf-8");

        PrintWriter out = resp.getWriter();

        synchronized (this)
        {
            RoomManager roomManager = RoomManager.getInstance();
            GameRoom room = roomManager.getRoomOfPlayer(user);

            boolean isAvailable = false;
            if(room != null){
                isAvailable = room.getRoomState() == GameRoom.WAITING || room.getRoomState() == GameRoom.MATCHED;
            }

            if(room != null && isAvailable)
            {
                // 当前已处于正在等待匹配的房间中
                String roomId = room.getRoomId();
                String jsonData = "roomId:" + roomId;

                if(room.getRoomState() == GameRoom.MATCHED) {
                    jsonData = jsonData + ", \"match_state\": true";
                    room.setRoomState(GameRoom.GAMING);
                }
                else{
                    jsonData = jsonData + ", \"match_state\": false";
                }

                jsonData = "{" + jsonData + "}";
                out.write(jsonData);
            }
            else if(!roomManager.hasAvailableRoom())
            {
                // 当前未处于正在等待匹配的房间中，且等待队列中有正在寻找匹配的房间
                String roomId = roomManager.createRoom(user);
                roomManager.addRoomToQueue(roomId);

                String jsonData = "{\"roomId\":" + roomId + ", \"match_state\": false}";
                out.write(jsonData);
            }
            else
            {
                // 创建新的等待匹配的房间并加入
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
}