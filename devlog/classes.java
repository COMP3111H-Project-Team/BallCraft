interface Renderable {
    void render(Renderer r);
}

abstract class Unit {
    float x;
    float y;
    float vx;
    float vy;
    float ax;
    float ay;

    float getX();
    float setX(float x);
    float getY();
    float setY(float y);
    float getVX();
    float setVX(float vx);
    float getVY();
    float setVY(float jy);
    float getAX();
    float setAX(float ax);
    float getAY();
    float setAY(float ay);
}

class GameMap implements Renderable {
    GameMap(XmlNode node);
    ArrayList<Unit> getTerrain();
    XmlNode toXml();
}

class ClientLoop {
    GameState state;
    Server server;
    Renderer renderer;

    void loop() {
        while (!state.gameOver()) {
            Counter.start();

            GameInput input = SensorInput.getInput();
            state = server.process(input);
            state.render(renderer);

            Counter.waitUntil(33);
        }
    }
}

class GameState implements Renderable, Seralizable {
    PhysicalEngine;
    GameMap;
    Players;
    Magic;
    Skill;

    void processPlayerInput(int playerId, GameInput input);
    void stepOneFrame();
}

class Server {
    GameState process(GameInput input);
}

class ServerListener implements Runnable {
    Socket socket;
    Server server;

    void run() {
        socket.listen(...);

        while (true) {
            Counter.start();

            GameState state = GameState.fromXmlString(socket.receive());
            GameState newState = server.process(state);
            socket.send(newState.toXmlString());

            Counter.waitUntil(33);
        }
    }
}

class Renderer {
}

// And some internal API for GameState
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

