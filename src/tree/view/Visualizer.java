package tree.view;
import tree.Node;
import tree.Block;
import tree.BinaryTree;
import tree.BinaryTreeFactory;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Visualizer {
  public static void printDot(BinaryTree tree) {
    System.out.println("graph graphname {");
    tree.inorderMap(new Block<Node>() {
      public void execute(Node node) {
        for (Node tmp : new Node[]{node.getLeft(),node.getRight()}) {
          if (tmp != null) {
            System.out.println("" + node.getKey() + " -- " + tmp.getKey() + ";");
          }
        }
      } 
    });
    System.out.println("}");
  }

/*
  public static display(BinaryTree tree) {

    JFrame frame = new JFrame("title");

    JPanel panel = new JPanel();
    JButton button = new JButton();
    panel.add(button);
    frame.add(panel);


    frame.setBounds(100, 100, 200, 160);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

  }
*/
  public static void main(String args[]) {
    BinaryTree tree = BinaryTreeFactory.createTest();


  }
}
