package toad.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class SelectionHighlight {
	private Canvas cvs;
	private Context2d ctx;
	private int width;
	private int height;
	
	public SelectionHighlight(int width, int height) {
		this.width = width;
		this.height = height;
		
		cvs = Canvas.createIfSupported();
		
		cvs.setWidth(width + "px");
		cvs.setHeight(height + "px");
		cvs.setCoordinateSpaceWidth(width);
		cvs.setCoordinateSpaceHeight(height);
		
		ctx = cvs.getContext2d();
		ctx.translate(0.5, 0.5);
	}
	
	public Context2d getContext() {
		return ctx;
	}
	
	public void highlightArea(int x, int y, int dx, int dy) {
		clear();
		
		ctx.beginPath();
		ctx.setFillStyle(CssColors.BLUE_HIGHLIGHT);
		ctx.setGlobalAlpha(0.3);
		ctx.fillRect(x, y, dx, dy);
		
		ctx.setGlobalAlpha(1);
		ctx.setFillStyle(CssColors.BLACK);
		
		ctx.rect(x, y, dx, dy);
		ctx.stroke();
		ctx.closePath();
	}
	
	public void clear() {
		ctx.clearRect(0, 0, width, height);
	}
}
