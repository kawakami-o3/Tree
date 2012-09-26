package tree;

import static tree.Color.*;

import java.util.ArrayList;
import java.util.List;

public class OrderStatisticsTree extends AbstractBinaryTree<NodeOS> {

	public static OrderStatisticsTree create(Long... keys) {
		OrderStatisticsTree result = new OrderStatisticsTree();
		for (Long i : keys) {
			result.insert(new NodeOS(i));
		}
		return result;
	}

	public static OrderStatisticsTree create(Integer... keys) {
		OrderStatisticsTree result = new OrderStatisticsTree();
		for (Integer i : keys) {
			result.insert(new NodeOS((long) i));
		}
		return result;
	}

	private NodeOS NIL;

	public OrderStatisticsTree() {
		super();
		NIL = new NodeOS(null);
		setRoot(NIL);

		NIL.setParent(NIL);
		NIL.setL(NIL);
		NIL.setR(NIL);
		NIL.setSize(0);
	}

	@Override
	public NodeOS getNil() {
		return NIL;
	}

	@Override
	public void setRoot(NodeOS node) {
		node.setParent(getNil());
		super.setRoot(node);
	}

	public void rotateRight(NodeOS x) {
		NodeOS y = x.getL();
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
			if (x == x.getParent().getL()) {
				x.getParent().setL(y);
			} else {
				x.getParent().setR(y);
			}
		}

