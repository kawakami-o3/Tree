package tree;

public class NodeRB extends AbstractNode<NodeRB> {
  public enum Color {
    RED("r"),BLACK("B");
   
    private String shortName; 
    private Color(String shortName) {
      this.shortName = shortName;
    }

    public String getShortName() {
      return shortName;
    }
  }


  private Color color;

  public NodeRB(Long key) {
    super(key);
    color = Color.BLACK;
  }

  public boolean isBlack() {
    return color == Color.BLACK;
  }

  public boolean isRed() {
    return color == Color.RED;
  }

  public Color getColor() {
    return color;
  }

  public NodeRB setColor(Color color) {
    this.color = color;
    return this;
  }

  public NodeRB setRed() {
    this.color = Color.RED;
    return this;
  }

  public NodeRB setBlack() {
    this.color = Color.BLACK;
    return this;
  }
/*
  public String toString() {
    if (getKey() == null) {
      return "null";
    }
    return ""+getKey()+", " + getColor().toString() + " <= "
      + getParent().toString() + ", " + getLeft().toString() + ", " + getRight().toString();
  }
  */
}
