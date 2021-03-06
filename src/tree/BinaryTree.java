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
    return Math.max(getHeight(x.getL(),height+1),getHeight(x.getR(),height+1));
  }

  public void inorderMap(Block<Node> block) {
    inorderMap(root, block);
  }

  public void inorderMap(Node x, Block<Node> block) {
    if (x!=null) {
      inorderMap(x.getL(),block);
      block.execute(x);
      inorderMap(x.getR(),block);
    }
  }

  public List<Node> getListInorderWalk() {
    List<Node> result = new ArrayList<Node>();
    inorderWalk(getRoot(), result);
    return result;
  }

  public void inorderWalk(Node x, List<Node> list) {
    if (x != null) {
      inorderWalk(x.getL(), list);
      list.add(x);
      inorderWalk(x.getR(), list);
    }
  }


  public void inorderWalk() {
    inorderWalk(root);
    System.out.println("");
  }

  private void inorderWalk(Node x) {
    if (x!=null) {
      inorderWalk(x.getL());
      System.out.print(", "+x.getKey());
      inorderWalk(x.getR());
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
      return search(x.getL(),k);
    } else {
      return search(x.getR(),k);
    }
  }

  public Node searchIterative(Long k) {
    return searchIterative(k);
  }

  public Node searchIterative(Node x, Long k) {
    while (x != null && (! k.equals(x.getKey()))) {
      if (k < x.getKey()) {
        x = x.getL();
      } else {
        x = x.getR();
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
        x = x.getL();
      } else {
        x = x.getR();
      }
    }

    z.setParent(y);

    if (y==null) {
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

  public Node minimum(Node x) {
    while (x.getL() != null) {
      x = x.getL();
    }
    return x;
  }

  public Node maximum(Node x) {
    while (x.getR() != null) {
      x = x.getR();
    }
    return x;
  }

  public Node minimumRec(Node x) {
    return x.getL()==null ? x : minimumRec(x.getL());
  }

  public Node maximumRec(Node x) {
    return x.getR()==null ? x : maximumRec(x.getR());
  }

  public Node successor(Node x) {
    if (x.getR() != null) {
      return minimum(x.getR());
    }
    Node y = x.getParent();
    while (y!=null && x == y.getR()) {
      x = y;
      y = y.getParent();
    }
    return y;
  }

  public Node predecessor(Node x) {
    if (x.getL() != null) {
      return maximum(x.getL());
    }
    Node y = x.getParent();
    while (y!=null && x == y.getL()) {
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
      if (t.getL() == null) {
        x.setParent(t);
        t.setL(x);
      } else {
        insertRecPartial(t.getL(),x);
      }
    } else {
      if (t.getR() == null) {
        x.setParent(t);
        t.setR(x);
      } else {
        insertRecPartial(t.getR(),x);
      }
    }
  }
/*
  public void deleteNode(Node x) {
    if (x.getR() == null && x.getL() == null) {
      if (x.getParent() != null) {
        if (x.getParent().getR() == x) {
          x.getParent().setR(null);
        } else {
          x.getParent().setL(null);
        }
      }
    }
  }
*/
  int cnt=0;
  public Node deleteNode(Node z) {
    Node y;
    if (z.getR() == null || z.getL() == null) {
      y = z;
    } else {
      cnt ++;
      y = cnt%2==0 ? successor(z) : predecessor(z);
    }

    Node x;
    if (y.getL() != null) {
      x = y.getL();
    } else {
      x = y.getR();
    }

    if (x!=null) {
      x.setParent(y.getParent());
    }

    if (y.getParent() == null) {
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

