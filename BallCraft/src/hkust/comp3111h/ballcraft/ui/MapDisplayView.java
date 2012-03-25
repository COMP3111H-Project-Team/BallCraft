package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.client.Map;
import hkust.comp3111h.ballcraft.client.Map.WallData;
import hkust.comp3111h.ballcraft.client.MapParser;
import hkust.comp3111h.ballcraft.server.Ball;
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
		if (currMap != null) {
			Vector<WallData> walls = currMap.getWallData();
			float width = currMap.getWidth();
			float height = currMap.getHeight();
			float xOrig = width / 2;
			float yOrig = height / 2;
			
			for (int i = 0; i < walls.size(); i++) {
				WallData data = walls.get(i);
				float xStart = 280 - ((xOrig + data.start.x) / 2 + 45);
				float yStart = (yOrig + data.start.y) / 2 + 60;
				float xEnd = 280 - ((xOrig + data.end.x) / 2 + 45);
				float yEnd = (yOrig + data.end.y) / 2 + 60;
				canvas.drawLine(xStart, yStart, xEnd, yEnd, paint);
			}
		}
	}

}
