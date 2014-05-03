package toad.client;

import java.util.Arrays;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;

import toad.client.render.CanvasLayer;
import toad.client.tools.Brush;

public class TileLayer {
	private final static String zeroWidthHeightErrorMessage = "ERROR: Can't init layer. Width or height is 0.";
	private final static String outOfBoundsErrorMessage = "ERROR: Index is out of bounds.";
	public final static int EMPTY_CELL = -1;

	// in tiles
	private int tilesX;
	private int tilesY;
	private TileSet tileset;

	public int[][] data;

	private String name = "";
	private CanvasLayer canvasLayer;
	
	private boolean needRedraw = true;

	public TileLayer(int tilesX, int tilesY, TileSet tileSet) {
		this.tileset = tileSet;
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		init();
		canvasLayer = new CanvasLayer(tilesX * tileset.getTileSize(), tilesY * tileset.getTileSize());
	}

	public void draw() {
		int tileSize = tileset.getTileSize();
		int tilesInRow = tileset.getTilesX();

		ImageElement img = tileset.getImgElement();
		
		canvasLayer.clear();
		Context2d ctx = canvasLayer.getContext();
		
		for (int i = 0; i < data.length; i++) {
			int x = i * tileSize;

			for (int j = 0; j < data[i].length; j++) {
				int y = j * tileSize;
				int tile = data[i][j];
				if (tile != -1) {
					int tileX = tile % tilesInRow * tileSize;
					int tileY = tile / tilesInRow * tileSize;

					ctx.drawImage(img, tileX, tileY, tileSize, tileSize, x, y, tileSize, tileSize);
				}
			}
		}
		needRedraw = false;
	}
	
	// Paint layer at location x,y with current Brush
	public void set(int x, int y) {
		if (!isOutOfBounds(x, y)) {
			Brush brush = ToolBox.getInstance().getBrush();
			// Check if brush is a single tile
			if (brush.isSingleTile()) {
				data[x][y] = brush.getTile();
			} else {
				int[][] brushData = brush.getData();
				
				int brushWidth = brushData.length;
				int dataWidth = data.length;
				int dataHeight = data[0].length;
				
				if (x + brushWidth > dataWidth)
					brushWidth = dataWidth - x;
				
				int brushHeight = brushData[0].length;
				if (y + brushHeight > dataHeight)
					brushHeight = dataHeight - y;
				
				for (int i = 0; i < brushWidth; i++) {
					// TODO: Add check to fix arrayOutOfBoundsException
					System.arraycopy(brushData[i], 0, data[i + x], y, brushHeight);
				}
			}
		}
		needRedraw = true;
	}

	private boolean isOutOfBounds(int x, int y) {
		if (x < tilesX && y < tilesY) {
			return false;
		} else {
			System.out.println(outOfBoundsErrorMessage);
			return true;
		}
	}

	// Set width and height multiple of tileSize
	private void normalizeDimentionsToTileSize(int width, int height, int tileSize) {
		int remainderWidth = width % tileSize;
		int remainderHeight = height % tileSize;
		width -= remainderWidth;
		height -= remainderHeight;
		this.tilesX = width;
		this.tilesY = height;
	}

	// Initialize Layer data[][] array. If it is already existing, stretch existing.
	private void init() {
		if (data == null) {
			data = new int[tilesX][tilesY];
			for (int i = 0; i < tilesX; i++) {
				Arrays.fill(data[i], EMPTY_CELL);
			}
		} else {
			if (tilesX > 0 && tilesY > 0) {
				// TODO: fix this. Right now it erases old data instead of resizing it
				int buff[][] = new int[1][1];// data.clone();
				data = new int[tilesX][tilesY];
				for (int i = 0; i < Math.min(buff.length, data.length); i++) {
					System.arraycopy(buff[i], 0, data[i], 0, Math.min(buff[i].length, data[i].length));
				}
			} else {
				System.out.println(zeroWidthHeightErrorMessage);
			}
		}
		needRedraw = true;
		System.out.println("TileLayer[" + tilesX + "][" + tilesY + "]; created");
	}

	public void erase(int tileX, int tileY) {
		int eraserSize = ToolBox.getInstance().getEraser().getSize();

		if (eraserSize == 1) {
			data[tileX][tileY] = EMPTY_CELL;
		} else {
			for (int i = 0; i < eraserSize; i++) {
				Arrays.fill(data[tileX + i], tileY, tileY + eraserSize, EMPTY_CELL);
			}
		}
		needRedraw = true;
	}

	public int getTileSize() {
		return tileset.getTileSize();
	}

	public TileSet getTileSet() {
		return tileset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[][] getData() {
		return data;
	}

	public CanvasLayer getCanvasLayer() {
		return canvasLayer;
	}

	public boolean isNeedRedraw() {
		return needRedraw;
	}
}
