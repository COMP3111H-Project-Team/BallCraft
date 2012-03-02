package hkust.comp3111h.ballcraft;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
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

	@SuppressWarnings("unchecked")
	public void onDrawFrame(GL10 gl)
	{		
		fb.clear(back);
		ArrayList<UnitData> data = null;
		/*byte[] bytes = Server.process(0, client.getInput()).getUnitData();
		
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in;
		try
		{
			in = new ObjectInputStream(bis);
			data = (ArrayList<UnitData>) in.readObject();
			bis.close();
			in.close();
		} 
		catch (StreamCorruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		world.renderScene(fb);
		world.draw(fb);
		fb.display();		
	}
}
