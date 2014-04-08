package toad.client;

import toad.client.EditorToolset.Tool;
import toad.client.tools.Eraser;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class CursorOverlay {
	
	private Canvas canvas;
	private Context2d context;
	private int height;
	private int width;
	
	public CursorOverlay(int width, int height) {
		this.width = width;
		this.height = height;
		canvas = Canvas.createIfSupported();
		context = canvas.getContext2d();
		initCanvas(canvas);
	}
	
	private void initCanvas(Canvas canvas) {
		canvas.setWidth(width + "px");
		canvas.setHeight(height + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
	}
	
	public void repaint(Tool tool, int x, int y, int tileSize){
		context.clearRect(0, 0, width, height);
		
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
	
	public Context2d getContext2d() {
		return context;
	}
}
