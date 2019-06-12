package BTree;

import java.util.Comparator;
import java.util.Stack;

import com.mj.BinarySearchTree.Visitor;
import com.mj.printer.BinaryTreeInfo;

//import com.mj.BinarySearchTree.Node;

public class BinarySearchTree_Hww<E> implements BinaryTreeInfo
{
	private int size;
	private Node<E> root;
	private Comparator<E> comparator;

	public BinarySearchTree_Hww(Comparator<E> comparator)
	{
		this.comparator = comparator;
	}

	public int size()
	{
		return size;//
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public void clear()
	{
		root = null;
		size = 0;
	}

	public void add(E element)
	{
		if (element == null)
			return;
		if (root == null)
		{
			root = new Node<E>(element, null);
			size++;
			return;
		}
		Node<E> parent = root;
		Node<E> node = root;
		int cmp = 0;
		while (node != null)
		{
			cmp = compare(node.element, element);
			parent = node;
			if (cmp > 0)
				node = node.left;
			else if (cmp < 0)
				node = node.right;
			else
			{
				node.element = element;
				return;
			}

		}
		// 找到底了,比较叶子节点和新节点
		Node<E> newNode = new Node<>(element, parent);
		if (cmp > 0)
			parent.left = newNode;
		else
			parent.right = newNode;
		size++;
	}

	public void remove(E element)
	{
		if (element == null)
			return;
		Node<E> node = node(element);
		if (node == null)
			return;
		if (node.hasTwoChildren())
			removeNodeWithTwoChild(node);
		else if (node.left == null && node.right == null)
			removeNodeWithoutChild(node);
		else
			removeNodeWithOneChild(node);
	}

	private void removeNodeWithoutChild(Node<E> node)
	{
		if (node.isLeaf())
			node.parent.left = null;
		else
			node.parent.right = null;
	}

	private void removeNodeWithOneChild(Node<E> node)
	{
		if (node.isLeaf())
			node.parent.left = node.left;
		else
			node.parent.right = node.right;
	}

	private void removeNodeWithTwoChild(Node<E> node)
	{

	}

	public boolean contains(E element)
	{
		return false;
	}

	private void remove(Node<E> node)
	{
	}

	private Node<E> node(E element)
	{
		Node<E> node = root;
		while (node != null)
		{
			int cmp = compare(element, node.element);
			if (cmp == 0)
				return node;
			if (cmp > 0)
				node = node.right;
			else // cmp < 0
				node = node.left;
		}
		return null;
	}

	// 前序遍历
	void preorder(Visitor<E> visitor)
	{
		if (root == null)
			return;
		preorderByRecursive(root, visitor);
	}

	private void preorderByRecursive(Node<E> node, Visitor<E> visitor)
	{
		if (node == null)
			return;
		visitor.visit(node.element);
		preorderByRecursive(node.left, visitor);
		preorderByRecursive(node.right, visitor);
	}

	void preorderByIteration(Visitor<E> visitor)
	{
		if (root == null)
			return;
		Stack<Node<E>> stack = new Stack<Node<E>>();
		Node<E> node = root;
		stack.push(root);
		// 思路：先将根节点访问，再将游标node往左走到底，一路进栈。
		while (!stack.isEmpty())
		{
			// 有左就往左走，走到空。
			if (node != null)
			{
				visitor.visit(node.element);
				stack.push(node);
				node = node.left;

			} else // 来到这里说明左子树走到底，
			{
				// 前序:根左右，前面就遍历过根，左子树也走到底了，那不需要左节点了。直接Pop,往右走
				node = stack.pop();
				node = node.right;
			}

		}
	}

	// 中序遍历
	private void postorder(Visitor<E> visitor)
	{

	}

	// 中序遍历
	void postorderByIteration(Visitor<E> visitor)
	{
		if (root == null)
			return;
		Stack<Node<E>> stack = new Stack<Node<E>>();
		Node<E> node = root;
		// 思路：先将游标node往左走到底，一路将根节点进栈。
		while (!stack.isEmpty() || node != null)
		{
			if (node != null)
			{
				stack.push(node);
				node = node.left;
			} else
			{
				// 中序:左根右，左子树也走到底了，就访问根节点，之后就不需要根节点了。直接Pop,往右走
				node = stack.pop();
				visitor.visit(node.element);
				node = node.right;
			}
		}

	}

	// 后序遍历
	private void inorder(Visitor<E> visitor)
	{

	}

	// 后序遍历
	void inorderByIteration(Visitor<E> visitor)
	{
		if (root == null)
			return;
		Stack<Node<E>> stack = new Stack<Node<E>>();
		// 返回的方向
		Stack<Boolean> statusStack = new Stack<Boolean>();
		Node<E> node = root;
		boolean nextReturnDirection = false;// 初始化为右边返回，对应根节点
 		while (!stack.isEmpty() || node != null)
		{
			// 游标没走到空，往左一直走，一路进栈
			statusStack.push(nextReturnDirection);
			stack.push(node);
			if (node != null)
			{
				node = node.left;
				nextReturnDirection = false;
				continue;
			}
			// 走到空表明一个子树遍历完毕， 弹出一个null
			stack.pop();
			// 查看是哪边的子树遍历完毕
			// 如果是右子树遍历完毕
			if (statusStack.peek())
				while (statusStack.pop())// 一个右子树遍历完毕，可能导致多级父节点遍历完，一直弹到某个左子树的根节点
					visitor.visit(stack.pop().element);// 能POP出true的代表右子树遍历完毕，访问根节点。弹出根节点
			else // 左子树遍历完毕弹出方向
				statusStack.pop();
 			// 没了
			if (stack.isEmpty())
				return;
			//走到这里，节点栈中最前面的节点是某左子树的根节点，向右走一步
			node = stack.peek().right;
			nextReturnDirection = true;
		}

	}

	private Node<E> successor(Node<E> node)
	{

		return null;
	}

	private int compare(E e1, E e2)
	{
		if (comparator != null)
		{
			return comparator.compare(e1, e2);
		}
		return ((Comparable<E>) e1).compareTo(e2);
	}

	private static class Node<E>
	{
		E element;
		Node<E> left;
		Node<E> right;
		Node<E> parent;

		public Node(E element, Node<E> parent)
		{
			this.element = element;
			this.parent = parent;
		}

		public boolean isLeaf()
		{
			return left == null && right == null;
		}

		public boolean hasTwoChildren()
		{
			return left != null && right != null;
		}
	}

	@Override
	public Object root()
	{
		return root;
	}

	@Override
	public Object left(Object node)
	{
		return ((Node<E>) node).left;
	}

	@Override
	public Object right(Object node)
	{
		return ((Node<E>) node).right;
	}

	@Override
	public Object string(Object node)
	{
		Node<E> myNode = (Node<E>) node;
		String parentString = "null";
		if (myNode.parent != null)
		{
			parentString = myNode.parent.element.toString();
		}
		return myNode.element + "_p(" + parentString + ")";
	}

}
