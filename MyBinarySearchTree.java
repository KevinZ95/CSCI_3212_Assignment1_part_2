import edu.gwu.algtest.*;
import edu.gwu.util.*;
import java.util.*;



public class MyBinarySearchTree implements TreeSearchAlgorithm, OrderedSearchAlgorithm {

	
	TreeNode root;
	int maxSize = 100; 	// Default value is 100 nodes
	
	// constructor 
	public MyBinarySearchTree(TreeNode root) {
		this.root = root; 
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
		if (root == null) return 0; 
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
		return "Jinwen Zhang, implementation of BinarySearchTree";
	}

	@Override
	public void setPropertyExtractor(int arg0, PropertyExtractor arg1) {
		return;
	}

	@Override
	public Object delete(Comparable key) {
		root = delete(root, key); 
		return null;
	}
	
	private TreeNode delete(TreeNode root, Comparable key) {
		if (root == null) return null; 
		
		if (root.key.compareTo(key) > 0) {			// if root.key > target, go left
			root.left = delete(root.left, key);
		} else if (root.key.compareTo(key) < 0) {	// is root.key < target, go right
			root.right = delete(root.right, key);
		} else {									// else, we have found the node
			if (root.left == null) {
				return root.left;
			} else if (root.right == null) {
				return root.right;
			}
			
			root.key = successor(root.right);
			root.right = delete(root.right, root.key);
		}
		return root; 
	}

	@Override
	public Object insert(Comparable key, Object value) {
		insert(root, key, value);
		
		return null;
	}
	
	private TreeNode insert(TreeNode root, Comparable key, Object value) {
		
		if (root == null) return new TreeNode(key, value);
		
		if (root.key.compareTo(key) < 0) {
			TreeNode curNode = insert(root.right, key, value);
			root.right = curNode;
			curNode.parent = root; 
		} else {	// if key < roo t, we insert it at left
			TreeNode curNode = insert(root.left, key, value);
			root.left = curNode; 
			curNode.parent = root; 
		}
		return root; 
	}
	

	@Override
	public ComparableKeyValuePair maximum() {
		TreeNode curNode = root; 
		
		while (curNode.right != null) {
			curNode = curNode.right; 
		}
		
		return new ComparableKeyValuePair(curNode.key, curNode.value);
	}

	@Override
	public ComparableKeyValuePair minimum() {
		TreeNode curNode = root; 
		
		while (curNode.left != null) {
			curNode = curNode.left; 
		}
		
		return new ComparableKeyValuePair(curNode.key, curNode.value);
	}



	@Override
	public ComparableKeyValuePair search(Comparable key) {
		TreeNode result = search(root, key);
		
		if (result.key.compareTo(key) == 0) {
			return new ComparableKeyValuePair(result.key, result.value);
		}
		return null;
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

	@Override
	public Comparable successor(Comparable key) {
		TreeNode curNode = search(root, key);
		return successor(curNode);
	}

	private Comparable successor(TreeNode root) {
		Comparable min = root.key;
		
		while (root.left != null) {
			min = root.left.key;
			root = root.left; 
		}
		return min; 
	}
	@Override
	public Comparable predecessor(Comparable key) {
		TreeNode curNode = search(root, key);
		return predecessor(curNode);
	}

	private Comparable predecessor(TreeNode root) {
		Comparable max = root.key;
		
		while (root.right != null) {
			max = root.right.key;
			root = root.right;
		}
		return max; 
	}
	
	
	private static void inOrder(TreeNode root) {
		if (root == null) return;
		
		inOrder(root.left);
		System.out.print(root.key + " ");
		inOrder(root.right);
		return; 
	}

	public static void main(String[] args) {
		TreeNode n1 = new TreeNode(3, 0);
	
		MyBinarySearchTree b = new MyBinarySearchTree(n1);
		b.insert(5, 0); 
		b.insert(1, 0); 
		b.insert(3, 0); 
		b.insert(6, 0); 
		b.insert(2, 0); 
		b.insert(4, 0); 
		

		
	}
}
