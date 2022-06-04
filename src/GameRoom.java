import java.lang.reflect.WildcardType;

public class GameRoom
{
    public static final int WAITING = 0;
    public static final int MATCHED = 1;
    public static final int READY = 2;
    public static final int GAMING = 3;

    public String roomId;
    public String player1;
    public String player2;
    public int state;

    public GameRoom(String player1, String player2)
    {
        roomId = String.valueOf(System.currentTimeMillis());
        this.player1 = player1;
        this.player2 = player2;
        state = (player1 != null && player2 != null) ?MATCHED : WAITING;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public int getState() {
        return state;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean addPlayer(String player){
        if(player1 != null && player2 != null){
            return false;
        }

        if(player1 == null){
            player1 = player;
        }
        else{
            player2 = player;
        }

        state = (player1 != null && player2 != null) ?MATCHED : WAITING;
        return true;
    }
}
