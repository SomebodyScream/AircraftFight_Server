import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Versus extends HttpServlet
{
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String playerId = req.getParameter("playerId");
        int score = Integer.parseInt(req.getParameter("score"));

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset = utf-8");

        RoomManager roomManager = RoomManager.getInstance();
        GameRoom room = roomManager.getRoomOfPlayer(playerId);

        if(room != null)
        {
            int opponentScore = -1;
            try {
                room.setPlayerScoreById(playerId, score);
                opponentScore = room.getAnotherPlayerScore(playerId);
            } catch (Exception e) {
                e.printStackTrace();
            }

            PrintWriter out = resp.getWriter();
            String jsonData = "{\"score\":" + opponentScore + "}";
            out.write(jsonData);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
