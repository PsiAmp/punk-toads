package toad.client;

import toad.client.EditorToolset.Tool;
import toad.client.render.CanvasLayer;
import toad.client.tools.Eraser;
import toad.client.util.CssColors;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class CursorOverlay {
	
	private CanvasLayer canvasLayer;
	
	public CursorOverlay(int width, int height) {
		canvasLayer = new CanvasLayer(width, height);
	}
	
	public void repaint(Tool tool, int x, int y, int tileSize){
		Context2d context = canvasLayer.getContext();
		context.clearRect(0, 0, canvasLayer.getWidth(), canvasLayer.getHeight());
		
		if (tool == Tool.BRUSH) {
			
		} else if (tool == Tool.ERASER) {
			Eraser eraser = ToolBox.getInstance().getEraser();
			int size = eraser.getSize()*tileSize;
			context.save();
			context.setLineWidth(1.0);
			context.setStrokeStyle(CssColors.RED);
			context.setFillStyle(CssColors.LIGHT_GREY);
			
			context.rect(x, y, size, size);
			context.stroke();
			context.setGlobalAlpha(0.25);
			context.fill();
			
			context.restore();
		}
	}

	public CanvasLayer getCanvasLayer() {
		return canvasLayer;
	}
	
}
