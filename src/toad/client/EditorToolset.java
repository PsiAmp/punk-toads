package toad.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class EditorToolset {

	public enum Tool {
		BRUSH, ERASER
	};

	ToggleButton brushButton;
	CheckBox stampModeCheckBox;
	ToggleButton eraserButton;
	ListBox eraserSizeBox;
	
	private Tool tool = Tool.BRUSH;

	public EditorToolset() {
		brushButton = new ToggleButton("Brush");
		eraserButton = new ToggleButton("Eraser");
		stampModeCheckBox = new CheckBox("Stamp Mode");

		brushButton.setDown(true);
		eraserButton.setDown(false);
		stampModeCheckBox.setValue(false);
		
		brushButton.setWidth("80px");
		eraserButton.setWidth("80px");
		
		eraserSizeBox = new ListBox();
		for (int i = 1; i < 6; i++) {
			eraserSizeBox.addItem("" + i);
		}
		
		eraserSizeBox.setSelectedIndex(0);

		brushButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tool = Tool.BRUSH;
				brushButton.setDown(true);
				eraserButton.setDown(false);
			}
		});

		stampModeCheckBox.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ToolBox.getInstance().getBrush().setStampModeEnabled(stampModeCheckBox.getValue());
			}
		});
		
		eraserButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tool = Tool.ERASER;
				eraserButton.setDown(true);
				brushButton.setDown(false);
			}
		});
		
		eraserSizeBox.addChangeHandler( new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int eraserSize = eraserSizeBox.getSelectedIndex() + 1;
				ToolBox.getInstance().getEraser().setSize(eraserSize);
			}
		});
		
		RootPanel.get("tools").add(brushButton);
		RootPanel.get("tools").add(stampModeCheckBox);
		RootPanel.get("tools").add(eraserButton);
		RootPanel.get("tools").add(eraserSizeBox);
	}
	
	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
}
