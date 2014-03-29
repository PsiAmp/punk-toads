package toad.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class EditorToolset {

	public enum Tool {
		BRUSH, ERASER
	};

	ToggleButton brush;
	ToggleButton eraser;
	
	private Tool tool = Tool.BRUSH;

	public EditorToolset() {
		brush = new ToggleButton("Brush");
		eraser = new ToggleButton("Eraser");

		brush.setDown(true);
		eraser.setDown(false);

		brush.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tool = Tool.BRUSH;
				brush.setDown(true);
				eraser.setDown(false);
			}
		});

		eraser.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tool = Tool.ERASER;
				eraser.setDown(true);
				brush.setDown(false);
			}
		});

		RootPanel.get("tools").add(brush);
		RootPanel.get("tools").add(eraser);
	}

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
}
