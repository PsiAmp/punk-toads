package toad.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class Grid {

	private boolean dashed = true;
	private int stepX = 16;
	private int stepY = 16;
	private int startX = 0;
	private int startY = 0;
	private int width;
	private int height;
	private Canvas cvs;
	private Context2d ctx;

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		
		cvs = Canvas.createIfSupported();
		
		cvs.setWidth(width + "px");
		cvs.setHeight(height + "px");
		cvs.setCoordinateSpaceWidth(width);
		cvs.setCoordinateSpaceHeight(height);
		
		ctx = cvs.getContext2d();
		ctx.translate(0.5, 0.5);
		
		drawGrid();
	}

	public Grid(int width, int height, int step) {
		this(width, height, step, step);
	}
	
	public Grid(int width, int height, int stepX, int stepY) {
		this(width, height);
		this.stepX = stepX;
		this.stepY = stepY;		
	}
	
	public Context2d getContext() {
		return ctx;
	}
	
	// TODO change to buffered image method
	private void drawGrid() {
		ctx.save();
		ctx.setStrokeStyle(CssColors.BLACK);
		ctx.setLineWidth(1);

		ctx.beginPath();

		// Horizontal grid lines
		for (int i = startX; i < height; i += stepY) {
			if (dashed) {
				drawDashedLine(startX, i, width, 2, 2, true);
			} else {
				ctx.moveTo(startX, i);
				ctx.lineTo(width, i);
			}
		}

		// Vertical grid lines
		for (int i = startY; i < width; i += stepX) {
			if (dashed) {
				drawDashedLine(i, startY, height, 2, 2, false);
			} else {
				ctx.moveTo(i, startY);
				ctx.lineTo(i, height);
			}

		}

		ctx.closePath();
		ctx.stroke();
		ctx.restore();
	}

	// draws vertical or horizontal dashed line
	// TODO change to buffered image
	private void drawDashedLine(int x, int y, int length, int dash, int space, boolean horizontal) {
		if (horizontal)
			while (x < length) {
				ctx.moveTo(x, y);
				x += dash;
				ctx.lineTo(x, y);
				x += space;
			}
		else
			while (y < length) {
				ctx.moveTo(x, y);
				y += dash;
				ctx.lineTo(x, y);
				y += space;
			}
	}

}
