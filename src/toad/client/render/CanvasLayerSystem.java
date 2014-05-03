package toad.client.render;

import java.util.ArrayList;

import toad.client.Drawable;

import com.google.gwt.canvas.dom.client.Context2d;

public class CanvasLayerSystem implements Drawable {
	
	private ArrayList<Drawable> layers = new ArrayList<>();
	
	public CanvasLayerSystem() {
	}
	
	public void addLayer(Drawable layer) {
		layers.add(layer);
	}
	
	public void draw(Context2d mainContext) {
		// TODO: add optimization if all layers and !stale do not draw anything
		
		for (Drawable layer : layers) {
			layer.draw(mainContext);
		}
	}
	
}
