package BTree;

import com.mj.BinarySearchTree.Visitor;
import com.mj.printer.BinaryTrees;

public class Main
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		BinarySearchTree_Hww<Integer> bTree_Hww = new BinarySearchTree_Hww<Integer>(null);
		int[] a = { 9, 8, 3, 5, 10, 90, 58, 2, 89, 13, 45, 25, 6, 99, 100, 200, 300 };
		for (int i = 0; i < a.length; i++)
		{
			bTree_Hww.add(a[i]);
		}
		BinaryTrees.println(bTree_Hww);
		bTree_Hww.inorderByIteration(new Visitor<Integer>()
		{
			public void visit(Integer element)
			{
				System.out.print("_" + element + "_ ");
			}
		});
		// bTree_Hww.preorder(new Visitor<Integer>()
		// {
		// public void visit(Integer element)
		// {
		// System.out.print("_" + element + "_ ");
		// }
		// });
	}

}
