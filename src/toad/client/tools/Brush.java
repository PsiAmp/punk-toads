package toad.client.tools;

import toad.client.TileSet;

public class Brush {

	private TileSet tileSet;
	// TODO set a proper name
	private int[][] data;
	private int width;
	private int height;
	private int tile;
	private boolean singleTile;
	private boolean stampModeEnabled = false;
	// just updated data that wasn't used to paint
	private boolean virginBrush;

	public Brush() {
	}

	public TileSet getTileSet() {
		return tileSet;
	}

	public void setTileSet(TileSet tileSet) {
		this.tileSet = tileSet;
	}

	public int[][] getData() {
		return data;
	}

	public void setData(int[][] data) {
		this.data = data;
		singleTile = false;
		virginBrush = true;
		if (data != null) {
			width = data.length;
			height = data[0].length;
		} else {
			width = 0;
			height = 0;
		}
	}

	public void setTile(int tile) {
		this.tile = tile;
		singleTile = true;
		width = 1;
		height = 1;
		virginBrush = true;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTile() {
		return tile;
	}

	public boolean isSingleTile() {
		return singleTile;
	}

	public boolean isStampModeEnabled() {
		return stampModeEnabled;
	}

	public void setStampModeEnabled(boolean stampModeEnabled) {
		this.stampModeEnabled = stampModeEnabled;
	}

	public boolean isVirginBrush() {
		return virginBrush;
	}

}
