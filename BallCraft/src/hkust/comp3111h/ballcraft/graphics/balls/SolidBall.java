package hkust.comp3111h.ballcraft.graphics.balls;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.graphics.GraphicUtils;
import hkust.comp3111h.ballcraft.server.Ball;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public abstract class SolidBall extends Ball {

    private FloatBuffer strip;
    private FloatBuffer fan_top, fan_bottom;
    // private FloatBuffer texture_top, texture_bottom;
    
    private float [] texCoords = {
            0, 1,
            1, 1,
            0, 0,
            1, 0
    };
    
    private static int [] textureIDs = new int [1];
    
    private int stacks = 20, slices = 20;
 
    public SolidBall(float radius, Vec2 vec, float z) {
        super(radius, vec, z);
        unitSphere(stacks, slices);
    }
    
    @Override
    public void draw(GL10 gl) {
        gl.glPushMatrix();

        this.loadLight(gl);

        gl.glTranslatef(this.getPosition().x, this.getPosition().y, this.getRadius() - z);
        gl.glScalef(this.getRadius(), this.getRadius(), this.getRadius());

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        // gl.glEnabeClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        // gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture_top);
        
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fan_top);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, fan_top);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, slices + 2);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, strip);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, strip);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, (slices + 1) * 2 * stacks);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fan_bottom);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, fan_bottom);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, slices + 2);
        
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        // gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        GraphicUtils.restoreMaterialAmbient(gl);
        GraphicUtils.restoreMaterialDiffuse(gl);
        GraphicUtils.restoreMaterialSpecular(gl);
        GraphicUtils.restoreMaterialShininess(gl);

        gl.glPopMatrix();
    }
    
    protected abstract void loadLight(GL10 gl);

    private FloatBuffer makeEndCap(int stacks, int slices, boolean top) {
        // Calculate the Triangle Fan for the endcaps
        int triangleFanVertexCount = slices + 2;
        float dtheta = (float) (2.0 * Math.PI / slices);
        float drho = (float) (Math.PI / stacks);
        float [] fanVertices = new float[triangleFanVertexCount * 3];
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
    
    private FloatBuffer makeTextureBuffer() {
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        FloatBuffer texBuffer = tbb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);
        return texBuffer;
    }

    private void unitSphere(int stacks, int slices) {
        float drho = (float) (Math.PI / stacks);
        float dtheta = (float) (2.0 * Math.PI / slices);

        fan_top = makeEndCap(stacks, slices, true);
        fan_bottom = makeEndCap(stacks, slices, false);
        
        // texture_top = this.makeTextureBuffer();

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

    public static void loadTexture(GL10 gl, Context context) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_wall);
        
        gl.glGenTextures(1, textureIDs, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
        
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0); 
		bmp.recycle();
    }

}
