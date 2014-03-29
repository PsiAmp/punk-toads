package toad.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PunkToads implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		TileSet standartTileSet = new TileSet("/standard2_transparent.png", 16);
		
		Brush brush = new Brush();
		brush.setTileSet(standartTileSet);
		
		MapLayer backgroundLayer = new MapLayer(800, 500, standartTileSet);
		backgroundLayer.setName("Background");
		
		MapLayer middleLayer = new MapLayer(800, 500, standartTileSet);
		middleLayer.setName("Middle");
		
		MapLayer foregroundLayer = new MapLayer(800, 500, standartTileSet);
		foregroundLayer.setName("Foreground");
		
		MapEditor mapEditor = new MapEditor(600, 500);
		mapEditor.addLayer(backgroundLayer);
		mapEditor.addLayer(middleLayer);
		mapEditor.addLayer(foregroundLayer);
		mapEditor.setBrush(brush);

		TilePicker tilePicker = new TilePicker(standartTileSet);
		tilePicker.setBrush(brush);
		
		mapEditor.start();
		tilePicker.start();
	}
	
	public static boolean isDevelopmentMode() {
	    return !GWT.isProdMode() && GWT.isClient();
	}
}
