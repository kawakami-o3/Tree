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
    return Math.max(getHeight(x.getL(),height+1),getHeight(x.getR(),height+1));
  }

  public void inorderMap(Block<T> block) {
    inorderMap(root, block);
  }

  public void inorderMap(T x, Block<T> block) {
    if (x!=getNil()) {
      inorderMap(x.getL(),block);
      block.execute(x);
      inorderMap(x.getR(),block);
    }
  }

  public List<T> getListInorderWalk() {
    List<T> result = new ArrayList<T>();
    inorderWalk(getRoot(), result);
    return result;
  }

  public void inorderWalk(T x, List<T> list) {
    if (x != getNil()) {
      inorderWalk(x.getL(), list);
      list.add(x);
      inorderWalk(x.getR(), list);
    }
  }


  public void inorderWalk() {
    inorderWalk(root);
    System.out.println("");
  }

  private void inorderWalk(T x) {
    if (x!=getNil()) {
      inorderWalk(x.getL());
      System.out.print(" "+x.getKey());
      inorderWalk(x.getR());
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
      return search(x.getL(),k);
    } else {
      return search(x.getR(),k);
    }
  }

  public T searchIterative(Long k) {
    return searchIterative(k);
  }

  public T searchIterative(T x, Long k) {
    while (x != getNil() && (! k.equals(x.getKey()))) {
      if (k < x.getKey()) {
        x = x.getL();
      } else {
        x = x.getR();
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
        x = x.getL();
      } else {
        x = x.getR();
      }
    }

    z.setParent(y);

    if (y==getNil()) {
      //setRoot(z);
      root = z;
    } else {
      if (z.getKey() < y.getKey()) {
        y.setL(z);
      } else {
        y.setR(z);
      }
    }
  }

  public T minimum(T x) {
    while (x.getL() != getNil()) {
      x = x.getL();
    }
    return x;
  }

  public T maximum(T x) {
    while (x.getR() != getNil()) {
      x = x.getR();
    }
    return x;
  }

  public T minimumRec(T x) {
    return x.getL()==getNil() ? x : minimumRec(x.getL());
  }

  public T maximumRec(T x) {
    return x.getR()==getNil() ? x : maximumRec(x.getR());
  }

  public T successor(T x) {
    if (x.getR() != getNil()) {
      return minimum(x.getR());
    }
    T y = x.getParent();
    while (y!=getNil() && x == y.getR()) {
      x = y;
      y = y.getParent();
    }
    return y;
  }

  public T predecessor(T x) {
    if (x.getL() != getNil()) {
      return maximum(x.getL());
    }
    T y = x.getParent();
    while (y!=getNil() && x == y.getL()) {
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
      if (t.getL() == getNil()) {
        x.setParent(t);
        t.setL(x);
      } else {
        insertRecPartial(t.getL(),x);
      }
    } else {
      if (t.getR() == getNil()) {
        x.setParent(t);
        t.setR(x);
      } else {
        insertRecPartial(t.getR(),x);
      }
    }
  }

  public T deleteNode(T z) {
    T y;
    if (z.getR() == getNil() || z.getL() == getNil()) {
      y = z;
    } else {
      y = successor(z);
    }

    T x;
    if (y.getL() != getNil()) {
      x = y.getL();
    } else {
      x = y.getR();
    }

    if (x!=getNil()) {
      x.setParent(y.getParent());
    }

    if (y.getParent() == getNil()) {
      setRoot(x);
    } else {
      if (y.getParent().getL() == y) {
        y.getParent().setL(x);
      } else {
        y.getParent().setR(x);
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

