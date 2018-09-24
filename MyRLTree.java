import edu.gwu.algtest.*;
import edu.gwu.util.*;
import java.util.*;

public class MyRLTree /*implements TreeSearchAlgorithm, OrderedSearchAlgorithm*/ {

	TreeNode root;
	int maxSize = 100; // I dont know why we have this stupid useless field
	// the homework asks us to implement the function "initialize,"
	// but the tree nodes are objects,
	// which makes no sense to give a limits for the size of tree
	TreeNode maxNode;
	TreeNode minNode;
	int curSize; 
	Set<Double> label = new TreeSet<>();
/*
	public MyRLTree(TreeNode root) {
		this.root = root;
		this.root.parent = null;
		this.root.numericLabel = UniformRandom.uniform();
		this.maxNode = root;
		this.minNode = root;
	}
*/
	
	public void setPropertyExtractor(int arg0, PropertyExtractor arg1) {
	}


	public Comparable predecessor(Comparable arg0) {
		return null;
	}


	public Comparable successor(Comparable arg0) {
		return null;
	}


	public Object delete(Comparable key) {
		// TODO Auto-generated method stub
		return null;
	}


	public TreeNode getRoot() {
		return root;
	}


	public int getCurrentSize() {
		
		return curSize;
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
		return "Jinwen Zhang, implementation of RL-Tree";
	}


	public ComparableKeyValuePair maximum() {
		return new ComparableKeyValuePair(maxNode.key, maxNode.value);
	}


	public ComparableKeyValuePair minimum() {
		return new ComparableKeyValuePair(minNode.key, minNode.value);
	}


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


	public Object insert(Comparable key, Object value) {
		if (curSize == 0) {
			TreeNode newNode = new TreeNode(key, value); 
			this.root = newNode;
			curSize++;
			this.maxNode = newNode;
			this.minNode = newNode; 
			return null; 
		}
		
		
		TreeNode newNode = insert(root, key, value); // first insert node as binary tree
		curSize++;
		if (maxNode.key.compareTo(newNode.key) < 0) {
			maxNode = newNode;
		}
		
		if (minNode.key.compareTo(newNode.key) > 0) {
			minNode = newNode; 
		}
		
		this.root = rebuild();
		return null;
	}

	private TreeNode insert(TreeNode root, Comparable key, Object value) {

		if (root == null) {
			TreeNode newNode = new TreeNode(key, value);
			newNode.numericLabel = UniformRandom.uniform();
			return newNode;

		}
			
		if (root.key.compareTo(key) < 0) {
			TreeNode curNode = insert(root.right, key, value);
			root.right = curNode;
			curNode.parent = root;
		} else { // if key < root, we insert it at left
			TreeNode curNode = insert(root.left, key, value);
			root.left = curNode;
			curNode.parent = root;
		}
		return root;
	}

	private void insert(TreeNode root, TreeNode newNode) {

		TreeNode curNode = root;
/*
		if (curNode.right == null && curNode.left == null) {
			if (newNode.numericLabel > curNode.numericLabel) {
				curNode.right = newNode;
				newNode.parent = curNode;
			} else {
				curNode.left = newNode;
				newNode.parent = curNode;
			}
		}
	*/	
		while (curNode.right != null || curNode.left != null) {
			if (newNode.numericLabel > curNode.numericLabel) { // if newNode.num > curNode.num
				curNode = curNode.right;
			} else {
				curNode = curNode.left;
			}
		}

		if (newNode.numericLabel > curNode.numericLabel) {
			curNode.right = newNode;
			newNode.parent = curNode;
		} else {
			curNode.left = newNode;
			newNode.parent = curNode;
		}
		return ;
	}

	private TreeNode rebuild() {

		TreeNode[] nodes = new TreeNode[curSize]; 
		
		if (nodes.length == 1) {
			this.root = nodes[0];
			root.right = null; 
			root.left = null;
			return root; 
		}
		
		
		Deque<TreeNode> stack = new LinkedList<>();
		TreeNode curNode = root;
		int index = 0; 
		while (curNode != null || !stack.isEmpty()) {
			if (curNode != null) {
				stack.offerFirst(curNode);
				curNode = curNode.left;
			} else {
				curNode = stack.pollFirst();
				nodes[index]= curNode;
				curNode = curNode.right;
				index++; 
			}
			
		}
		
		Arrays.sort(nodes, new Comparator<TreeNode>()
			{
				@Override
				public int compare(TreeNode n1, TreeNode n2) {
					if (n1.numericLabel > n2.numericLabel) return 1;
					else if (n1.numericLabel < n2.numericLabel) return -1;
					else return 0; 
				}
			}
		);

		this.root = nodes[0]; 
		root.right = null; 
		root.left = null;
		for (int i = 1; i < curSize; i++) {
			insert(root, nodes[i]);
			
		}
		return root; 
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
		System.out.print("key: " + node.key + ", label: " + node.numericLabel + ", value : " + node.value);
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
	private static double randNum() {
		double a = Math.random(); 
	    int scale = (int) Math.pow(10, 1);
	    return (double) Math.round(a * scale) / scale;
	}
	public static void main(String[] arg) {
		MyRLTree b = new MyRLTree();
		
		
		b.insert(3, 1);
		b.insert(3, 2);
		//b.insert(1, 3);
		/*
		b.insert(3, 4);
		b.insert(6, 5);
		b.insert(2, 6);
		b.insert(4, 7);
		*/
		inOrder(b.root);
		System.out.println("root : " + b.root.key + ", label : " + b.root.numericLabel + ", value : " + b.root.value);
		printNode(b.root.right);
		System.out.println();
		printNode(b.root.left);
		//System.out.println("root.left : " + b.root.left.key);
		//System.out.println("root.right : " + b.root.right.key + ", label : " + b.root.right.numericLabel + ", value : " + b.root.right.value);
		//System.out.println("root.right : " + b.root.left.key + ", label : " + b.root.left.numericLabel + ", value : " + b.root.left.value);

		/*
		System.out.println("root.left.right : " + b.root.left.right.key);
		System.out.println("root.left.right.left : " + b.root.left.right.left.key);
		System.out.println("root.right.left : " + b.root.right.left.key);
		System.out.println("root.right.rgiht : " + b.root.right.right.key);
		 */
	}
}
