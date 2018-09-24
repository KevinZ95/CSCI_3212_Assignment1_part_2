import edu.gwu.algtest.*;
import edu.gwu.util.*;
import java.util.*;

public class MyBinarySearchTree /* implements TreeSearchAlgorithm, OrderedSearchAlgorithm */ {

	TreeNode root;
	int maxSize = 100; // Default value is 100 nodes

	// constructor
	public MyBinarySearchTree() {
	}

	public TreeNode getRoot() {
		return root;
	}

	public int getCurrentSize() {
		return getCurrentSize(root);
	}

	private int getCurrentSize(TreeNode root) {
		if (root == null)
			return 0;
		return getCurrentSize(root.left) + getCurrentSize(root.right) + 1;
	}

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

	public void initialize(int size) {
		this.maxSize = size;
	}

	public String getName() {
		return "Jinwen Zhang, implementation of BinarySearchTree";
	}

	public void setPropertyExtractor(int arg0, PropertyExtractor arg1) {
		return;
	}

	public Object delete(Comparable key) {
		TreeNode result = search(root, key);

		if (result.left == null && result.right == null) { // if the node has no child
			// if the node is not its parent's left child
			if (result.parent.left == null || result.parent.left.key.compareTo(result.key) != 0) {
				result.parent.right = null; // delete it as its parent's right child
			} else {
				result.parent.left = null; // else, delete it as its parent's left child
			}
			return null;
		} else if (result.left == null) { // if the node has no left child, only has the right child
			// if the node is not its parent's left child
			if (result.parent.left == null || result.parent.left.key.compareTo(result.key) != 0) {
				result.parent.right = result.right; // connect the node's parent to its right child
				result.right.parent = result.parent;
				result.right = null; // cout off the node from the tree
				result.parent = null;
			} else {
				result.parent.left = result.right; // connect the node's parent to its left child
				result.right.parent = result.parent;
				result.right = null; // cout off the node from the tree
				result.parent = null;
			}
			return null;
		} else if (result.right == null) { // if the node has no right child, only has the left child
			// if the node is not its parent's left child
			if (result.parent.left == null || result.parent.left.key.compareTo(result.key) != 0) {
				result.parent.right = result.left; // connect the node's parent to its left child
				result.left.parent = result.parent;
				result.left = null; // cout off the node from the tree
				result.parent = null;
			} else {
				result.parent.left = result.left; // connect the node's parent to its left child
				result.left.parent = result.parent;
				result.left = null; // cout off the node from the tree
				result.parent = null;
			}
			return null; 
		} else { // if the node has two child
			TreeNode successor = successor(result); // find the node's successor
			
			// Splice out the successor using Case 1 or 2 
			if (successor.left == null && successor.right == null) {
				if (successor.parent.left == null || successor.parent.left.key.compareTo(successor.key) != 0) {
					successor.parent.right = null; 
				} else {
					successor.parent.left = null; 
				}
			} else if (successor.left == null) { 
			
				if (successor.parent.left == null || successor.parent.left.key.compareTo(successor.key) != 0) {
					successor.parent.right = successor.right; 
					successor.right.parent = successor.parent;
					successor.right = null; 
					successor.parent = null;
				} else {
					successor.parent.left = successor.right; 
					successor.right.parent = successor.parent;
					successor.right = null;
					successor.parent = null;
				}
			} else if (successor.right == null) { 
			
				if (successor.parent.left == null || result.parent.left.key.compareTo(successor.key) != 0) {
					successor.parent.right = successor.left; 
					successor.left.parent = successor.parent;
					successor.left = null; 
					successor.parent = null;
				} else {
					successor.parent.left = successor.left; 
					successor.left.parent = successor.parent;
					successor.left = null; 
					successor.parent = null;
				}
			} else {
				// the successor suppose to have only one child, so there is no other case 
			}
			// Replace the node with the successor, setting pointers correctly
			
			result.height = successor.height;
			result.ID = successor.ID;
			result.key = successor.key;
			result.value = successor.value;
			result.numericLabel = successor.numericLabel; 

			return null;
		}
	}