		y.setR(x);
		x.setParent(y);
		y.setSize(x.getSize());
		x.setSize(x.getL().getSize() + x.getR().getSize() + 1);
	}

	public void rotateLeft(NodeOS x) {
		NodeOS y = x.getR();
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
			if (x == x.getParent().getL()) {
				x.getParent().setL(y);
			} else {
				x.getParent().setR(y);
			}
		}

		y.setL(x);
		x.setParent(y);
		y.setSize(x.getSize());
		x.setSize(x.getL().getSize() + x.getR().getSize() + 1);
	}

	public void insert(NodeOS z) {
		NodeOS y = getNil();
		NodeOS x = getRoot();
		while (x != getNil()) {
			y = x;
			y.setSize(y.getSize() + 1);
			if (z.getKey() < x.getKey()) {
				x = x.getL();
			} else {
				x = x.getR();
			}
		}

		z.setParent(y);
		z.setL(getNil());
		z.setR(getNil());
		z.setRed();

		if (y == getNil()) {
			setRoot(z);
		} else {
			if (z.getKey() < y.getKey()) {
				y.setL(z);
			} else {
				y.setR(z);
			}
		}

		insertFixUp(z);
	}

	public void insertFixUp(NodeOS z) {
		// while (z.getParent().isRed()) {
		while (getRoot() != z && z.getParent().isRed()) {
			if (z.getParent() == z.getParent().getParent().getL()) {
				NodeOS y = z.getParent().getParent().getR();
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
				NodeOS y = z.getParent().getParent().getL();
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

	public NodeOS delete(NodeOS z) {

		NodeOS y;
		if (z.getL() == getNil() || z.getR() == getNil()) {
			y = z;
		} else {
			y = successor(z);
		}
		resizeForDelete(y);

		NodeOS x;
		if (z.getL() != getNil()) {
			x = y.getL();
		} else {
			x = y.getR();
		}

		x.setParent(y.getParent());

		if (y.getParent() == getNil()) {
			setRoot(x);
		} else {
			if (y == y.getParent().getL()) {
				y.getParent().setL(x);
			} else {
				y.getParent().setR(x);
			}
		}

		if (y != z) {
			z.setKey(y.getKey());
		}

		if (y.getColor() == BLACK) {
			deleteFixUp(x);
		}

		return y;
	}

	public void resizeForDelete(NodeOS x) {
		if (x != getNil()) {
			x.setSize(x.getSize() - 1);
			resizeForDelete(x.getParent());
		}
	}

	public void deleteFixUp(NodeOS x) {
		while (x != getRoot() && x.isBlack()) {
			// System.out.println(x.getKey()+"-"+x.getParent().getKey());
			if (x == x.getParent().getL()) {
				NodeOS w = x.getParent().getR();
				if (w.getColor() == RED) {
					w.setColor(BLACK);
					x.getParent().setColor(RED);
					rotateLeft(x.getParent());
					w = x.getParent().getR();
				}

				if (w.getL().getColor() == BLACK
						&& w.getR().getColor() == BLACK) {
					w.setColor(RED);
					x = x.getParent();
				} else {
					if (w.getR().getColor() == BLACK) {
						w.getL().setColor(BLACK);
						w.setColor(RED);
						rotateRight(w);
						w = x.getParent().getR();
					}

					w.setColor(x.getParent().getColor());
					x.getParent().setColor(BLACK);
					w.getR().setColor(BLACK);
					rotateLeft(x.getParent());
					x = getRoot();
				}
			} else {
				NodeOS w = x.getParent().getL();
				if (w.getColor() == RED) {
					w.setColor(BLACK);
					x.getParent().setColor(RED);
					rotateRight(x.getParent());
					w = x.getParent().getL();
				}

				if (w.getR().getColor() == BLACK
						&& w.getL().getColor() == BLACK) {
					w.setColor(RED);
					x = x.getParent();
				} else {
					if (w.getL().getColor() == BLACK) {
						w.getR().setColor(BLACK);
						w.setColor(RED);
						rotateLeft(w);
						w = x.getParent().getL();
					}

					w.setColor(x.getParent().getColor());
					x.getParent().setColor(BLACK);
					w.getL().setColor(BLACK);
					rotateRight(x.getParent());
					x = getRoot();
				}
			}
		}

		x.setColor(BLACK);
	}

	public NodeOS select(NodeOS x, int i) {
		int r = x.getL().getSize() + 1;
		if (i == r || x == getNil()) {
			return x;
		} else if (i < r) {
			return select(x.getL(), i);
		} else {
			return select(x.getR(), i - r);
		}
	}

	public NodeOS selectDesc(NodeOS x, int i) {
		int r = x.getR().getSize() + 1;
		if (i == r || x == getNil()) {
			return x;
		} else if (i < r) {
			return selectDesc(x.getR(), i);
		} else {
			return selectDesc(x.getL(), i - r);
		}
	}

	public int getRank(NodeOS x) {
		int r = x.getL().getSize() + 1;
		NodeOS y = x;
		while (y != getRoot()) {
			if (y == y.getParent().getR()) {
				r = r + y.getParent().getL().getSize() + 1;
			}
			y = y.getParent();
		}
		return r;
	}

	public int getRankDesc(NodeOS x) {
		int r = x.getR().getSize() + 1;
		NodeOS y = x;
		while (y != getRoot()) {
			if (y == y.getParent().getL()) {
				r = r + y.getParent().getR().getSize() + 1;
			}
			y = y.getParent();
		}
		return r;
	}

	public int size() {
		return getRoot().getSize();
	}

	public void print() {
		List<NodeOS> nodes = getListInorderWalk();

		List<Integer> depths = new ArrayList<Integer>();
		int maxDepth = 0;
		for (NodeOS n : nodes) {
			int h = getDepth(n, 0);
			maxDepth = Math.max(maxDepth, h);
			depths.add(h);
		}

		for (int i = 0; i <= maxDepth; i++) {
			for (int j = 0; j < nodes.size(); j++) {

				if (i == depths.get(j)) {
					NodeOS n = nodes.get(j);
					System.out.printf("%5d[" + n.getColor().getShortName()
							+ ",%3d]", n.getKey(), n.getSize());
					// System.out.printf("%5d",nodes.get(j).getKey());
				} else {
					System.out.print("     " + "      ");
				}
			}
			System.out.println("");
		}
	}

	public void printOrder() {
		List<NodeOS> nodes = getListInorderWalk();

		for (int j = 0; j < nodes.size(); j++) {
			NodeOS n = nodes.get(j);
			System.out.printf("%3d[%2d,%2d]", n.getKey(), getRank(n),
					getRankDesc(n));
		}
		System.out.println("");
	}

	public static void main(String[] args) {
		OrderStatisticsTree tree = OrderStatisticsTree.create(26, 17, 41, 14,
				21, 30, 47, 10, 16, 19, 23, 28, 38, 7, 12, 15, 20, 35, 39, 3);
		System.out.println("----------------------------------------");
		tree.print();
		System.out.println("----------------------------------------");
		tree.delete(tree.search(12L));
		tree.print();
		System.out.println("----------------------------------------");
		tree.printOrder();

		for (int i = 1; i <= tree.size(); i++) {
			System.out
					.print("" + tree.select(tree.getRoot(), i).getKey() + " ");
		}
		System.out.println("");
		for (int i = 1; i <= tree.size(); i++) {
			System.out.print("" + tree.selectDesc(tree.getRoot(), i).getKey()
					+ " ");
		}
		System.out.println("");

	}
}
