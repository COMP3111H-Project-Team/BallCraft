package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.client.MultiPlayerGameInitializer;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MapSelectMenu extends Activity {

	private MapSelectMenu self;
	
	private ListView mapList;
	
	private ArrayList<String> maps;
	
	private int ballSelected;
	private String currMap;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		self = this;
		
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Intent intent = this.getIntent();
		ballSelected = intent.getIntExtra("ballSelected", BallCraft.WoodBall.id);
		
		this.initMaps(); // must be put before initLayout() because maps are needed
		this.initLayout();
	}
	
	private void initLayout() {
		this.setContentView(R.layout.map_select_menu);
		
		GLSurfaceView glView = (GLSurfaceView) 
				this.findViewById(R.id.map_select_menu_gl_surface_view);
		glView.setRenderer(new GameMenuRenderer(this));
		
		final MapDisplayView mapDisplay = (MapDisplayView) 
				this.findViewById(R.id.map_select_menu_map_display_view);
		mapDisplay.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		mapDisplay.setMap(maps.get(0));
		
		mapList = (ListView) this.findViewById(R.id.map_select_menu_list);
		mapList.setAdapter(new MapAdapter());
		mapList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				currMap = maps.get(position);
				mapDisplay.setMap(currMap);
			}
			
		});
		
		ImageView mapSelectView = (ImageView) this.findViewById(R.id.map_select_menu_select_view);
		mapSelectView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(self, MultiPlayerGameInitializer.class);
				intent.putExtra("ballSelected", ballSelected);
				intent.putExtra("mapSelected", currMap);
				self.startActivity(intent);
			}
			
		});
	}
	
	private void initMaps() {
		maps = new ArrayList<String>();
		try {
			String [] list = self.getAssets().list("");
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					if (self.isMapFile(list[i])) {
						maps.add(list[i]);
					}
				}
			}
		} catch (IOException e) {
		}
		currMap = maps.get(0);
	}
	
	private boolean isMapFile(String filename) {
		return filename.endsWith("xml");
	}
	
	private class MapAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		
		public MapAdapter() {
			inflater = self.getLayoutInflater();
		}
		
		@Override
		public int getCount() {
			return maps.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = inflater.inflate(R.layout.map_select_list_item, null);
			TextView mapNameView = (TextView) v.findViewById(R.id.map_select_list_item_name);
			mapNameView.setText(maps.get(position));
			
			return v;
		}
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
    
}
