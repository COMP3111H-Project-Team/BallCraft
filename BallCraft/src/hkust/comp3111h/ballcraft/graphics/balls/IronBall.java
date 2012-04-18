package hkust.comp3111h.ballcraft.graphics.balls;

import hkust.comp3111h.ballcraft.graphics.GraphicUtils;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec2;

public class IronBall extends SolidBall {

    public IronBall(float radius, Vec2 vec, float z) {
        super(radius, vec, z);
    }

    @Override
    protected void loadLight(GL10 gl) {
        GraphicUtils.setMaterialAmbient(gl, new float[] { 0.1f, 0.1f, 0.1f, 1f });
        GraphicUtils.setMaterialDiffuse(gl, new float[] { 0.8f, 0.8f, 0.8f, 1f });
        GraphicUtils.setMaterialSpecular(gl, new float[] { 1.0f, 1.0f, 1.0f, 1f });
        GraphicUtils.setMaterialShininess(gl, new float [] { 30 });
    }

}
