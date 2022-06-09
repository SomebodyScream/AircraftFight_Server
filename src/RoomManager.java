import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class RoomManager
{
    /**
     * singleton
     */
    private static RoomManager roomManager = new RoomManager();

    /**
     * rooms waiting for matching opponent
     */
    private final Queue<String> availableRooms = new LinkedList<>();

    /**
     * roomId -> GameRoom instance mapping
     * maintaining existing room
     */
    private final HashMap<String, GameRoom> roomMap = new HashMap<>();

    /**
     * playerId -> GameRoom instance mapping
     * maintaining players who have already entered a room
     */
    private final HashMap<String, GameRoom> playerToRoomMap = new HashMap<>();

    private RoomManager(){
    }

    public static RoomManager getInstance(){
        return roomManager;
    }

    public String createRoom() {
        return createRoom(null, null);
    }

    public String createRoom(String player1) {
        return createRoom(player1, null);
    }

    /**
     * Create a new room. It will link the player(s) to the new room.
     * Use "getRoomOfPlayer" method to query which room is a certain player in.
     * @param player1 ID of player1 (null is allowed)
     * @param player2 ID of player2 (null is allowed)
     * @return unique ID of the new room
     */
    public String createRoom(String player1, String player2)
    {
        GameRoom room = new GameRoom(player1, player2);
        String roomId = room.getRoomId();
        roomMap.put(roomId, room);

        if(player1 != null){
            bindRoomToPlayer(player1, room);
        }
        if(player2 != null){
            bindRoomToPlayer(player2, room);
        }

        return roomId;
    }

    /**
     * remove room with specified roomId
     */
    public void removeRoom(String roomId)
    {
        GameRoom room = roomMap.get(roomId);
        playerToRoomMap.remove(room.getPlayer1(), room);
        playerToRoomMap.remove(room.getPlayer2(), room);
        roomMap.remove(roomId);
    }

    /**
     * get room with specified roomId
     * @return GameRoom object or null when roomId does not exist
     */
    public GameRoom getRoomById(String roomId){
        return roomMap.getOrDefault(roomId, null);
    }

    /**
     * whether there is room available for a new game
     */
    public boolean hasAvailableRoom(){
        return !availableRooms.isEmpty();
    }

    /**
     * add room with specified roomId to available room queue
     */
    public void addRoomToQueue(String roomId) {
        availableRooms.add(roomId);
    }

    /**
     * poll a room from available room queue
     */
    public String pollRoomFromQueue()
    {
        String roomId = availableRooms.poll();
        if(roomId == null){
            /* TODO */
        }
        return roomId;
    }

    /**
     * get room where the specified player is in
     */
    public GameRoom getRoomOfPlayer(String playerId){
        return playerToRoomMap.get(playerId);
    }

    /**
     * add the player to the room
     * @return whether the addition is success
     */
    public boolean addPlayerToRoom(String playerId, String roomId)
    {
        GameRoom room = getRoomById(roomId);
        if(room != null)
        {
            boolean result = room.addPlayer(playerId);
            if(result){
                bindRoomToPlayer(playerId, room);
                return true;
            }
        }
        return false;
    }

    private void bindRoomToPlayer(String playerId, GameRoom room){
        if(playerToRoomMap.containsKey(playerId)){
            playerToRoomMap.replace(playerId, room);
        }
        else{
            playerToRoomMap.put(playerId, room);
        }
    }
}