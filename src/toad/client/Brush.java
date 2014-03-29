package toad.client;

public class Brush {
	
	private TileSet tileSet;
	private int[][] data;
	private int tile;
	private boolean singleTile;
	
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
	}

	public int getTile() {
		return tile;
	}

	public void setTile(int tile) {
		this.tile = tile;
		singleTile = true;
	}

	public boolean isSingleTile() {
		return singleTile;
	}

	public void setSingleTile(boolean singleTile) {
		this.singleTile = singleTile;
	}
	
}
