package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.client.ClientGameState;
import hkust.comp3111h.ballcraft.graphics.GraphicUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Ball extends Unit {

    public float z;
    float zv;

    FloatBuffer strip, fan_top, fan_bottom;
    float radius = 10;
    int stacks = 20, slices = 20;

    float theta, pi;
    float co, si;
    float r1, r2;
    float h1, h2;
    float step = 4.0f;
    float[][] v;
    
    FloatBuffer vfb;
    
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

        unitSphere(stacks, slices);
    }

    public void draw(GL10 gl) {
        gl.glPushMatrix();

        GraphicUtils.setMaterialColor(gl, new float[] { 0.0f, 0.4f, 0.8f, 1f });

        gl.glTranslatef(this.getPosition().x, this.getPosition().y, getRadius() - z);
        gl.glScalef(this.getRadius(), this.getRadius(), this.getRadius());

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fan_top);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, fan_top);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, slices + 2);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, strip);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, strip);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, (slices + 1) * 2 * stacks);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fan_bottom);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, fan_bottom);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, slices + 2);

        GraphicUtils.restoreMaterialColor(gl);

        gl.glPopMatrix();
    }

    protected FloatBuffer makeEndCap(int stacks, int slices, boolean top) {
        // Calculate the Triangle Fan for the endcaps
        int triangleFanVertexCount = slices + 2;
        float dtheta = (float) (2.0 * Math.PI / slices);
        float drho = (float) (Math.PI / stacks);
        float[] fanVertices = new float[triangleFanVertexCount * 3];
        float theta = 0;
        float sin_drho = (float) Math.sin(drho);
        // float cos_drho = (float)Math.cos(Math.PI / stacks);
        int index = 0;
        fanVertices[index++] = 0.0f;
        fanVertices[index++] = 0.0f;
        fanVertices[index++] = (top ? 1 : -1);
        for (int j = 0; j <= slices; j++) {
            theta = (j == slices) ? 0.0f : j * (top ? 1 : -1) * dtheta;
            float x = (float) -Math.sin(theta) * sin_drho;
            float y = (float) Math.cos(theta) * sin_drho;
            float z = (top ? 1 : -1) * (float) Math.cos(drho);
            fanVertices[index++] = x;
            fanVertices[index++] = y;
            fanVertices[index++] = z;
        }

        return makeFloatBuffer(fanVertices);
    }

    protected void unitSphere(int stacks, int slices) {
        float drho = (float) (Math.PI / stacks);
        float dtheta = (float) (2.0 * Math.PI / slices);

        fan_top = makeEndCap(stacks, slices, true);
        fan_bottom = makeEndCap(stacks, slices, false);

        // Calculate the triangle strip for the sphere body
        int triangleStripVertexCount = (slices + 1) * 2 * stacks;
        float[] stripVertices = new float[triangleStripVertexCount * 3];

        int index = 0;
        for (int i = 0; i < stacks; i++) {
            float rho = i * drho;

            for (int j = 0; j <= slices; j++) {
                float theta = (j == slices) ? 0.0f : j * dtheta;
                float x = (float) (-Math.sin(theta) * Math.sin(rho));
                float y = (float) (Math.cos(theta) * Math.sin(rho));
                float z = (float) Math.cos(rho);
                // TODO: Implement texture mapping if texture used
                // TXTR_COORD(s, t);
                stripVertices[index++] = x;
                stripVertices[index++] = y;
                stripVertices[index++] = z;

                x = (float) (-Math.sin(theta) * Math.sin(rho + drho));
                y = (float) (Math.cos(theta) * Math.sin(rho + drho));
                z = (float) Math.cos(rho + drho);
                // TODO: Implement texture mapping if texture used
                // TXTR_COORD(s, t);
                stripVertices[index++] = x;
                stripVertices[index++] = y;
                stripVertices[index++] = z;
            }
        }
        strip = makeFloatBuffer(stripVertices);
    }

    private FloatBuffer makeFloatBuffer(float[] arr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
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
