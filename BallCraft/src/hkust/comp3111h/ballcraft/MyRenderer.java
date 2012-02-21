package hkust.comp3111h.ballcraft;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.RGBColor;
import com.threed.jpct.World;

public class MyRenderer implements GLSurfaceView.Renderer 
{	
	
	private FrameBuffer fb = null;
	private World world = null;
	private RGBColor back = new RGBColor(50, 50, 100);
	private Client client;
	
	public MyRenderer(Client client) 
	{
		this.client = client;
	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
		if (fb != null) {
			fb.dispose();
		}
		fb = new FrameBuffer(gl, w, h);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	}

	public void onDrawFrame(GL10 gl)
	{		
		fb.clear(back);
		world = Server.process(0, client.getInput()).getWorld();
		world.renderScene(fb);
		world.draw(fb);
		fb.display();		
	}
}
