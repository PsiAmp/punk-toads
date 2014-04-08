package toad.client;

import toad.client.tools.Brush;
import toad.client.tools.Eraser;


public class ToolBox {
	
	private static ToolBox instance;
	
	private Brush brush;
	private Eraser eraser;
	
	private ToolBox(){
		brush = new Brush();
		eraser = new Eraser();
	}

	public static ToolBox getInstance() {
		if (instance == null)
			instance = new ToolBox();
		return instance;
	}

	public Brush getBrush() {
		return brush;
	}

	public void setBrush(Brush brush) {
		this.brush = brush;
	}

	public Eraser getEraser() {
		return eraser;
	}

	public void setEraser(Eraser eraser) {
		this.eraser = eraser;
	}
	
}
