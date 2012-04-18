package hkust.comp3111h.ballcraft.graphics;

import javax.microedition.khronos.opengles.GL10;

public class GraphicUtils {

    private static float [] matAmbient = { 0.4f, 0.4f, 0.4f, 1.0f };
    private static float [] matDiffuse = { 0.6f, 0.6f, 0.6f, 1f };
    private static float [] matSpecular = { 0.9f, 0.9f, 0.9f, 1f };
    private static float [] matShininess = { 8f };

    // set colors:
    public static void setMaterialAmbient(GL10 gl, float [] color4fv) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, color4fv, 0);
    }
    
    public static void setMaterialDiffuse(GL10 gl, float [] color4fv) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, color4fv, 0);
    }
    
    public static void setMaterialSpecular(GL10 gl, float [] color4fv) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, color4fv, 0);
    }
    
    public static void setMaterialShininess(GL10 gl, float [] shininess) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shininess, 0);
    }

    // restore colors:
    public static void restoreMaterialAmbient(GL10 gl) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
    }
    
    public static void restoreMaterialDiffuse(GL10 gl) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);
    }
    
    public static void restoreMaterialSpecular(GL10 gl) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, matSpecular, 0);
    }
    
    public static void restoreMaterialShininess(GL10 gl) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, matShininess, 0);
    }

}