import java.lang.reflect.WildcardType;

public class GameRoom
{
    public static final int INVALID = -1;
    public static final int WAITING = 0;
    public static final int MATCHED = 1;
    public static final int READY = 2;
    public static final int GAMING = 3;

    public String roomId;
    public String player1;
    public String player2;
    private int playerScore1;
    private int playerScore2;
    public int state;

    public GameRoom(String player1, String player2)
    {
        roomId = String.valueOf(System.currentTimeMillis());
        this.player1 = player1;
        this.player2 = player2;
        playerScore1 = playerScore2 = 0;
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

    public int getPlayerScoreById(String playerId) throws Exception{
        if(playerId.equals(player1)){
            return playerScore1;
        }
        else if(playerId.equals(player2)){
            return playerScore2;
        }
        else{
            throw new Exception("The player is not in this room");
        }
    }

    public void setPlayerScoreById(String playerId, int score) throws Exception{
        if(playerId.equals(player1)){
            playerScore1 = score;
        }
        else if(playerId.equals(player2)){
            playerScore2 = score;
        }
        else{
            throw new Exception("The player is not in this room");
        }
    }

    public String getAnotherPlayerId(String playerId) throws Exception{
        if(playerId.equals(player1)){
            return player2;
        }
        else if(playerId.equals(player2)){
            return player1;
        }
        else{
            throw new Exception("The player is not in this room");
        }
    }

    public int getAnotherPlayerScore(String playerId) throws Exception{
        String anotherPlayer = getAnotherPlayerId(playerId);
        int score = getPlayerScoreById(anotherPlayer);
        return score;
    }

    public int getState() {
        return state;
    }

    public void setPlayer1(String playerId) {
        this.player1 = playerId;
    }

    public void setPlayer2(String playerId) {
        this.player2 = playerId;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean addPlayer(String playerId){
        if(player1 != null && player2 != null){
            return false;
        }

        if(player1 == null){
            player1 = playerId;
        }
        else{
            player2 = playerId;
        }

        state = (player1 != null && player2 != null) ?MATCHED : WAITING;
        return true;
    }
}
