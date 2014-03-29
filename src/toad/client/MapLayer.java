package toad.client;

import java.util.Arrays;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;

public class MapLayer {
	private final static String zeroWidthHeightErrorMessage = "ERROR: Can't init layer. Width or height is 0.";
	private final static String outOfBoundsErrorMessage = "ERROR: Index is out of bounds.";
	public final static int EMPTY_CELL = -1;

	private int width;
	private int height;
	private TileSet tileSet;

	public int[][] data;

	private String name = "";
	private Canvas cvs;
	private Context2d ctx;

	public MapLayer(int width, int height, TileSet tileSet) {
		this.tileSet = tileSet;
		resize(width, height);

		cvs = Canvas.createIfSupported();

		cvs.setWidth(width + "px");
		cvs.setHeight(height + "px");
		cvs.setCoordinateSpaceWidth(width);
		cvs.setCoordinateSpaceHeight(height);

		ctx = cvs.getContext2d();
	}

	public void draw() {
		ctx.clearRect(0, 0, width, height);

		int tileSize = getTileSize();
		int tilesInRow = getTileSet().getTilesX();

		for (int i = 0; i < data.length; i++) {
			int x = i * tileSize;

			for (int j = 0; j < data[i].length; j++) {
				int y = j * tileSize;
				int tile = data[i][j];
				if (tile != -1) {
					int tileX = tile % tilesInRow * tileSize;
					int tileY = tile / tilesInRow * tileSize;

					ImageElement img = getTileSet().getImgElement();
					ctx.drawImage(img, tileX, tileY, tileSize, tileSize, x, y, tileSize, tileSize);
				}
			}
		}
	}

	public Context2d getContext() {
		return ctx;
	}

	// Paint layer at location x,y with current Brush
	public void set(int x, int y, Brush brush) {
		if (!isOutOfBounds(x, y)) {
			
			// Check if brush is a single tile
			if (brush.isSingleTile()) {
				data[x][y] = brush.getTile();
			} else {
				int[][] brushData = brush.getData();
				int len = brushData[0].length;
				for (int i = 0; i < brushData.length && x + i < data.length; i++) {
					System.arraycopy(brushData[i], 0, data[i + x], y, len);
				}
			}
		}
	}

	private boolean isOutOfBounds(int x, int y) {
		if (x < width && y < height) {
			return false;
		} else {
			System.out.println(outOfBoundsErrorMessage);
			return true;
		}
	}

	// Change Layer size to set number of pixels normalized to tile size
	public void resize(int width, int height) {
		normalizeDimentionsToTileSize(width, height, getTileSize());
		initLayer();
	}

	// Set width and height multiple of tileSize
	private void normalizeDimentionsToTileSize(int width, int height, int tileSize) {
		int remainderWidth = width % tileSize;
		int remainderHeight = height % tileSize;
		width -= remainderWidth;
		height -= remainderHeight;
		this.width = width;
		this.height = height;
	}

	// Initialize Layer data[][] array. If it is already existing, stretch existing.
	private void initLayer() {
		if (data == null) {
			data = new int[width][height];
			for (int i = 0; i < width; i++) {
				Arrays.fill(data[i], EMPTY_CELL);
			}
		} else {
			if (width > 0 && height > 0) {
				int buff[][] = new int[1][1];// data.clone();
				data = new int[width][height];
				for (int i = 0; i < Math.min(buff.length, data.length); i++) {
					System.arraycopy(buff[i], 0, data[i], 0, Math.min(buff[i].length, data[i].length));
				}
			} else {
				System.out.println(zeroWidthHeightErrorMessage);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTileSize() {
		return tileSet.getTileSize();
	}

	public TileSet getTileSet() {
		return tileSet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void erase(int tileX, int tileY) {
		data[tileX][tileY] = EMPTY_CELL;
	}
}
