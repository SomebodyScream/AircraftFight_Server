import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class RoomManager
{
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
            playerToRoomMap.put(player1, room);
        }
        if(player2 != null){
            playerToRoomMap.put(player2, room);
        }

        return roomId;
    }

    public void removeRoom(String roomId) {
        roomMap.remove(roomId);
    }

    public GameRoom getRoomById(String roomId){
        return roomMap.getOrDefault(roomId, null);
    }

    public boolean hasAvailableRoom(){
        return !availableRooms.isEmpty();
    }

    public void addRoomToQueue(String roomId) {
        availableRooms.add(roomId);
    }

    public String pollRoomFromQueue()
    {
        String roomId = availableRooms.poll();
        if(roomId == null){
            /* TODO */
        }
        return roomId;
    }

    public GameRoom getRoomOfPlayer(String player){
        return playerToRoomMap.get(player);
    }

    public boolean addPlayerToRoom(String player, String roomId){
        GameRoom room = getRoomById(roomId);
        if(room != null) {
            boolean result = room.addPlayer(player);
            if(result){
                playerToRoomMap.put(player, room);
            }
        }
        return false;
    }
}