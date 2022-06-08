package exception;

public class PlayerNotFoundException extends Exception
{
    public PlayerNotFoundException(){
        super("The player is not in this room");
    }
}
