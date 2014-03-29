package toad.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

public class TilePicker {

	private int grabStartX;
	private int grabStartY;
	private boolean isMouseDown = false;
	private boolean updateBuffer = true;
	private BufferedCanvas bufferedCanvas;
	private static final int canvasHeight = 600;
	private static final int canvasWidth = 600;
	private Timer canvasUpdateTimer;

	private Grid grid;
	private SelectionHighlight highlight;

	private TileSet activeTileset;
	private Brush brush;

	public TilePicker(TileSet tileSet) {

		grid = new Grid(canvasWidth, canvasHeight);
		highlight = new SelectionHighlight(canvasWidth, canvasHeight);

		bufferedCanvas = new BufferedCanvas(canvasWidth, canvasHeight);
		activeTileset = tileSet;

		RootPanel.get("tilepickerContainer").add(bufferedCanvas.getCanvas());

		registerMouseEvents();

		canvasUpdateTimer = new Timer() {
			@Override
			public void run() {
				render();
			}
		};
	}

	public void start() {
		if (PunkToads.isDevelopmentMode()) {
			canvasUpdateTimer.scheduleRepeating(33);
		} else {
			canvasUpdateTimer.scheduleRepeating(13);
		}
	}

	public void stop() {
		canvasUpdateTimer.cancel();
	}

	public void render() {
		if (updateBuffer) {
			bufferedCanvas.clearBuffer();
			Context2d ctx = bufferedCanvas.getContextBuffer();

			ImageElement imageElement = activeTileset.getImgElement();
			if (imageElement != null) {
				ctx.drawImage(imageElement, 0, 0);
			}

			ctx.drawImage(highlight.getContext().getCanvas(), 0, 0);

			// Grid
			ctx.drawImage(grid.getContext().getCanvas(), 0, 0);

			updateBuffer = false;
		}

		bufferedCanvas.flushBuffer();
	}

	private void mouseAction(int x, int y) {
		int tileSize = activeTileset.getTileSize();

		int x1 = Math.min(x, grabStartX);
		int x2 = Math.max(x, grabStartX);
		int y1 = Math.min(y, grabStartY);
		int y2 = Math.max(y, grabStartY);

		// Making x1,y1 start at the top-left corner of tile so when dx is less then
		// N tiles it doesn't count as N-1
		x1 -= x1 % tileSize;
		y1 -= y1 % tileSize;

		int tilesetWidth = activeTileset.getWidth();
		int tilesetHeight = activeTileset.getHeight();

		// Check x1, y1 out of tileset
		if (x1 < tilesetWidth && y1 < tilesetHeight) {
			// Check x1, y1 < 0
			if (x1 < 0)
				x1 = 0;
			if (y1 < 0)
				y1 = 0;
			// Check x2, y2 out of tileset
			if (x2 >= tilesetWidth)
				x2 = tilesetWidth - 1;
			if (y2 >= tilesetHeight)
				y2 = tilesetHeight - 1;

			// Get selected tiles
			int dx = x2 - x1;
			int dy = y2 - y1;

			// Tiles in row, tiles in column
			int xTiles = dx / tileSize + 1;
			int yTiles = dy / tileSize + 1;

			// Starting tiles
			int startX = x1 / tileSize;
			int startY = y1 / tileSize;
			int tileInRow = activeTileset.getTilesX();
			
			// if single tile set Brush in non-array mode
			if (yTiles == 1 && xTiles == 1) {
				brush.setTile(startX + startY * tileInRow);
			} else {
				int data[][] = new int[xTiles][yTiles];

				for (int i = 0; i < xTiles; i++) {
					for (int j = 0; j < yTiles; j++) {
						data[i][j] = (startX + i) + (startY + j) * tileInRow;
					}
				}

				brush.setData(data);
			}
		}
	}

	private void registerMouseEvents() {
		Canvas canvas = bufferedCanvas.getCanvas();

		canvas.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent e) {
				isMouseDown = true;
				grabStartX = e.getX();
				grabStartY = e.getY();
				highlight.clear();
				updateBuffer = true;
			}
		});

		canvas.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent e) {
				isMouseDown = false;
				mouseAction(e.getX(), e.getY());

				int x1 = Math.min(grabStartX, e.getX());
				int y1 = Math.min(grabStartY, e.getY());
				int x2 = Math.max(grabStartX, e.getX());
				int y2 = Math.max(grabStartY, e.getY());

				int tileSize = activeTileset.getTileSize();

				x1 -= x1 % tileSize;
				y1 -= y1 % tileSize;

				x2 = (x2 / tileSize + 1) * tileSize;
				y2 = (y2 / tileSize + 1) * tileSize;

				highlight.highlightArea(x1, y1, x2 - x1, y2 - y1);
				updateBuffer = true;
			}
		});

		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent e) {
				if (isMouseDown) {
					int x = e.getX();
					int y = e.getY();
					int dx = x - grabStartX;
					int dy = y - grabStartY;
					highlight.highlightArea(grabStartX, grabStartY, dx, dy);
					updateBuffer = true;
				}
			}
		});

	}

	public void setBrush(Brush brush) {
		this.brush = brush;
	}

}
