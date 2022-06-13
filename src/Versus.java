import exception.PlayerNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        boolean isGameOver = Boolean.parseBoolean(req.getParameter("gameover"));

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset = utf-8");

        RoomManager roomManager = RoomManager.getInstance();
        GameRoom room = roomManager.getRoomOfPlayer(playerId);

        synchronized (this)
        {
            if(room != null)
            {
                try{
                    int opponentScore = room.getAnotherPlayerScore(playerId);

                    if(!isGameOver) {
                        room.setPlayerScoreById(playerId, score);
                    }
                    else
                    {
                        room.setPlayerGameOver(playerId);
                        if(room.isAnotherPlayerGameOver(playerId))
                        {
                            String opponentId = room.getAnotherPlayerId(playerId);
                            int mscore = room.getPlayerScoreById(playerId);
                            int gscore = room.getPlayerScoreById(opponentId);

                            // get current time
                            DateFormat df = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
                            Date date = Calendar.getInstance().getTime();
                            String time = df.format(date);

                            // save record to database
                            RecordDatabaseHelper.addRecordToDatabase(playerId, mscore, opponentId, gscore, time);
                            RecordDatabaseHelper.addRecordToDatabase(opponentId, gscore, playerId, mscore, time);

                            // remove this room
                            room.setRoomState(GameRoom.INVALID);
                            roomManager.removeRoom(room.getRoomId());
                        }
                    }

                    PrintWriter out = resp.getWriter();
                    String jsonData = "{\"score\":" + opponentScore + "}";
                    out.write(jsonData);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
