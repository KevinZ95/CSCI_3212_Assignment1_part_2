import edu.gwu.algtest.*;
import edu.gwu.util.*;
import java.util.*;

public class MyRLTree implements TreeSearchAlgorithm, OrderedSearchAlgorithm {

	TreeNode root;
	int maxSize = 100; // I dont know why we have this stupid useless field
	// the homework asks us to implement the function "initialize,"
	// but the tree nodes are objects,
	// which makes no sense to give a limits for the size of tree
	TreeNode maxNode;
	TreeNode minNode;
	Set<Double> label = new TreeSet<>();

	public MyRLTree(TreeNode root) {
		this.root = root;
		this.root.parent = null;
		this.root.numericLabel = UniformRandom.uniform();
		this.maxNode = root;
		this.minNode = root;
	}

	@Override
	public void setPropertyExtractor(int arg0, PropertyExtractor arg1) {
	}

	@Override
	public Comparable predecessor(Comparable arg0) {
		return null;
	}

	@Override
	public Comparable successor(Comparable arg0) {
		return null;
	}

	@Override
	public Object delete(Comparable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeNode getRoot() {
		return root;
	}

	@Override
	public int getCurrentSize() {
		return getCurrentSize(root);
	}

	private int getCurrentSize(TreeNode root) {
		if (root == null)
			return 0;
		return (getCurrentSize(root.left) + 1) + (getCurrentSize(root.right) + 1) + 1;
	}

	@Override
	public Enumeration getKeys() {
		Enumeration<Comparable> keys;
		Vector<Comparable> keyNames = new Vector<>();

		Deque<TreeNode> stack = new LinkedList<>();
		TreeNode curNode = root;

		while (curNode != null || !stack.isEmpty()) {
			if (curNode != null) {
				stack.offerFirst(curNode);
				curNode = curNode.left;
			} else {
				curNode = stack.pollFirst();
				keyNames.add(curNode.key);
				curNode = curNode.right;
			}
		}

		keys = keyNames.elements();
		return keys;
	}

	@Override
	public Enumeration getValues() {
		Enumeration<Object> values;
		Vector<Object> valueNames = new Vector<>();

		Deque<TreeNode> stack = new LinkedList<>();
		TreeNode curNode = root;

		while (curNode != null || !stack.isEmpty()) {
			if (curNode != null) {
				stack.offerFirst(curNode);
				curNode = curNode.left;
			} else {
				curNode = stack.pollFirst();
				valueNames.add(curNode.value);
				curNode = curNode.right;
			}
		}

		values = valueNames.elements();
		return values;
	}

	@Override
	public void initialize(int size) {
		this.maxSize = size;

	}

	@Override
	public String getName() {
		return "Jinwen Zhang, implementation of RL-Tree";
	}

	@Override
	public ComparableKeyValuePair maximum() {
		return new ComparableKeyValuePair(maxNode.key, maxNode.value);
	}

	@Override
	public ComparableKeyValuePair minimum() {
		return new ComparableKeyValuePair(minNode.key, minNode.value);
	}

	@Override
	public ComparableKeyValuePair search(Comparable key) {
		Deque<TreeNode> stack = new LinkedList<>();
		TreeNode curNode = root;

		while (curNode != null || !stack.isEmpty()) {
			if (curNode != null) {
				stack.offerFirst(curNode);
				curNode = curNode.left;
			} else {
				curNode = stack.pollFirst();
				if (curNode.key.compareTo(key) == 0) {
					return new ComparableKeyValuePair(curNode.key, curNode.value);
				}
				curNode = curNode.right;
			}
		}
		return null;
	}

	@Override
	public Object insert(Comparable key, Object value) {
		TreeNode newNode = insert(root, key, value); // first insert node as binary tree
		this.root = rebuild();
		return null;
	}

	private TreeNode insert(TreeNode root, Comparable key, Object value) {

		if (root == null)
			return new TreeNode(key, value);

		if (root.key.compareTo(key) < 0) {
			TreeNode curNode = insert(root.right, key, value);
			root.right = curNode;
			curNode.parent = root;
		} else { // if key < roo t, we insert it at left
			TreeNode curNode = insert(root.left, key, value);
			root.left = curNode;
			curNode.parent = root;
		}
		return root;
	}

	private TreeNode insert(TreeNode root, TreeNode newNode) {

		TreeNode curNode = root;

		while (curNode.right != null || curNode.left != null) {
			if (newNode.key.compareTo(curNode.key) > 0) { // if newNode.key > curNode.key
				curNode = curNode.right;
			} else {
				curNode = curNode.left;
			}
		}

		if (newNode.key.compareTo(curNode.key) > 0) {
			curNode.right = newNode;
			newNode.parent = curNode;
		} else {
			curNode.left = newNode;
			newNode.parent = curNode;
		}
		return newNode;
	}

	private TreeNode rebuild() {
		TreeNode[] nodes = new TreeNode[getCurrentSize()]; 
		Arrays.sort(nodes, new Comparator<TreeNode>()
			{
				@Override
				public int compare(TreeNode n1, TreeNode n2) {
					return Double.compare(n1.numericLabel, n2.numericLabel); 
				}
			}
		);
		
		this.root = nodes[0];
		for (int i = 1; i < getCurrentSize(); i++) {
			insert(root, nodes[i]);
		}
		return root; 
	}

}
