import exception.PlayerNotFoundException;

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
    private PlayerState statePlayer1;
    private PlayerState statePlayer2;
    public int state;

    public GameRoom(String player1, String player2)
    {
        roomId = String.valueOf(System.currentTimeMillis());
        this.player1 = player1;
        this.player2 = player2;
        statePlayer1= new PlayerState();
        statePlayer2= new PlayerState();
        state = (player1 != null && player2 != null) ?MATCHED : WAITING;
    }

    /**
     * Get the score of the player with the specified ID
     * @throws PlayerNotFoundException could not found player with given ID
     */
    public int getPlayerScoreById(String playerId) throws PlayerNotFoundException {
        PlayerState playerState = getPlayerStateById(playerId);
        return playerState.score;
    }

    /**
     * Set the score of the player with the specified ID
     * @throws PlayerNotFoundException could not found player with given ID
     */
    public void setPlayerScoreById(String playerId, int score) throws PlayerNotFoundException{
        PlayerState playerState = getPlayerStateById(playerId);
        playerState.score = score;
    }

    /**
     * Mark that the specified ID player has ended the game
     * @throws PlayerNotFoundException could not found player with given ID
     */
    public void setPlayerGameOver(String playerId) throws PlayerNotFoundException{
        PlayerState playerState = getPlayerStateById(playerId);
        playerState.isGameOver = true;
    }

    /**
     * Get whether the specified ID player has ended the game
     * @throws PlayerNotFoundException could not found player with given ID
     */
    public boolean isPlayerGameOver(String playerId) throws PlayerNotFoundException{
        PlayerState playerState = getPlayerStateById(playerId);
        return playerState.isGameOver;
    }

    /**
     * Get the ID of another player in the room
     * @throws PlayerNotFoundException could not found player with given ID
     */
    public String getAnotherPlayerId(String playerId) throws PlayerNotFoundException{
        if(playerId.equals(player1)){
            return player2;
        }
        else if(playerId.equals(player2)){
            return player1;
        }
        else{
            throw new PlayerNotFoundException();
        }
    }

    /**
     * Get the score of another player in the room
     * @throws PlayerNotFoundException could not found player with given ID
     */
    public int getAnotherPlayerScore(String playerId) throws PlayerNotFoundException{
        String anotherPlayer = getAnotherPlayerId(playerId);
        return getPlayerScoreById(anotherPlayer);
    }

    /**
     * Get whether another player in the room has ended the game
     * @throws PlayerNotFoundException could not found player with given ID
     */
    public boolean isAnotherPlayerGameOver(String playerId) throws PlayerNotFoundException{
        String anotherPlayer = getAnotherPlayerId(playerId);
        return isPlayerGameOver(anotherPlayer);
    }

    /**
     * add a new player to this room
     * @return whether the addition is successful (when the room is full it will be false)
     */
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

    public String getRoomId() {
        return roomId;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public int getRoomState() {
        return state;
    }

    public void setPlayer1(String playerId) {
        this.player1 = playerId;
    }

    public void setPlayer2(String playerId) {
        this.player2 = playerId;
    }

    public void setRoomState(int state) {
        this.state = state;
    }

    /**
     * Get the PlayerState instance bound with the specified player
     */
    private PlayerState getPlayerStateById(String playerId) throws PlayerNotFoundException{
        if(playerId.equals(player1)){
            return statePlayer1;
        }
        else if(playerId.equals(player2)){
            return statePlayer2;
        }
        else{
            throw new PlayerNotFoundException();
        }
    }

    /**
     * inner class for managing player state
     */
    private class PlayerState
    {
        public int score;
        public boolean isGameOver;

        public PlayerState() {
            score = 0;
            isGameOver = false;
        }

        public PlayerState(int score, boolean isGameOver){
            this.score = score;
            this.isGameOver = isGameOver;
        }
    }
}
