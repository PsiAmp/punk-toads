package toad.client;

import toad.client.render.CanvasLayer;
import toad.client.util.CssColors;

import com.google.gwt.canvas.dom.client.Context2d;

public class Grid {

	private boolean dashed = false;
	private int stepX = 16;
	private int stepY = 16;
	private int startX = 0;
	private int startY = 0;
	private int width;
	private int height;
	private CanvasLayer canvasLayer;

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		canvasLayer = new CanvasLayer(width, height);
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
	
	private void drawGrid() {
		Context2d ctx = canvasLayer.getContext();
		
		ctx.save();
		ctx.translate(0.5, 0.5);
		ctx.setStrokeStyle(CssColors.BLACK);
		ctx.setLineWidth(1);
		
		ctx.beginPath();

		// Horizontal grid lines
		for (int i = startX; i < height; i += stepY) {
			if (dashed) {
				drawDashedLine(startX, i, width, 2, 2, true, ctx);
			} else {
				ctx.moveTo(startX, i);
				ctx.lineTo(width, i);
			}
		}

		// Vertical grid lines
		for (int i = startY; i < width; i += stepX) {
			if (dashed) {
				drawDashedLine(i, startY, height, 2, 2, false, ctx);
			} else {
				ctx.moveTo(i, startY);
				ctx.lineTo(i, height);
			}

		}
		
		ctx.stroke();
		ctx.restore();
	}

	// draws vertical or horizontal dashed line
	// TODO change to buffered image
	private void drawDashedLine(int x, int y, int length, int dash, int space, boolean horizontal, Context2d ctx) {
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

	public Context2d getContext() {
		return canvasLayer.getContext();
	}

	public CanvasLayer getCanvasLayer() {
		return canvasLayer;
	}
	
	
	
}
