package hkust.comp3111h.ballcraft.graphics;

import javax.microedition.khronos.opengles.GL10;

public class GraphicUtils {

    private static float matAmbient[] = { 0.4f, 0.4f, 0.4f, 1.0f };

    public static void setMaterialColor(GL10 gl, float[] color4fv) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, color4fv, 0);
    }

    public static void restoreMaterialColor(GL10 gl) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
    }

}