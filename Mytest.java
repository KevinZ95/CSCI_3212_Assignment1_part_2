import edu.gwu.algtest.*;
import edu.gwu.util.*;
import java.util.*;


public class Mytest {
	
	public static void inOrder(TreeNode root) {
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
