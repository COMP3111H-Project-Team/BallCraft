// We are using JPCT super gaming library, yay o/

class Unit extends Object3D {
    void move(int msecElapsed);
}

class Player {
    Ball getBall();
    Achievement getAchievement();
}

class Achievement {
    // which balls you can unlock
    Hashtable<String, String> achieves; // achievement name -> value
}

abstract class Ball extends Unit {
}

abstract class Magic extends Unit {
}

class GameState {
    void processPlayerInput(int playerId, GameInput input);
    void onEveryFrame(int msecElapsed);
    static GameState createTestGameState();
}

class Server {
    GameState process(GameInput input);
}

class DummyServer extends Server {
}

class GameInput {
    // Getter/setters
    SimpleVector accel;
    ArrayList<int> keysPressed;
}


