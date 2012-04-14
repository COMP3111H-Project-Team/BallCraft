package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.Ball;

import java.util.ArrayList;

public class GameUpdater {

    public ArrayList<Ball> balls = null;

    public GameUpdater() {
        balls = new ArrayList<Ball>();
    }

    public String toSerializedString() {
        String serialized = "";
        for (int i = 0; i < balls.size(); i++) {
            serialized += balls.get(i).toSerializedString();
            if (i != balls.size() - 1) { // not the last one
                serialized += "/";
            }
        }
        return serialized;
    }

    public ArrayList<Ball> getballs() {
        return balls;
    }

}
