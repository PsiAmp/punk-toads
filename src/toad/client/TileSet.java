package toad.client;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class TileSet {

	private Image img;
	private ImageElement imgElement;
	private int width;
	private int height;
	private int tilesX;
	private int tilesY;
	private int tileSize;
	//private int selectedTile = -1;

	public TileSet(String path, int tileSize) {
		this.tileSize = tileSize;
		img = new Image(path);

		// TODO add to a hidden block
		RootPanel.get("resourceLoader").add(img);

		img.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				imgElement = ImageElement.as(img.getElement());
				initImageParameters(imgElement.getWidth(), imgElement.getHeight());
			}
		});
	}

	private void initImageParameters(int width, int height) {
		this.width = width;
		this.height = height;
		tilesX = width / tileSize;
		tilesY = height / tileSize;
	}

	public Image getImg() {
		return img;
	}

	public ImageElement getImgElement() {
		return imgElement;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTilesX() {
		return tilesX;
	}

	public int getTilesY() {
		return tilesY;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
