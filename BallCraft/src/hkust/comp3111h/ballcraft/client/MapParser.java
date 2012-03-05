package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.R.drawable;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MapParser{
	private Context context;
	
	public MapParser(Context context) {
		this.context = context;
	}
	
	public Map getMapFromXML(String fileName) {
		Map map = new Map();
		DocumentBuilderFactory factory=null;
	    DocumentBuilder builder=null;
	    Document document=null;
	    InputStream inputStream=null;
	    
	    //find the xml file first,just two layers now
	    factory = DocumentBuilderFactory.newInstance();
	    try {
	    	//load file
	    	builder = factory.newDocumentBuilder();
	    	inputStream = context.getResources().getAssets().open(fileName);
	    	document = builder.parse(inputStream);
	    	Log.d("map", "load document");
	    	//get root
	    	Element root = document.getDocumentElement();
	    	//get map name
	    	Element mapName = (Element)root.getElementsByTagName("name").item(0);
	    	String msg = mapName.getFirstChild().getNodeValue();
	    	Log.d("map", msg);
	    	map.setName(mapName.getFirstChild().getNodeValue());
	    	//get image URL and set Bitmap
	    	Element imageUrl = (Element)root.getElementsByTagName("imageUrl").item(0);
	    	String imageUrlString = (imageUrl.getFirstChild().getNodeValue());
	    	Log.i("map", imageUrlString);
	    	map.setBitmap(readBitmap(imageUrlString));
	    	//get height count
	    	Element height = (Element)root.getElementsByTagName("height").item(0);
	    	map.setHeightCount(Integer.parseInt(height.getFirstChild().getNodeValue()));
	    	//get width count
	    	Element width = (Element)root.getElementsByTagName("width").item(0);
	    	map.setWidthCount(Integer.parseInt(width.getFirstChild().getNodeValue()));
	    	//get tile size
	    	Element size = (Element)root.getElementsByTagName("size").item(0);
	    	map.setTileSize(Integer.parseInt(size.getFirstChild().getNodeValue()));
	    	//get layer01 string value and transfer it to 2D array.
	    	Element layer1 = (Element) root.getElementsByTagName("layer1").item(0);
	    	String layer1String = layer1.getFirstChild().getNodeValue();
	    	map.setLayer(1, layerToArray(layer1String,map.getWidthCount(),map.getHeightCount()));
	    	//get layer02 string value and transfer it to 2D array
	    	Element layer2 = (Element) root.getElementsByTagName("layer2").item(0);
	    	String layer2String = layer2.getFirstChild().getNodeValue();
	    	map.setLayer(1, layerToArray(layer2String,map.getWidthCount(),map.getHeightCount()));
	    	
	    }catch (IOException e){
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
         catch (ParserConfigurationException e) {
            e.printStackTrace();
	    }
	    return map;
	}


	public int[][] layerToArray(String original,int width, int height) { 
    	original = original.replaceAll("[^\\d,]", "");
    	String[] parts = original.split(",");
		int[][] array = new int[width][height];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				array[i][j]= Integer.parseInt(parts[i*width+j]);
			}
		}
		return array;
	}
	
	public Bitmap readBitmap(String imageUrl){  
        BitmapFactory.Options opt = new BitmapFactory.Options();  
        opt.inPreferredConfig = Bitmap.Config.RGB_565;   
        opt.inPurgeable = true;  
        opt.inInputShareable = true;
        Bitmap bitmap = null;
        if(imageUrl == "redbird.png") {
        	bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.redbird,opt);
        }  
        return bitmap;
    }  
}
