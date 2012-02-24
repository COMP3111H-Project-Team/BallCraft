package hkust.comp3111h.ballcraft;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
	    	//get node
	    	Element root = document.getDocumentElement();
	    	Element mapName = (Element)root.getElementsByTagName("name").item(0);
	    	map.setName(mapName.getFirstChild().getNodeValue());
	    	Element imageUrl = (Element)root.getElementsByTagName("imageUrl").item(0);
	    	map.setImageurl(imageUrl.getFirstChild().getNodeValue());
	    	Element height = (Element)root.getElementsByTagName("height").item(0);
	    	map.setHeightCount(Integer.parseInt(height.getFirstChild().getNodeValue()));
	    	Element width = (Element)root.getElementsByTagName("width").item(0);
	    	map.setWidthCount(Integer.parseInt(width.getFirstChild().getNodeValue()));
	    	Element size = (Element)root.getElementsByTagName("size").item(0);
	    	map.setTileSize(Integer.parseInt(size.getFirstChild().getNodeValue()));
	    
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    return map;
	}
}