	public Object insert(Comparable key, Object value) {

		TreeNode newNode = insert(root, key, value);

		if (getCurrentSize() == 0)
			this.root = newNode;

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

	public ComparableKeyValuePair maximum() {
		TreeNode curNode = root;

		while (curNode.right != null) {
			curNode = curNode.right;
		}

		return new ComparableKeyValuePair(curNode.key, curNode.value);
	}

	public ComparableKeyValuePair minimum() {
		TreeNode curNode = root;

		while (curNode.left != null) {
			curNode = curNode.left;
		}

		return new ComparableKeyValuePair(curNode.key, curNode.value);
	}

	public ComparableKeyValuePair search(Comparable key) {
		TreeNode result = search(root, key);

		if (result == null) {
			// if we have not found the node, return null
			return null;
		}

		// if we have found the node, return
		return new ComparableKeyValuePair(result.key, result.value);

	}

	private TreeNode search(TreeNode root, Comparable key) {
		if (root == null || root.key.compareTo(key) == 0) {
			return root;
		}

		if (root.key.compareTo(key) < 0) {
			return search(root.right, key);
		}

		return search(root.left, key);
	}

	public Comparable successor(Comparable key) {
		TreeNode curNode = search(root, key);
		if (curNode == null)
			return null;
		TreeNode result = successor(curNode);
		if (result == null)
			return null;
		return successor(curNode).key;
	}

	private TreeNode successor(TreeNode node) {

		if (node.right != null) { // if the node has right child
			return node.right; // return its right child
		}
		// else, when node is its parent's right child
		while (node.key.compareTo(node.parent.right.key) == 0) {

			node = node.parent; // go to its parent

			if (node.parent == null)
				return null; // this node is the max node in the tree

		}
		// when we have found the fist "parent" who is a left child
		return node.parent; // return the parent
	}

	public Comparable predecessor(Comparable key) {

		TreeNode curNode = search(root, key);
		if (curNode == null)
			return null;

		TreeNode result = predecessor(curNode);
		if (result == null)
			return null;

		return predecessor(curNode).key;
	}

	private TreeNode predecessor(TreeNode node) {
		if (node.left != null) { // if the node has left child
			return node.left; // return its left child
		}
		// else, when node is its parent's left child
		while (node.key.compareTo(node.parent.left.key) == 0) {
			node = node.parent; // go to its parent
			if (node.parent == null)
				return null; // this node is the max node in the tree
		}
		// when we have found the fist "parent" who is a right child
		return node.parent; // return the parent
	}

	private static void inOrder(TreeNode root) {
		if (root == null)
			return;

		inOrder(root.left);
		printNode(root);
		System.out.println();
		inOrder(root.right);
		return;
	}

	private static void printNode(TreeNode node) {
		if (node == null) {
			System.out.println("null");
			return;
		}
		System.out.print("key: " + node.key + ", value: " + node.value);
		return;
	}

	private static void printNode(ComparableKeyValuePair pair) {
		if (pair == null) {
			System.out.println("null");
			return;
		}
		System.out.println("(" + pair.key + ", " + pair.value + ")");
		return;
	}

	public static void main(String[] args) {

		MyBinarySearchTree b = new MyBinarySearchTree();
		b.insert(3, 1);
		b.insert(5, 2);
		b.insert(1, 3);
		b.insert(3, 4);
		b.insert(6, 5);
		b.insert(2, 6);
		b.insert(4, 7);

		inOrder(b.root);
		System.out.println("");
		System.out.println("root : " + b.root.key);
		System.out.println("root.left : " + b.root.left.key);
		System.out.println("root.right : " + b.root.right.key);
		System.out.println("root.left.right : " + b.root.left.right.key);
		System.out.println("root.left.right.left : " + b.root.left.right.left.key);
		System.out.println("root.right.left : " + b.root.right.left.key);
		System.out.println("root.right.rgiht : " + b.root.right.right.key);

		printNode(b.getRoot());
		System.out.println();
		System.out.println("curent size : " + b.getCurrentSize());
		System.out.print("search 2 : ");
		printNode(b.search(2));
		System.out.print("search 3 : ");
		printNode(b.search(3));
		System.out.print("search 5 : ");
		printNode(b.search(5));
		System.out.print("search 7 : ");
		printNode(b.search(7));
		System.out.print("get max : ");
		printNode(b.maximum());
		System.out.print("get min : ");
		printNode(b.minimum());
		System.out.println("get successor 6 : " + b.successor(6));
		System.out.println("get successor 4 : " + b.successor(4));
		System.out.println("get predecessor 4 : " + b.predecessor(4));
		System.out.println();
		b.delete(2);
		System.out.println("after delete 2 : ");
		inOrder(b.root);
		System.out.print("get root : ");
		printNode(b.getRoot());
		System.out.println();
		System.out.println("root : " + b.root.key);
		System.out.println("root.left : " + b.root.left.key);
		System.out.println("root.right : " + b.root.right.key);
		System.out.println("root.left.right : " + b.root.left.right.key);
		//System.out.println("root.left.right.left : " + b.root.left.right.left.key);
		System.out.println("root.right.left : " + b.root.right.left.key);
		System.out.println("root.right.rgiht : " + b.root.right.right.key);
	}
}
