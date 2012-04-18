package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.client.ClientGameState;
import hkust.comp3111h.ballcraft.graphics.balls.DarkBall;
import hkust.comp3111h.ballcraft.graphics.balls.FireBall;
import hkust.comp3111h.ballcraft.graphics.balls.IronBall;
import hkust.comp3111h.ballcraft.graphics.balls.RockBall;
import hkust.comp3111h.ballcraft.graphics.balls.WaterBall;
import hkust.comp3111h.ballcraft.graphics.balls.WoodBall;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Ball extends Unit {

    public float z;
    private float zv;
    
    public static Ball getTypedBall(Ball ball, int ballType) {
        switch (ballType) {
        
        case BallCraft.Ball.WOOD_BALL:
            return new WoodBall(ball.getRadius(), ball.getPosition(), ball.z);
            
        case BallCraft.Ball.ROCK_BALL:
            return new RockBall(ball.getRadius(), ball.getPosition(), ball.z);
            
        case BallCraft.Ball.WATER_BALL:
            return new WaterBall(ball.getRadius(), ball.getPosition(), ball.z);
            
        case BallCraft.Ball.IRON_BALL:
            return new IronBall(ball.getRadius(), ball.getPosition(), ball.z);
            
        case BallCraft.Ball.FIRE_BALL:
            return new FireBall(ball.getRadius(), ball.getPosition(), ball.z);
            
        case BallCraft.Ball.DARK_BALL:
            return new DarkBall(ball.getRadius(), ball.getPosition(), ball.z);
            
        default:
            return ball;
            
        }
    }
   
    public Ball(float size, float mass, float friction, Vec2 position) {
        super();
        size /= rate;
        z = 0;
        zv = 0;
        position = position.mul(1.0f / rate);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC; // dynamic means it is subject to
                                         // forces
        bodyDef.position = position;
        body = ServerGameState.world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.m_radius = size;

        FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up
                                                  // with the following info:
        fixtureDef.shape = shape; // ... its shape is the dynamic box (2x2
                                  // rectangle)
        fixtureDef.density = mass; // ... its density is 1 (default is zero)
        fixtureDef.friction = friction; // ... its surface has some friction
                                        // coefficient
        body.createFixture(fixtureDef); // bind the dense, friction-laden
                                        // fixture to the body
    }

    public Ball(float radius, Vec2 vec, float z) {
        super();

        this.z = z;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position = vec;
        body = ClientGameState.world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.m_radius = radius;
        body.createFixture(shape, 0); // bind the dense, friction-laden fixture
                                      // to the body
    }

    public void draw(GL10 gl) {
    }
    
    public String toSerializedString() {
        if (Math.abs(body.getPosition().x) > 200 / rate
                || Math.abs(body.getPosition().y) > 200 / rate) {

			if (status == Status.NORMAL)
			{
				status = Status.DEAD;
				String str = "dead:";
				str += id;
				Server.extraMessage(str);
			}
			zv += g * 0.3;
			z += zv * 0.3;	
        }

        String serialized = "";
        serialized += "ball:";
        serialized += body.getPosition().x * rate + "," + body.getPosition().y
                * rate;
        serialized += "," + this.getRadius() * rate + "," + z;
        return serialized;
    }

    public float getRadius() {
        return body.getFixtureList().m_shape.m_radius;
    }

    public void updateFromString(String string) {
        String[] parts = string.split(":");
        String[] vals = parts[1].split(",");
        float x = Float.valueOf(vals[0]);
        float y = Float.valueOf(vals[1]);
        float radius = Float.valueOf(vals[2]);

        z = Float.valueOf(vals[3]);
        body.getFixtureList().m_shape.m_radius = radius;
        body.setTransform(new Vec2(x, y), 0);
    }
   }
