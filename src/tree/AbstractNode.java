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

  @SuppressWarnings("unchecked")
  public T setKey(Long key) {
    this.key = key;
    return (T)this;
  }

  @SuppressWarnings("unchecked")
  public T setParent(T parent) {
    this.parent = parent;
    return (T)this;
  }
 
  @SuppressWarnings("unchecked")
  public T setR(T right) {
    this.right = right;
    return (T)this;
  }

  @SuppressWarnings("unchecked")
  public T setL(T left) {
    this.left = left;
    return (T)this;
  }
 
  public Long getKey() {
    return key;
  }

  public T getParent() {
    return parent;
  }

  public T getR() {
    return right;
  }

  public T getL() {
    return left;
  }

}

