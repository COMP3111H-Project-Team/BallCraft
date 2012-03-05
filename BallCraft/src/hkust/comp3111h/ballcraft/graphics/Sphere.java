package hkust.comp3111h.ballcraft.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Sphere {
    FloatBuffer strip, fan_top, fan_bottom;
    float radius;
    int stacks,  slices;
 
    public Sphere(int stacks, int slices, float radius) {
        this.stacks = stacks;
        this.slices = slices;
        this.radius = radius;
        unitSphere(stacks, slices);
    }
 
    public void draw(GL10 gl) {
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
    }
 
    protected FloatBuffer makeEndCap(int stacks, int slices, boolean top) {
        // Calculate the Triangle Fan for the endcaps
        int triangleFanVertexCount = slices + 2;
        float dtheta = (float)(2.0 * Math.PI / slices);
        float drho =  (float)(Math.PI / stacks);
        float[] fanVertices = new float[triangleFanVertexCount * 3];
        float theta = 0;
        float sin_drho = (float)Math.sin(drho);
        //float cos_drho = (float)Math.cos(Math.PI / stacks);
        int index = 0;
        fanVertices[index++] = 0.0f;
        fanVertices[index++] = 0.0f;
        fanVertices[index++] = (top ? 1 : -1);
        for (int j = 0; j <= slices; j++)
        {
            theta = (j == slices) ? 0.0f : j * (top ? 1 : -1) * dtheta;
            float x = (float)-Math.sin(theta) * sin_drho;
            float y = (float)Math.cos(theta) * sin_drho;
            float z = (top ? 1 : -1) * (float)Math.cos(drho);
            fanVertices[index++] = x;
            fanVertices[index++] = y;
            fanVertices[index++] = z;
 
        }
 
        return makeFloatBuffer(fanVertices);
    }
 
    protected void unitSphere(int stacks, int slices) {
        float drho =  (float)(Math.PI / stacks);
        float dtheta = (float)(2.0 * Math.PI / slices);
 
        fan_top = makeEndCap(stacks, slices, true);
        fan_bottom = makeEndCap(stacks, slices, false);
 
        // Calculate the triangle strip for the sphere body
        int triangleStripVertexCount = (slices + 1) * 2 * stacks;
        float[] stripVertices = new float[triangleStripVertexCount * 3];
 
        int index = 0;
        for (int i = 0; i < stacks; i++) {
            float rho = i * drho;
 
            for (int j = 0; j <= slices; j++)
            {
                float theta = (j == slices) ? 0.0f : j * dtheta;
                float x = (float)(-Math.sin(theta) * Math.sin(rho));
                float y = (float)(Math.cos(theta) * Math.sin(rho));
                float z = (float)Math.cos(rho);
                // TODO: Implement texture mapping if texture used
                //                TXTR_COORD(s, t);
                stripVertices[index++] = x;
                stripVertices[index++] = y;
                stripVertices[index++] = z;
 
                x = (float)(-Math.sin(theta) * Math.sin(rho + drho));
                y = (float)(Math.cos(theta) * Math.sin(rho + drho));
                z = (float)Math.cos(rho + drho);
                // TODO: Implement texture mapping if texture used
                //                TXTR_COORD(s, t);
                stripVertices[index++] = x;
                stripVertices[index++] = y;
                stripVertices[index++] = z;
            }
        }
        strip = makeFloatBuffer(stripVertices);
    }
 
    private FloatBuffer makeFloatBuffer(float[] arr){
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }
}
