package tree;

//import java.util.Math;
import static tree.NodeRB.Color.*;
import java.util.List;
import java.util.ArrayList;

public class RedBlack extends AbstractBinaryTree<NodeRB> {
  public NodeRB NIL;
  public RedBlack() {
    super();
    NIL = new NodeRB(null);
    setRoot(NIL);

    NIL.setParent(NIL);
    NIL.setL(NIL);
    NIL.setR(NIL);
  }

  public NodeRB getNil() {
    return NIL;
  }

  public void setRoot(NodeRB node) {
    node.setParent(getNil());
    super.setRoot(node);
  }
/*
  public NodeRB getRoot() {
    return super.getRoot()==null ? NIL : super.getRoot();
  }
*/
  public void rotateRight(NodeRB x) {
    NodeRB y = x.getL();
    //System.out.println("rotateRight> "+y);
    x.setL(y.getR());

    if (y.getR() != getNil()) {
      y.getR().setParent(x);
    }

    if (y != getNil()) {
      y.setParent(x.getParent());
    }

    if (x.getParent() == getNil()) {
      setRoot(y);
    } else {
      if (x==x.getParent().getL()) {
        x.getParent().setL(y);
      } else {
        x.getParent().setR(y);
      }
    }

    y.setR(x);
    if (x != getNil()) {
      x.setParent(y);
    }
  }

  public void rotateLeft(NodeRB x) {
    NodeRB y = x.getR();
    x.setR(y.getL());

    if (y.getL() != getNil()) {
      y.getL().setParent(x);
    }

    if (y != getNil()) {
      y.setParent(x.getParent());
    }

    if (x.getParent() == getNil()) {
      setRoot(y);
    } else {
      if (x==x.getParent().getL()) {
        x.getParent().setL(y);
      } else {
        x.getParent().setR(y);
      }
    }

    y.setL(x);
    if (x != getNil()) {
      x.setParent(y);
    }
  }


  public void insert(NodeRB z) {
    NodeRB y = getNil();
    NodeRB x = getRoot();
    while (x!=getNil()) {
      y = x;
      if (z.getKey() < x.getKey()) {
        x = x.getL();
      } else {
        x = x.getR();
      }
    }

    z.setParent(y);
    z.setL(getNil());
    z.setR(getNil());
    z.setColor(RED);

    if (y==getNil()) {
      setRoot(z);
    } else {
      if (z.getKey() < y.getKey()) {
        y.setL(z);
      } else {
        y.setR(z);
      }
    }


    System.out.println("===== "+z.getKey());
    insertFixUp(z);
    print();
  }

  public void insertFixUp(NodeRB z) {
    //while (z.getParent().isRed()) {
    while (getRoot() != z && z.getParent().isRed()) {
      if (z.getParent() == z.getParent().getParent().getL()) {
        NodeRB y = z.getParent().getParent().getR();
        if (y.isRed()) {
          z.getParent().setBlack();
          y.setBlack();
          z.getParent().getParent().setRed();
          z = z.getParent().getParent();
        } else {
          if (z == z.getParent().getR()) {
          z = z.getParent();
          rotateLeft(z);
          }
          z.getParent().setBlack();
          z.getParent().getParent().setRed();
          rotateRight(z.getParent().getParent());
        }
      } else {
        NodeRB y = z.getParent().getParent().getL();
        if (y.isRed()) {
          z.getParent().setBlack();
          y.setBlack();
          z.getParent().getParent().setRed();
          z = z.getParent().getParent();
        } else {
          if (z == z.getParent().getL()) {
            z = z.getParent();
            rotateRight(z);
          }
          z.getParent().setBlack();
          z.getParent().getParent().setRed();
          rotateLeft(z.getParent().getParent());
        }
      }
    }
    getRoot().setColor(BLACK);
  }

  public NodeRB deleteNode(NodeRB z) {
    //if (z.getL()
    return null;
  }



  public static RedBlack create(Long ... keys) {
    RedBlack result = new RedBlack();
    for (Long i: keys) {
      result.insert(new NodeRB(i));
    }
    return result;
  }


  public static RedBlack create(Integer ... keys) {
    RedBlack result = new RedBlack();
    for (Integer i: keys) {
      result.insert(new NodeRB((long)i));
    }
    return result;
  }

  public void print() {
    List<NodeRB> nodes = getListInorderWalk();
    
    List<Integer> depths = new ArrayList<Integer>();
    int maxDepth = 0;
    for (NodeRB n : nodes) {
      int h = getDepth(n,0);
      maxDepth = Math.max(maxDepth, h);
      depths.add(h);
    }

    for (int i=0 ; i<=maxDepth ; i++)  {
      for (int j=0 ; j<nodes.size() ; j++) {

        if (i==depths.get(j)) {
          NodeRB n = nodes.get(j);
          System.out.printf("%5d["+n.getColor().getShortName()+"]",n.getKey());
          //System.out.printf("%5d",nodes.get(j).getKey());
        } else {
          System.out.print("     "+"   ");
        }
      }
      System.out.println("");
    }
  }



  public static void main(String[] args) {
    //RedBlack tree = RedBlack.create(15L,6L,18L,3L,7L,17L,20L,2L,4L,13L,9L);
    /*
    RedBlack tree = RedBlack.create(7, 4, 11, 3, 6, 9, 18, 2, 14, 19, 12, 17, 22, 20);
    System.out.println("-----");
    tree.print();
    System.out.println("-----");
    tree.rotateLeft(tree.search(11L));
    tree.print();
    */

    //RedBlack tree = RedBlack.create(7, 4, 11, 3, 6, 9, 18, 2, 14, 19, 12, 17, 22, 20);
    RedBlack tree = RedBlack.create(26,17,41,14,21,30,47,10,16,19,23,28,38,7,12,15,20,35,39,3);
    System.out.println("-----");
    tree.print();
    System.out.println("-----");
    tree.rotateRight(tree.search(11L));
    tree.print();
 

    /*
    List<Node> nodes = tree.getListInorderWalk();
    System.out.println(nodes);
    for (Node n : nodes) {
      System.out.printf("%5d",n.getKey());
    }
    System.out.println("");

    for (Node n : nodes) {
      System.out.printf("%5d",tree.getDepth(n,0));
    }
    System.out.println("");



    List<Integer> depths = new ArrayList<Integer>();
    int maxDepth = 0;
    for (Node n : nodes) {
      int h = tree.getDepth(n,0);
      maxDepth = Math.max(maxDepth, h);
      depths.add(h);
    }

    for (int i=0 ; i<=maxDepth ; i++)  {
      for (int j=0 ; j<nodes.size() ; j++) {

        if (i==depths.get(j)) {
          System.out.printf("%5d",nodes.get(j).getKey());
        } else {
          System.out.print("     ");
        }
      }
      System.out.println("");
    }

    System.out.println(tree.search(3L).getKey());
    tree.deleteNode(tree.search(3L));
    tree.getListInorderWalk();
    */

  }
}
