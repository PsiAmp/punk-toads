package toad.client.tools;

public class Eraser {

	private int size = 1;

	public Eraser() {
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size < 1)
			throw new IllegalArgumentException("Eraser size can't be less then 1");
		this.size = size;
	}
	
}
