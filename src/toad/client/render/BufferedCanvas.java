package toad.client.render;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class BufferedCanvas {
	private Canvas canvas;
	private Context2d context;
	private Canvas canvasBuffer;
	private Context2d contextBuffer;
	private int width;
	private int height;
	
	public BufferedCanvas(int width, int height) {
		this.width = width;
		this.height = height;
		
		canvas = Canvas.createIfSupported();
		context = canvas.getContext2d();
		initCanvas(canvas);
		
		canvasBuffer = Canvas.createIfSupported();
		contextBuffer = canvas.getContext2d();
		initCanvas(canvasBuffer);
		
	}
	
	private void initCanvas(Canvas canvas) {
		canvas.setWidth(width + "px");
		canvas.setHeight(height + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
	}
	
	public void flushBuffer() {
		context.drawImage(contextBuffer.getCanvas(), 0, 0);
	}

	public void clearBuffer() {
		contextBuffer.clearRect(0, 0, width, height);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Context2d getContext() {
		return context;
	}

	public void setContext(Context2d context) {
		this.context = context;
	}

	public Canvas getCanvasBuffer() {
		return canvasBuffer;
	}

	public void setCanvasBuffer(Canvas canvasBuffer) {
		this.canvasBuffer = canvasBuffer;
	}

	public Context2d getContextBuffer() {
		return contextBuffer;
	}

	public void setContextBuffer(Context2d contextBuffer) {
		this.contextBuffer = contextBuffer;
	}
	
}
