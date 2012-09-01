package tree;

import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;

public class BinaryTree {
  private Node root;
  /*
  public BinaryTree(Node<T> root) {
    setRoot(root);
  }
  */

  public void setRoot(Node root) {
    this.root = root;
  }

  public Node getRoot() {
    return root;
  }

  public int getDepth(Node x, int depth) {
    return x.getParent()==null ? depth : getDepth(x.getParent(),depth+1);
  }

  public int getHeight() {
    return getHeight(root,0);
  }

  public int getHeight(Node x, int height) {
    if (x == null) {
      return height;
    }
    return Math.max(getHeight(x.getLeft(),height+1),getHeight(x.getRight(),height+1));
  }

  public void inorderMap(Block<Node> block) {
    inorderMap(root, block);
  }

  public void inorderMap(Node x, Block<Node> block) {
    if (x!=null) {
      inorderMap(x.getLeft(),block);
      block.execute(x);
      inorderMap(x.getRight(),block);
    }
  }

  public List<Node> getListInorderWalk() {
    List<Node> result = new ArrayList<Node>();
    inorderWalk(getRoot(), result);
    return result;
  }

  public void inorderWalk(Node x, List<Node> list) {
    if (x != null) {
      inorderWalk(x.getLeft(), list);
      list.add(x);
      inorderWalk(x.getRight(), list);
    }
  }


  public void inorderWalk() {
    inorderWalk(root);
    System.out.println("");
  }

  private void inorderWalk(Node x) {
    if (x!=null) {
      inorderWalk(x.getLeft());
      System.out.print(", "+x.getKey());
      inorderWalk(x.getRight());
    }
  }

  public Node search(Long k) {
    return search(root, k);
  }

  public Node search(Node x, Long k) {
    if (x==null || k.equals(x.getKey())) {
      return x;
    }
    if (k < x.getKey()) {
      return search(x.getLeft(),k);
    } else {
      return search(x.getRight(),k);
    }
  }

  public Node searchIterative(Long k) {
    return searchIterative(k);
  }

  public Node searchIterative(Node x, Long k) {
    while (x != null && (! k.equals(x.getKey()))) {
      if (k < x.getKey()) {
        x = x.getLeft();
      } else {
        x = x.getRight();
      }
    }
    return x;
  }

