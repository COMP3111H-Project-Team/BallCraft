package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.MyApplication;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MapParser{
	//debug
	public static boolean D=true;
	
	public static Map getMapFromXML(String fileName) {
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
	    	inputStream = MyApplication.getAppContext().getResources().getAssets().open(fileName);
	    	document = builder.parse(inputStream);
	    	
	    	//get root
	    	Element root = document.getDocumentElement();
	    	
	    	//get map name
	    	Element mapName = (Element)root.getElementsByTagName("name").item(0);
	    	map.setName(mapName.getFirstChild().getNodeValue());
	    	
	        //get height count
	    	Element height = (Element)root.getElementsByTagName("height").item(0);
	    	map.setHeight(Integer.parseInt(height.getFirstChild().getNodeValue()));
	    	
	    	//get width count
	    	Element width = (Element)root.getElementsByTagName("width").item(0);
	    	map.setWidth(Integer.parseInt(width.getFirstChild().getNodeValue()));
	    	
	    	//get terrain
	    	Element terrain = (Element)root.getElementsByTagName("terrain").item(0);
	    	map.setTerrain(Integer.parseInt(terrain.getFirstChild().getNodeValue()));
	    	
	    	//get map mode
	    	Element mode = (Element)root.getElementsByTagName("mode").item(0);
	    	map.setMode(Integer.parseInt(mode.getFirstChild().getNodeValue()));
	    	
	    	String data;
	    	NodeList nodes;
	    	
	    	//get initPosition list
	    	nodes = root.getElementsByTagName("initPosition");
			for(int i=0;i<nodes.getLength();i++){
				Element initElement=(Element)(nodes.item(i));
				data = initElement.getFirstChild().getNodeValue();
				//Log.i("map", data);
				map.addInit(parseString(data));                     
			}
	    	
	    	//get wall list
	    	nodes=root.getElementsByTagName("wall");
			for(int i=0;i<nodes.getLength();i++){
				Element wallElement=(Element)(nodes.item(i));
				data = wallElement.getFirstChild().getNodeValue();
				map.addWall(parseString(data));                     
			}
			
			//get trap list
	    	nodes=root.getElementsByTagName("trap");
			for(int i=0;i<nodes.getLength();i++){
				Element TrapElement=(Element)(nodes.item(i));
				data = TrapElement.getFirstChild().getNodeValue();
				map.addTrap(parseString(data));                     
			}
			
			//get plane list
	    	nodes=root.getElementsByTagName("plane");
			for(int i=0;i<nodes.getLength();i++){
				Element wallElement=(Element)(nodes.item(i));
				data = wallElement.getFirstChild().getNodeValue();
				map.addPlane(parseString(data));                     
			}
			
        } catch (Exception e) {
        }
        return map;
    }

    private static int[] parseString(String wallData) {
        String[] parts = wallData.split(",");
        int length = parts.length;
        int[] data = new int[length];
        for (int j = 0; j < length; j++) {
            data[j] = (int) (Integer.parseInt(parts[j])*0.6);
        }
        return data;
    }

    public static int[][] readLayer(String fileName, int width, int height) {
        int[][] layer = new int[height][width];
        int i_data = 0;
        try {
            InputStream inputStream = MyApplication.getAppContext().getResources().getAssets()
                    .open(fileName);
            // Wrap the FileInputStream with a DataInputStream
            DataInputStream data_in = new DataInputStream(inputStream);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    try {
                        i_data = data_in.readInt();
                    } catch (EOFException eof) {
                        break;
                    }
                    // Print out the integer, double data pairs.
                    layer[i][j] = i_data;
                }
            }
            data_in.close();
        } catch (IOException e) {
        }
        return layer;
    }

}
