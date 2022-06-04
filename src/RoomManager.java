import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class RoomManager
{
    private final Queue<String> availableRooms = new LinkedList<>();

    private final HashMap<String, GameRoom> roomMap = new HashMap<>();

    private final HashMap<String, GameRoom> playerToRoomMap = new HashMap<>();

    public String createRoom() {
        return createRoom(null, null);
    }

    public String createRoom(String player1) {
        return createRoom(player1, null);
    }

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
        GameRoom room = roomMap.get(roomId);
        if(room != null) {
            boolean result = room.addPlayer(player);
            if(result){
                playerToRoomMap.put(player, room);
            }
        }
        return false;
    }
}