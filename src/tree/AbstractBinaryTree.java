package tree;

import java.util.List;
import java.util.ArrayList;

abstract public class AbstractBinaryTree<T extends AbstractNode<T>> {
  private T root;

  abstract public T getNil();

  public void setRoot(T root) {
    //System.out.println("setRoot> " + (this.root == null ? "null" : this.root.getKey()));
    this.root = root;
  }

  public T getRoot() {
    return root;
  }

  public int getDepth(T x, int depth) {
    return x.getParent()==getNil() ? depth : getDepth(x.getParent(),depth+1);
  }

  public int getHeight() {
    return getHeight(root,0);
  }

  public int getHeight(T x, int height) {
    if (x == getNil()) {
      return height;
    }
    return Math.max(getHeight(x.getLeft(),height+1),getHeight(x.getRight(),height+1));
  }

  public void inorderMap(Block<T> block) {
    inorderMap(root, block);
  }

  public void inorderMap(T x, Block<T> block) {
    if (x!=getNil()) {
      inorderMap(x.getLeft(),block);
      block.execute(x);
      inorderMap(x.getRight(),block);
    }
  }

  public List<T> getListInorderWalk() {
    List<T> result = new ArrayList<T>();
    inorderWalk(getRoot(), result);
    return result;
  }

  public void inorderWalk(T x, List<T> list) {
    if (x != getNil()) {
      inorderWalk(x.getLeft(), list);
      list.add(x);
      inorderWalk(x.getRight(), list);
    }
  }


  public void inorderWalk() {
    inorderWalk(root);
    System.out.println("");
  }

  private void inorderWalk(T x) {
    if (x!=getNil()) {
      inorderWalk(x.getLeft());
      System.out.print(" "+x.getKey());
      inorderWalk(x.getRight());
    }
  }

  public T search(Long k) {
    return search(root, k);
  }

  public T search(T x, Long k) {
    if (x==getNil() || k.equals(x.getKey())) {
      return x;
    }
    if (k < x.getKey()) {
      return search(x.getLeft(),k);
    } else {
      return search(x.getRight(),k);
    }
  }

  public T searchIterative(Long k) {
    return searchIterative(k);
  }

  public T searchIterative(T x, Long k) {
    while (x != getNil() && (! k.equals(x.getKey()))) {
      if (k < x.getKey()) {
        x = x.getLeft();
      } else {
        x = x.getRight();
      }
    }
    return x;
  }

  public void insert(T z) {
    T y = getNil();
    T x = root;
    while (x!=getNil()) {
      y = x;
      if (z.getKey() < x.getKey()) {
        x = x.getLeft();
      } else {
        x = x.getRight();
      }
    }

    z.setParent(y);

    if (y==getNil()) {
      //setRoot(z);
      root = z;
    } else {
      if (z.getKey() < y.getKey()) {
        y.setLeft(z);
      } else {
        y.setRight(z);
      }
    }
  }

  public T minimum(T x) {
    while (x.getLeft() != getNil()) {
      x = x.getLeft();
    }
    return x;
  }

  public T maximum(T x) {
    while (x.getRight() != getNil()) {
      x = x.getRight();
    }
    return x;
  }

  public T minimumRec(T x) {
    return x.getLeft()==getNil() ? x : minimumRec(x.getLeft());
  }

  public T maximumRec(T x) {
    return x.getRight()==getNil() ? x : maximumRec(x.getRight());
  }

  public T successor(T x) {
    if (x.getRight() != getNil()) {
      return minimum(x.getRight());
    }
    T y = x.getParent();
    while (y!=getNil() && x == y.getRight()) {
      x = y;
      y = y.getParent();
    }
    return y;
  }

  public T predecessor(T x) {
    if (x.getLeft() != getNil()) {
      return maximum(x.getLeft());
    }
    T y = x.getParent();
    while (y!=getNil() && x == y.getLeft()) {
      x = y;
      y = y.getParent();
    }
    return y;
  }

  public void insertRec(T x) {
    if (root == getNil()) {
      root = x;
    } else {
      insertRecPartial(root, x);
    }
  }


  public void insertRecPartial(T t, T x) {
    if (x.getKey() < t.getKey()) {
      if (t.getLeft() == getNil()) {
        x.setParent(t);
        t.setLeft(x);
      } else {
        insertRecPartial(t.getLeft(),x);
      }
    } else {
      if (t.getRight() == getNil()) {
        x.setParent(t);
        t.setRight(x);
      } else {
        insertRecPartial(t.getRight(),x);
      }
    }
  }

  public T deleteNode(T z) {
    T y;
    if (z.getRight() == getNil() || z.getLeft() == getNil()) {
      y = z;
    } else {
      y = successor(z);
    }

    T x;
    if (y.getLeft() != getNil()) {
      x = y.getLeft();
    } else {
      x = y.getRight();
    }

    if (x!=getNil()) {
      x.setParent(y.getParent());
    }

    if (y.getParent() == getNil()) {
      setRoot(x);
    } else {
      if (y.getParent().getLeft() == y) {
        y.getParent().setLeft(x);
      } else {
        y.getParent().setRight(x);
      }
    }

    if (y!=z) {
      z.setKey(y.getKey());
    }

    return y;
  }

  public void print() {
    List<T> nodes = getListInorderWalk();
    
    List<Integer> depths = new ArrayList<Integer>();
    int maxDepth = 0;
    for (T n : nodes) {
      int h = getDepth(n,0);
      maxDepth = Math.max(maxDepth, h);
      depths.add(h);
    }

    for (int i=0 ; i<=maxDepth ; i++)  {
      for (int j=0 ; j<nodes.size() ; j++) {

        if (i==depths.get(j)) {
          //System.out.printf("%3d["+nodes.get(i).getColor().getShortName()+"]",nodes.get(j).getKey());
          System.out.printf("%5d",nodes.get(j).getKey());
        } else {
          System.out.print("     ");
        }
      }
      System.out.println("");
    }
  }

}

