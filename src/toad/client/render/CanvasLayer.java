package toad.client.render;

import toad.client.Drawable;
import toad.client.util.CssColors;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

/**
 * @author Admin
 *
 */
public class CanvasLayer implements Drawable {
	
	public static int count = 0;
	
	private Canvas canvas;
	private Context2d ctx;
	private int width;
	private int height;
	private boolean stale = false;
	private boolean visible = true;
	
	public CanvasLayer(int width, int height) {
		this.width = width;
		this.height = height;
		
		canvas = Canvas.createIfSupported();
		ctx = canvas.getContext2d();

		canvas.setWidth(width + "px");
		canvas.setHeight(height + "px");
		
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
		
	}
	
	public void draw(Context2d context) {
		context.drawImage(ctx.getCanvas(), 0, 0);
		//ctx.drawImage(context.getCanvas(), 0, 0);
	}
	
	public void clear() {
		ctx.clearRect(0, 0, width, height);
	}

	// Time to redraw?
	public boolean isStale() {
		return stale;
	}

	public void setStale(boolean stale) {
		this.stale = stale;
	}
	
	public Context2d getContext() {
		return ctx;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void testDraw() {
		ctx.setStrokeStyle(CssColors.BLACK);
		ctx.beginPath();
		ctx.moveTo(10, 10);
		ctx.lineTo(width, height);
		ctx.closePath();
		ctx.stroke();
		
	}
	
}