  public void insert(Node z) {
    Node y = null;
    Node x = root;
    while (x!=null) {
      y = x;
      if (z.getKey() < x.getKey()) {
        x = x.getLeft();
      } else {
        x = x.getRight();
      }
    }

    z.setParent(y);

    if (y==null) {
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

  public Node minimum(Node x) {
    while (x.getLeft() != null) {
      x = x.getLeft();
    }
    return x;
  }

  public Node maximum(Node x) {
    while (x.getRight() != null) {
      x = x.getRight();
    }
    return x;
  }

  public Node minimumRec(Node x) {
    return x.getLeft()==null ? x : minimumRec(x.getLeft());
  }

  public Node maximumRec(Node x) {
    return x.getRight()==null ? x : maximumRec(x.getRight());
  }

  public Node successor(Node x) {
    if (x.getRight() != null) {
      return minimum(x.getRight());
    }
    Node y = x.getParent();
    while (y!=null && x == y.getRight()) {
      x = y;
      y = y.getParent();
    }
    return y;
  }

  public Node predecessor(Node x) {
    if (x.getLeft() != null) {
      return maximum(x.getLeft());
    }
    Node y = x.getParent();
    while (y!=null && x == y.getLeft()) {
      x = y;
      y = y.getParent();
    }
    return y;
  }

  public void insertRec(Node x) {
    if (root == null) {
      root = x;
    } else {
      insertRecPartial(root, x);
    }
  }


  public void insertRecPartial(Node t, Node x) {
    if (x.getKey() < t.getKey()) {
      if (t.getLeft() == null) {
        x.setParent(t);
        t.setLeft(x);
      } else {
        insertRecPartial(t.getLeft(),x);
      }
    } else {
      if (t.getRight() == null) {
        x.setParent(t);
        t.setRight(x);
      } else {
        insertRecPartial(t.getRight(),x);
      }
    }
  }
/*
  public void deleteNode(Node x) {
    if (x.getRight() == null && x.getLeft() == null) {
      if (x.getParent() != null) {
        if (x.getParent().getRight() == x) {
          x.getParent().setRight(null);
        } else {
          x.getParent().setLeft(null);
        }
      }
    }
  }
*/
  int cnt=0;
  public Node deleteNode(Node z) {
    Node y;
    if (z.getRight() == null || z.getLeft() == null) {
      y = z;
    } else {
      cnt ++;
      y = cnt%2==0 ? successor(z) : predecessor(z);
    }

    Node x;
    if (y.getLeft() != null) {
      x = y.getLeft();
    } else {
      x = y.getRight();
    }

    if (x!=null) {
      x.setParent(y.getParent());
    }

    if (y.getParent() == null) {
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
      // y.p <- z.p
      // y.left <- z.left
      // y.right <- z.right
      // z.left.p <- y
      // z.right.p <- y
      // if z.p != nil
      //   if z.p.left == z
      //     z.p.left <- y
      //   else
      //     z.p.right <- y
      //
      // return z
    }

    return y;
  }


  public void print() {
    List<Node> nodes = getListInorderWalk();
    
    List<Integer> depths = new ArrayList<Integer>();
    int maxDepth = 0;
    for (Node n : nodes) {
      int h = getDepth(n,0);
      maxDepth = Math.max(maxDepth, h);
      depths.add(h);
    }

    for (int i=0 ; i<=maxDepth ; i++)  {
      for (int j=0 ; j<nodes.size() ; j++) {

        if (i==depths.get(j)) {
          long l = nodes.get(j).getKey();
          //System.out.printf("%5d"+(l < 0 ? "-" : " "),(int)Math.ceil(Math.log(Math.abs(l))));
          System.out.printf("%5d",l/(1000L*1000L*1000L*1000L*1000L));
        } else {
          System.out.print("      ");
        }
      }
      System.out.println("");
    }
  }


  public static BinaryTree create(Long ... keys) {
    BinaryTree result = new BinaryTree();
    for (Long i: keys) {
      result.insert(new Node(i));
    }
    return result;
  }

  public static void main(String[] args) {
    //BinaryTree<Long> tree=new BinaryTree<Long>(new Node<Long>(10L));
    //tree.printer();
    /*
    BinaryTree tree=new BinaryTree();
    for (Long i : new Long[]{5L, 3L, 7L, 2L, 5L, 8L}) {
      tree.insert(new Node(i));
    }
    */
    /*
    BinaryTree tree = BinaryTree.create(5L, 3L, 7L, 2L, 5L, 8L);
    tree.inorderWalk();
    System.out.println(tree.search(8L).getParent().getKey());
    System.out.println(tree.getHeight());
    System.out.println(tree.minimum(tree.getRoot()).getKey());
    System.out.println(tree.minimumRec(tree.getRoot()).getKey());
    System.out.println(tree.maximum(tree.getRoot()).getKey());
    System.out.println(tree.maximumRec(tree.getRoot()).getKey());
    System.out.println("---");
*/
/*
    BinaryTree tree = BinaryTree.create(15L,6L,18L,3L,7L,17L,20L,2L,4L,13L,9L);
    tree.inorderWalk();
    System.out.println(tree.search(3L).getKey());
    tree.deleteNode(tree.search(3L));
    tree.inorderWalk();
    */

    BinaryTree tree = new BinaryTree();
    //long seed = 1828191884974591589L; // new Random().nextLong();
    long seed = new Random().nextLong();
    Random r = new Random(seed);
    List<Long> keys = new ArrayList<Long>();
    for (int i=0 ; i<100000 ; i++) {
      Long l = r.nextLong();
      for (int j=0 ; j<keys.size() ; j++) {
        if (l.equals(keys.get(j))) {
          continue;
        }
      }
      keys.add(l);
      tree.insert(new Node(l));
    }

    List<Long> tmpList = new ArrayList<Long>(keys);
    Collections.sort(tmpList);
    //tree.inorderWalk();
    //
    long time = 0;
    for (int i=0 ; i<1000 ; i++) {
      int idx = r.nextInt(keys.size());
      Node n = tree.search(keys.get(idx));


      long start = System.nanoTime();
      //long start = System.currentTimeMillis();
      tree.deleteNode(n);
      time += System.nanoTime()-start;
      //time += System.currentTimeMillis()-start;
      keys.remove(idx);
    }
    System.out.println("nano sec: "+time);
/*
 * successor
 * nano sec: 1036000
 * nano sec: 915000
 * nano sec: 985000
 * nano sec: 1023000
 * nano sec: 928000
 * nano sec: 930000
 * nano sec: 1007000
 * nano sec: 920000
 * nano sec: 978000
 * nano sec: 1059000
 *
 * successor or predeccesor
 * nano sec: 980000
 * nano sec: 939000
 * nano sec: 921000
 * nano sec: 977000
 * nano sec: 963000
 * nano sec: 971000
 * nano sec: 1026000
 * nano sec: 1055000
 * nano sec: 1007000
 * nano sec: 981000
 */
    /* 
    BinaryTree tree = new BinaryTree();
    long seed = 1828191884974591589L; // new Random().nextLong();
    Random r = new Random(seed);
    List<Long> keys = new ArrayList<Long>();
    for (int i=0 ; i<12 ; i++) {
      Long l = r.nextLong();
      for (int j=0 ; j<keys.size() ; j++) {
        if (l.equals(keys.get(j))) {
          continue;
        }
      }
      keys.add(l);
      tree.insert(new Node(l));
    }

    List<Long> tmpList = new ArrayList<Long>(keys);
    Collections.sort(tmpList);
    System.out.println(tmpList);
    tree.inorderWalk();
    //
    long time = 0;
    for (int i=0 ; i<10 ; i++) {
      int idx = r.nextInt(keys.size());
      System.out.println("loop--> "+i+" "+keys.get(idx));
      Node n = tree.search(keys.get(idx));

      tree.inorderWalk();
      if (n==null) {
        System.out.println("null> "+i+" "+keys.get(idx));
        System.out.println(seed);
        System.out.println("---------------------------------------------------------");
        for (Node n : tree.getListInorderWalk()) {
          System.out.println(""+n.getKey()+" "+n.getParent());
        }
        System.out.println("---------------------------------------------------------");
      }

      long start = System.currentTimeMillis();
      tree.deleteNode(n);
      time += System.currentTimeMillis()-start;
      keys.remove(idx);
    }
    System.out.println(time);
*/

/*
    System.out.println(tree.getHeight());
    System.out.println(tree.successor(tree.search(15L)).getKey());
    System.out.println(tree.minimum(tree.getRoot()).getKey());
    System.out.println(tree.minimumRec(tree.getRoot()).getKey());
    System.out.println(tree.maximum(tree.getRoot()).getKey());
    System.out.println(tree.maximumRec(tree.getRoot()).getKey());
    System.out.println(tree.predecessor(tree.search(15L)).getKey());
*/



  }
}

