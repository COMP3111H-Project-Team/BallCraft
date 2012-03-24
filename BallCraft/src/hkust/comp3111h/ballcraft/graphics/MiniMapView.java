package hkust.comp3111h.ballcraft.graphics;

import hkust.comp3111h.ballcraft.client.ClientGameState;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Used to draw the mini map on the game screen, which indicates the terrain and the 
 * positions of balls
 */
public class MiniMapView extends SurfaceView {
	
	private ClientGameState gameState;
	
	public MiniMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initView();
	}

	public MiniMapView(Context context) {
		super(context);
		this.initView();
	}
	
	private void initView() {
		gameState = ClientGameState.getClientGameState();
	}
	
	@Override
	public void draw(Canvas canvas) {
		Paint textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setAntiAlias(true);
		canvas.drawText("Map", 0, 0, textPaint);
	}

}
