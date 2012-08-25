package tree;

abstract public class AbstractNode<T extends AbstractNode<T>> {
  private Long key;
  private T parent;
  private T right;
  private T left;

  public AbstractNode(Long key) {
    this.key = key;
    parent=null;
    right=null;
    left=null;
  }

  public T setKey(Long key) {
    this.key = key;
    return (T)this;
  }

  public T setParent(T parent) {
    this.parent = parent;
    return (T)this;
  }
 
  public T setRight(T right) {
    this.right = right;
    return (T)this;
  }

  public T setLeft(T left) {
    this.left = left;
    return (T)this;
  }
 
  public Long getKey() {
    return key;
  }

  public T getParent() {
    return parent;
  }

  public T getRight() {
    return right;
  }

  public T getLeft() {
    return left;
  }

}

