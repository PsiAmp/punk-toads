package toad.client;

import toad.client.EditorToolset.Tool;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

public class MapEditor {

	private double perspective = 0;
	private boolean gridEnabled = true;
	private BufferedCanvas bufferedCanvas;
	private int canvasHeight;
	private int canvasWidth;
	private Timer canvasUpdateTimer;
	private boolean updateBuffer = true;
	private boolean updateLayerBuffer = true;

	// TODO change some time
	private MapLayer activeLayer;
	private int currentTileX;
	private int currentTileY;

	private Grid grid;

	private boolean isMouseDown = false;
	
	private MapLayerHolder mapLayerHolder;
	private EditorToolset editorToolset; 
	
	public MapEditor(int canvasWidth, int canvasHeight) {
		this.canvasHeight = canvasHeight;
		this.canvasWidth = canvasWidth;

		grid = new Grid(canvasWidth, canvasHeight);

		bufferedCanvas = new BufferedCanvas(canvasWidth, canvasHeight);

		RootPanel.get("mapContainer").add(bufferedCanvas.getCanvas());
		
		editorToolset = new EditorToolset();

		registerMouseEvents();

		mapLayerHolder = new MapLayerHolder();
		mapLayerHolder.setOnLayerChange(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent e) {
				activeLayer = mapLayerHolder.getActiveLayer();
				currentTileX = -1;
			}
		});
		
		canvasUpdateTimer = new Timer() {
			@Override
			public void run() {
				render();
			}
		};
	}

	public void addLayer(MapLayer layer) {
		mapLayerHolder.addlayer(layer);
		activeLayer = mapLayerHolder.getActiveLayer();
	}

	private int eventToTileX(int x) {
		return x / activeLayer.getTileSize();
	}

	private int eventToTileY(int y) {
		return y / activeLayer.getTileSize();
	}

	public void start() {
		if (PunkToads.isDevelopmentMode()) {
			System.out.println("Dev mode enabled. Canvas update timer set to 33.");
			canvasUpdateTimer.scheduleRepeating(33);
		} else {
			canvasUpdateTimer.scheduleRepeating(13);
		}
	}

	public void stop() {
		canvasUpdateTimer.cancel();
	}

	public void render() {
		Context2d ctx = bufferedCanvas.getContextBuffer();
		if (updateBuffer) {
			ctx.save();
			// Coordinate translation so brush draws from 0,0 coordinate.
			ctx.translate(0.5, 0.5);

			ctx.setFillStyle(CssColors.LIGHT_GREY);
			ctx.fillRect(0, 0, canvasWidth, canvasHeight);
			ctx.restore();
			
			int i=0;
			for (MapLayer layer : mapLayerHolder.getMapLayerList()) {
				if (updateLayerBuffer) {
					layer.draw();
				}
				double shift = 0;
				if (i == 0 || i == 2) {
					shift = perspective/(canvasWidth/2/layer.getTileSize());
					if (i == 0)
						shift *= -1;
				}
				
				ctx.drawImage(layer.getContext().getCanvas(), shift, 0);
				i++;
			}

			if (gridEnabled) {
				ctx.drawImage(grid.getContext().getCanvas(), 0, 0);
			}
			updateBuffer = false;
			updateLayerBuffer = false;
		}
		bufferedCanvas.flushBuffer();
	}

	public boolean isGrid() {
		return gridEnabled;
	}

	public void setGrid(boolean grid) {
		this.gridEnabled = grid;
	}

	private void registerMouseEvents() {
		Canvas canvas = bufferedCanvas.getCanvas();
		canvas.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				isMouseDown = true;
				mouseAction(event.getX(), event.getY());
			}
		});

		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			int mid = canvasWidth / 2;
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				if (isMouseDown) {
					mouseAction(event.getX(), event.getY());
				}
				if (event.isControlKeyDown()) {
					perspective = event.getX() - mid;
					updateBuffer = true;
				} else {
					perspective = 0;
				}
			}
		});

		canvas.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				isMouseDown = false;
			}
		});
		
		canvas.addMouseOutHandler( new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				isMouseDown = false;
			}
		});
	}

	private void mouseAction(int x, int y) {
		int tileX = eventToTileX(x);
		int tileY = eventToTileY(y);
		if (tileX != currentTileX || tileY != currentTileY) {
			currentTileX = tileX;
			currentTileY = tileY;
			
			if (editorToolset.getTool() == Tool.BRUSH)
				activeLayer.set(tileX, tileY);
			else if (editorToolset.getTool() == Tool.ERASER)
				activeLayer.erase(tileX, tileY);
			
			updateBuffer = true;
			updateLayerBuffer = true;
		}
	}

}
