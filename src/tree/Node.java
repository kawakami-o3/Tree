package tree;

public class Node {
  private Long key;
  private Node parent;
  private Node right;
  private Node left;

  public Node(Long key) {
    this.key = key;
    parent=null;
    right=null;
    left=null;
  }

  public void setKey(Long key) {
    this.key = key;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }
 
  public void setRight(Node right) {
    this.right = right;
  }

  public void setLeft(Node left) {
    this.left = left;
  }
 
  public Long getKey() {
    return key;
  }

  public Node getParent() {
    return parent;
  }

  public Node getRight() {
    return right;
  }

  public Node getLeft() {
    return left;
  }

}

