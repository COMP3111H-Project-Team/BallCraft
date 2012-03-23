package hkust.comp3111h.ballcraft.graphics;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Used to draw the mini map on the game screen, which indicates the terrain and the 
 * positions of balls
 */
public class MiniMapView extends SurfaceView {

	public MiniMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MiniMapView(Context context) {
		super(context);
	}

}
