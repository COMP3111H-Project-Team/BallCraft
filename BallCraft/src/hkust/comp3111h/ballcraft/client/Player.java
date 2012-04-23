package hkust.comp3111h.ballcraft.client;

public class Player {

    public String playerName;
    public int playerID;
    public boolean isServerPlayer;
    
    public Player(String name, int id, boolean isServerPlayer) {
        this.playerName = name;
        this.playerID = id;
        this.isServerPlayer = isServerPlayer;
    }

}
