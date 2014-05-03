package toad.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PunkToads implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//testGrid();
		
		int tileSize = 16;
		TileSet standartTileSet = new TileSet("/standard2_transparent.png", tileSize);
		
		ToolBox.getInstance().getBrush().setTileSet(standartTileSet);
		
		TileLayer backgroundLayer = new TileLayer(800 / tileSize, 500 / tileSize, standartTileSet);
		backgroundLayer.setName("Background");
		
		TileLayer middleLayer = new TileLayer(800 / tileSize, 500 / tileSize, standartTileSet);
		middleLayer.setName("Middle");
		
		TileLayer foregroundLayer = new TileLayer(800 / tileSize, 500 / tileSize, standartTileSet);
		foregroundLayer.setName("Foreground");
		
		MapEditor mapEditor = new MapEditor(600, 500);
		mapEditor.addMapLayer(backgroundLayer);
		mapEditor.addMapLayer(middleLayer);
		mapEditor.addMapLayer(foregroundLayer);

		TilePicker tilePicker = new TilePicker(standartTileSet);
		
		mapEditor.start();
		tilePicker.start();
	}
	
	public void testGrid() {
		Grid g = new Grid(500, 500, 32);
		RootPanel.get().add(g.getCanvasLayer().getCanvas());
		
	}
	
	public static boolean isDevelopmentMode() {
	    return !GWT.isProdMode() && GWT.isClient();
	}
}
