package hkust.comp3111h.ballcraft.graphics.balls;

import hkust.comp3111h.ballcraft.graphics.GraphicUtils;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec2;

public class RockBall extends SolidBall {

    public RockBall(float radius, Vec2 vec, float z) {
        super(radius, vec, z);
    }

    @Override
    protected void loadLight(GL10 gl) {
        GraphicUtils.setMaterialAmbient(gl, new float[] { 0.5f, 0.4f, 0.3f, 1f });
        GraphicUtils.setMaterialDiffuse(gl, new float[] { 0.7f, 0.7f, 0.6f, 1f });
        GraphicUtils.setMaterialSpecular(gl, new float[] { 0.2f, 0.2f, 0.2f, 1f });
        GraphicUtils.setMaterialShininess(gl, new float [] { 1 });
    }

}
