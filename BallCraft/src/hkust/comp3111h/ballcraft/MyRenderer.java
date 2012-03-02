package hkust.comp3111h.ballcraft;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.RGBColor;
import com.threed.jpct.World;

public class MyRenderer implements GLSurfaceView.Renderer 
{	
	
	private FrameBuffer fb = null;
	private World world = null;
	private RGBColor back = new RGBColor(50, 50, 100);
	private Client client;
	private Light sun;
	private Camera cam;
	
	public MyRenderer(Client client) 
	{
		this.client = client;
	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
		if (fb != null) {
			fb.dispose();
		}
		fb = new FrameBuffer(gl, w, h);
		
		world = new World();
		world.setAmbientLight(20, 20, 20);
		
		sun = new Light(world);
		sun.setIntensity(250, 250, 250);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	}

	public void onDrawFrame(GL10 gl)
	{		
		fb.clear(back);
		ArrayList<UnitData> data = Server.process(0, client.getInput()).getUnitData();
		world.renderScene(fb);
		world.draw(fb);
		fb.display();		
	}
}
