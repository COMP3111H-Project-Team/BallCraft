package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallDef;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.TerrainDef;
import hkust.comp3111h.ballcraft.client.Map;
import hkust.comp3111h.ballcraft.client.MapParser;
import hkust.comp3111h.ballcraft.client.MultiPlayerGameInitializer;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
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

    private ArrayList<String> mapFilenames;

    private int ballSelected;
    private String currMapName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        self = this;

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent intent = this.getIntent();
        ballSelected = intent
                .getIntExtra("ballSelected", BallDef.WoodBall.id);

        this.initMaps(); // must be put before initLayout() because maps are
                         // needed
        this.initLayout();
    }

    private void initLayout() {
        this.setContentView(R.layout.map_select_menu);

        GLSurfaceView glView = (GLSurfaceView) this
                .findViewById(R.id.map_select_menu_gl_surface_view);
        final MapRenderer renderer = new MapRenderer(this);
        
        renderer.setMap(this.mapFilenames.get(0));
        glView.setRenderer(renderer);
        
        Map initMap = MapParser.getMapFromXML(this.mapFilenames.get(0));

        final TextView terrainTextView = 
                (TextView) this.findViewById(R.id.map_select_menu_terrain_value);
        
        terrainTextView.setText(
                TerrainDef.getTerrainNameById(initMap.getTerrain()));
        
        final TextView lightTextView = 
                (TextView) this.findViewById(R.id.map_select_menu_light_value);
        
        lightTextView.setText(
                initMap.getMode() == BallCraft.MapMode.DAY_MODE ? "Daylight" : "Night");
                
        final TextView benefitBallView = 
                (TextView) this.findViewById(R.id.map_select_menu_benefit_ball_value);
        
        if (initMap.getMode() == BallCraft.MapMode.DAY_MODE) {
	        benefitBallView.setText(
	                BallDef.getBallNameById(
	                        TerrainDef.getTerrainBenefitBallById(initMap.getTerrain())));
        } else { // night mode
	        benefitBallView.setText(
	                BallDef.getBallNameById(BallCraft.Ball.DARK_BALL) + "\n" +
	                BallDef.getBallNameById(
	                        TerrainDef.getTerrainBenefitBallById(initMap.getTerrain())));
        }

        mapList = (ListView) this.findViewById(R.id.map_select_menu_list);
        mapList.setDividerHeight(0);
        mapList.setAdapter(new MapAdapter());
        mapList.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                currMapName = self.mapFilenames.get(position);
                
                Map map = MapParser.getMapFromXML(self.currMapName);
                
                renderer.setMap(currMapName);
                
                terrainTextView.setText(
                        TerrainDef.getTerrainNameById(map.getTerrain()));
                
                lightTextView.setText(
                        map.getMode() == 0 ? "Daylight" : "Night");
                
		        if (map.getMode() == BallCraft.MapMode.DAY_MODE) {
			        benefitBallView.setText(
			                BallDef.getBallNameById(
			                        TerrainDef.getTerrainBenefitBallById(map.getTerrain())));
		        } else { // night mode
			        benefitBallView.setText(
			                BallDef.getBallNameById(BallCraft.Ball.DARK_BALL) + "\n" +
			                BallDef.getBallNameById(
			                        TerrainDef.getTerrainBenefitBallById(map.getTerrain())));
		        }
		
	        }

        });
        
        ImageView mapSelectView = (ImageView) this
                .findViewById(R.id.map_select_menu_select_view);
        mapSelectView.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(self,
                        MultiPlayerGameInitializer.class);
                intent.putExtra("ballSelected", ballSelected);
                intent.putExtra("mapSelected", currMapName);
                self.startActivity(intent);
            }

        });
    }

    private void initMaps() {
        self.mapFilenames = new ArrayList<String>();
        try {
            String[] list = self.getAssets().list("");
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    if (self.isMapFile(list[i])) {
                        self.mapFilenames.add(list[i]);
                    }
                }
            }
        } catch (IOException e) {
        }
        currMapName = self.mapFilenames.get(0);
    }

    private boolean isMapFile(String filename) {
        return filename.endsWith("xml");
    }

    private class MapAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MapAdapter() {
            inflater = self.getLayoutInflater();
        }

        public int getCount() {
            return self.mapFilenames.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = inflater.inflate(R.layout.map_select_list_item, null);
            
            // get the map from the parser
            Map map = MapParser.getMapFromXML(self.mapFilenames.get(position));
            
            TextView mapNameView = (TextView) v
                    .findViewById(R.id.map_select_list_item_name);
            
            mapNameView.setText(map.getName());

            return v;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        self.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

}
