package tree;

import static tree.Color.*;

public class NodeOS extends AbstractNode<NodeOS> {

	private Color color;
	private int size;

	public NodeOS(Long key) {
		super(key);
		color = BLACK;
		size = 1;
	}

	public boolean isBlack() {
		return color == BLACK;
	}
	
	public boolean isRed() {
		return color == RED;
	}

	public Color getColor() {
		return color;
	}

	public NodeOS setColor(Color color) {
		this.color = color;
		return this;
	}

	public NodeOS setRed() {
		this.color = RED;
		return this;
	}

	public NodeOS setBlack() {
		this.color = BLACK;
		return this;
	}
	
	public int getSize() {
		return size;
	}
	
	public NodeOS setSize(int size) {
		this.size = size;
		return this;
	}
}
