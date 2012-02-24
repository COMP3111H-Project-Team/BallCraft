package hkust.comp3111h.ballcraft;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import android.content.Context;

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
	    
	    //find the xml file first
	    factory = DocumentBuilderFactory.newInstance();
	    try {
	    	//load file
	    	builder = factory.newDocumentBuilder();
	    	inputStream = context.getResources().getAssets().open(fileName);
	    	document = builder.parse(inputStream);
	    	//get root
	    	Element root = document.getDocumentElement();
	    	//get map name
	    	Element mapName = (Element)root.getElementsByTagName("name").item(0);
	    	map.setName(mapName.getFirstChild().getNodeValue());
	    	//get image URL
	    	Element imageUrl = (Element)root.getElementsByTagName("imageUrl").item(0);
	    	map.setImageurl(imageUrl.getFirstChild().getNodeValue());
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
	    	layer1String = layer1String.replace("[{} ]","");
	    	String[] parts = layer1String.split(",");
			System.out.println(parts.length);
			int[][] array = new int[map.getWidthCount()][map.getHeightCount()];
			for(int i = 0; i < map.getWidthCount(); i++) {
				for(int j = 0; j < map.getHeightCount(); j++) {
					array[i][j]= Integer.parseInt(parts[i*map.getHeightCount()+j]);
				}
			}
			//get layer02 string value and transfer it to 2D array.
	    	Element layer2 = (Element) root.getElementsByTagName("layer2").item(0);
	    	String layer2String = layer1.getFirstChild().getNodeValue();
	    	layer2String = layer2String.replace("[{} ]","");
	    	String[] parts2 = layer2String.split(",");
			System.out.println(parts2.length);
			int[][] array2 = new int[map.getWidthCount()][map.getHeightCount()];
			for(int i = 0; i < map.getWidthCount(); i++) {
				for(int j = 0; j < map.getHeightCount(); j++) {
					array2[i][j]= Integer.parseInt(parts[i*map.getHeightCount()+j]);
				}
			}
			map.setLayer(1, array2);
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
}
