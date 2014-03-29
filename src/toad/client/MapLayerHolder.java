package toad.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

public class MapLayerHolder {

	private ListBox layerBox;
	private ArrayList<MapLayer> mapLayerList = new ArrayList<>();
	
	public MapLayerHolder() {
		layerBox = new ListBox();
		RootPanel.get("layerSelector").add(layerBox);
	}
	
	public void addlayer(MapLayer layer) {
		mapLayerList.add(layer);
		layerBox.addItem(layer.getName());
	}
	
	public MapLayer getActiveLayer() {
		return mapLayerList.get(layerBox.getSelectedIndex());
	}

	public ArrayList<MapLayer> getMapLayerList() {
		return mapLayerList;
	}

	public void setOnLayerChange(ChangeHandler handler){
		layerBox.addChangeHandler(handler);
	}
	
}
