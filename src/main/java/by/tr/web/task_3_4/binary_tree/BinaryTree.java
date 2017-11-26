package by.tr.web.task_3_4.binary_tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinaryTree<E> {

	TreeNode<E> root;
	private final Comparator<? super E> comparator;

	public BinaryTree() {
		comparator = null;
	}

	public BinaryTree(Comparator<? super E> comparator) {
		this.comparator = comparator;
	}

	public void insert(E element) {
		TreeNode<E> newNode = new TreeNode<E>(element);
		if (root == null) {
			root = newNode;
		} else {
			TreeNode<E> current = root;
			TreeNode<E> parent = null;
			while (true) {
				parent = current;
				if (compare(element, current.data) < 0) {
					current = current.leftNode;
					if (current == null) {
						parent.leftNode = newNode;
						return;
					}
				} else {
					current = current.rightNode;
					if (current == null) {
						parent.rightNode = newNode;
						return;
					}
				}
			}
		}
	}

	public boolean delete(E element) {
		TreeNode<E> parent = root;
		TreeNode<E> current = root;
		boolean isLeftChild = false;
		while (!current.data.equals(element)) {
			parent = current;
			if (compare(current.data, element) > 0) {
				isLeftChild = true;
				current = current.leftNode;
			} else {
				isLeftChild = false;
				current = current.rightNode;
			}
			if (current == null) {
				return false;
			}
		}
		if (current.leftNode == null && current.rightNode == null) {
			if (current == root) {
				root = null;
			}
			if (isLeftChild == true) {
				parent.leftNode = null;
			} else {
				parent.rightNode = null;
			}
		} else if (current.rightNode == null) {
			if (current == root) {
				root = current.leftNode;
			} else if (isLeftChild) {
				parent.leftNode = current.leftNode;
			} else {
				parent.rightNode = current.leftNode;
			}
		} else if (current.leftNode == null) {
			if (current == root) {
				root = current.rightNode;
			} else if (isLeftChild) {
				parent.leftNode = current.rightNode;
			} else {
				parent.rightNode = current.rightNode;
			}
		} else if (current.leftNode != null && current.rightNode != null) {

			TreeNode<E> child = getChild(current);
			if (current == root) {
				root = child;
			} else if (isLeftChild) {
				parent.leftNode = child;
			} else {
				parent.rightNode = child;
			}
			child.leftNode = current.leftNode;
		}
		return true;
	}

	public boolean hasElement(E element) {
		TreeNode<E> current = root;
		while (current != null) {
			if (current.data.equals(element)) {
				return true;
			} else if (compare(current.data, element) > 0) {
				current = current.leftNode;
			} else {
				current = current.rightNode;
			}
		}
		return false;
	}
	
	public List<E> inOrderTraverse(TreeNode<E> node) {
		List<E> inOrder = new ArrayList<>();
		if (node != null) {
			inOrderTraverse(node.leftNode);
			inOrder.add(node.data);
			inOrderTraverse(node.rightNode);
		}
		return inOrder;
	}
	
	public List<E> preOrderTraverse(TreeNode<E> node) {
		List<E> preOrder = new ArrayList<>();
		if (node != null) {
			preOrder.add(node.data);
			preOrderTraverse(node.leftNode);
			preOrderTraverse(node.rightNode);
		}
		return preOrder;
	}
	
	public List<E> postOrderTraverse(TreeNode<E> node) {
		List<E> postOrder = new ArrayList<>();
		if (node != null) {
			postOrderTraverse(node.leftNode);
			postOrderTraverse(node.rightNode);
			postOrder.add(node.data);
		}
		return postOrder;
	}
	
	public List<E> inOrderTraverseTree() {
		return inOrderTraverse(root);
	}
	
	public List<E> preOrderTraverseTree() {
		return preOrderTraverse(root);
	}
	
	public List<E> postOrderTraverseTree() {
		return postOrderTraverse(root);
	}
	
	private TreeNode<E> getChild(TreeNode<E> parent) {
		TreeNode<E> child = null;
		TreeNode<E> childsParent = null;
		TreeNode<E> current = parent.rightNode;
		while (current != null) {
			childsParent = child;
			child = current;
			current = current.leftNode;
		}
		if (child != parent.rightNode) {
			childsParent.leftNode = child.rightNode;
			child.rightNode = parent.rightNode;
		}
		return child;
	}
	
	
	private final int compare(Object k1, Object k2) {
		int result = -1;
		if (comparator == null) {
			result = ((Comparable<? super E>) k1).compareTo((E) k2);
		} else {
			comparator.compare((E) k1, (E) k2);
		}
		return result;
	}
}
