package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.client.Map;
import hkust.comp3111h.ballcraft.client.MapParser;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Unit;
import hkust.comp3111h.ballcraft.server.Wall;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

public class MapDisplayView extends SurfaceView {
	
	private Map currMap;
	
	private Context context;

	public MapDisplayView(Context context) {
		super(context);
		this.context = context;
		this.initView();
	}
	
	public MapDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.initView();
	}
	
	private void initView() {
		this.setWillNotDraw(false);
		MapParser.setContext(context);
	}
	
	public void setMap(String filename) {
		currMap = MapParser.getMapFromXML(filename);
		this.invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		canvas.drawRect(new Rect(100, 100, 200, 200), paint);
		if (currMap != null) {
			Vector<Unit> units = currMap.getUnit();
			for (int i = 0; i < units.size(); i++) {
				if (units.get(i) instanceof Ball) {
					Log.w("ball", "parsed");
				} else if (units.get(i) instanceof Wall) {
					Log.w("wall", "parsed");
				}
			}
		}
	}

}
