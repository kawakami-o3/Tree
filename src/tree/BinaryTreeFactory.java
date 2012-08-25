package tree;

public class BinaryTreeFactory {
  public static BinaryTree createTest() {
    BinaryTree tree = new BinaryTree();

    for (Long i : new Long[]{5L, 3L, 7L, 2L, 5L, 8L}) {
      tree.insert(new Node(i));
    }

    return tree;
  }
}
