/**
 * This class implements an AVL tree containing key->value pairs.
 * AVL trees are self-balancing binary search trees.
 * 
 * @author Eric Alexander
 * @author Revised by Niechen Chen March 2017
 *
 * @param <K>	Object type for keys
 * @param <V>	Object type for values
 */

package AVLTree;

import java.util.LinkedList;
import java.util.Queue;

import DCEL.halfedge;
import DCEL.vertex;

public class AVLTree<K extends Comparable<K>, V> {
	private AVLNode<K,V> root;
	
	/**
	 * Constructor creates an empty AVLTree.
	 */
	public AVLTree() {
		root = null;
	}
	
	/**
	 * This method inserts a given key->value pair into the tree.
	 * If key already exists, overrides old value with new value.
	 * @param key	the key to be inserted
	 * @param value	the value to be inserted
	 */
	public void insert(K key, V value) {
		root = insert(root, key, value);
	}
	
	/**
	 * This (recursive) helper method inserts a given key->value pair into a given tree.
	 * If key already exists, overrides old value with new value.
	 * @param n		the root of the tree
	 * @param key	the key to be inserted
	 * @param value	the value to be inserted
	 * @return		the root of the new tree
	 */
	private AVLNode<K,V> insert(AVLNode<K,V> n, K key, V value) {
		// Base case: if n is empty, replace it with a new node containing key->value
		if (n == null) 
			return new AVLNode<K,V>(key, value);
		// Base case: if key is already in the tree, replace its value with value
		if (n.getKey().equals(key)) {
			n.setValue(value);
			return n;
		}
		
		// Recursive cases: insert into appropriate sub-tree, balance, and return
		if (key.compareTo(n.getKey()) < 0)
			n.setLeft(insert(n.getLeft(), key, value));
		else
			n.setRight(insert(n.getRight(), key, value));
		
		n.setHeight(1 + Math.max(height(n.getLeft()), height(n.getRight())));
		return balance(n);
	}
	
	/**
	 * This method retrieves the data corresponding to a given key.
	 * If the key does not exist, returns null.
	 * @param key	the key of the data to be retrieved
	 * @return		data corresponding to key
	 */
	public V lookup(K key) {
		return lookup(root, key);
	}
	
	/**
	 * This (recursive) helper method retrieves the data corresponding to a given key from a given tree.
	 * @param n		the root of the tree
	 * @param key	the key of the data to be retrieved
	 * @return		data corresponding to key
	 */
	private V lookup(AVLNode<K,V> n, K key) {
		// Base cases
		if (n == null) return null;
		if (n.getKey().compareTo(key)==0) return n.getValue();
		
		// Recursive cases
		if (key.compareTo(n.getKey()) < 0)
			return lookup(n.getLeft(), key);
		return lookup(n.getRight(), key);
	}
	
	//Added by Niechen starts
	public AVLNode<K,V> lookupLeftClosest(K key) {
		return lookupLeftClosest(root, key);
	}
	
	public AVLNode<K,V> lookupRightClosest(K key) {
		return lookupRightClosest(root, key);
	}
	
	/**
	 * This (recursive) helper method retrieves the data corresponding to a given key from a given tree.
	 * @param n		the root of the tree
	 * @param key	the key of the data to be retrieved
	 * @return		the node has a key value just to the left of the key, if the key is smaller than all keys then return the smallest key.
	 */
	private AVLNode<K,V> lookupLeftClosest(AVLNode<K,V> n, K key) {
		// Base cases
		if (n == null) return null;
		if(n.getLeft() == null && n.getRight() == null)
			return n;
		if (n.getKey().compareTo(key)==0) return n;
		
		// Recursive cases
		if (key.compareTo(n.getKey()) < 0){
			if(n.getLeft() == null)
				return n;
			else
				return lookupLeftClosest(n.getLeft(), key);
		}			
		else{
			if(n.getLeft() == null && n.getRight() == null)
				return n;
			else{
				AVLNode<K,V> comparenode = new AVLNode<K,V>();
				if(lookupLeftClosest(n.getRight(), key).getKey().compareTo(key) > 0)
					comparenode = lookupLeftClosest(n.getLeft(), key);
				else
					comparenode =  lookupLeftClosest(n.getLeft(), key).getKey().compareTo(lookupLeftClosest(n.getRight(), key).getKey()) > 0 
						? lookupLeftClosest(n.getLeft(), key): lookupLeftClosest(n.getRight(), key);
				return n.getKey().compareTo(comparenode.getKey()) > 0 ? n : comparenode;
						
			}			
		}	
		
	}
	/**
	 * This (recursive) helper method retrieves the data corresponding to a given key from a given tree.
	 * @param n		the root of the tree
	 * @param key	the key of the data to be retrieved
	 * @return		the node has a key value just to the right of the key, if the key is smaller than all keys then return the smallest key.
	 */
	private AVLNode<K,V> lookupRightClosest(AVLNode<K,V> n, K key) {
		// Base cases
		if (n == null) return null;
		if(n.getLeft() == null && n.getRight() == null)
			return n;
		if (n.getKey().compareTo(key)==0) return n;
		
		// Recursive cases
		if (key.compareTo(n.getKey()) > 0){
			if(n.getRight() == null)
				return n;
			else
				return lookupRightClosest(n.getRight(), key);
		}			
		else{
			if(n.getLeft() == null && n.getRight() == null)
				return n;
			else{
				AVLNode<K,V> comparenode = new AVLNode<K,V>();
				if(lookupRightClosest(n.getLeft(), key).getKey().compareTo(key) < 0)
					comparenode = lookupRightClosest(n.getRight(), key);
				else
					comparenode =  lookupRightClosest(n.getLeft(), key).getKey().compareTo(lookupRightClosest(n.getRight(), key).getKey()) < 0 
						? lookupRightClosest(n.getLeft(), key): lookupRightClosest(n.getRight(), key);
				return n.getKey().compareTo(comparenode.getKey()) < 0 ? n : comparenode;
						
			}			
		}	
		
	}
	public void printTree(){
		if(root!=null){
			root.setHeight(0);
			Queue<AVLNode<K,V>> queue = new LinkedList<AVLNode<K,V>>();
	
			queue.add(root);
			while (!queue.isEmpty()) {
				AVLNode<K,V> node = queue.poll();
				System.out.println(node);
				int level = node.getHeight();
				AVLNode<K,V> left = node.getLeft();
				AVLNode<K,V> right = node.getRight();
				if (left != null) {
					left.setHeight(level + 1);
					queue.add(left);
				}
				if (right != null) {
					right.setHeight(level + 1);
					queue.add(right);
				}
			}
		}		
	}
	//Get the root
	public AVLNode<K, V> getRoot(){
		return root;
	}
	//Added by Niechen ends
	/**
	 * This method deletes the node corresponding to a given key.
	 * If the key does not exist, does nothing.
	 * @param key	the key of the node to be deleted
	 */
	public void delete(K key) {
		root = delete(root, key);
	}
	
	/**
	 * This (recursive) helper method deletes the node corresponding to a given key from a given tree.
	 * @param n		the root of the tree
	 * @param key	the key of the node to be deleted
	 * @return		the root of the new tree
	 */
	private AVLNode<K,V> delete(AVLNode<K,V> n, K key) {
		// Base case: key doesn't exist.
		if (n == null) return null;
		// If it's in the left sub-tree, go left.
		if (key.compareTo(n.getKey()) < 0) {
			n.setLeft(delete(n.getLeft(), key));
			return balance(n); // Deleting may have unbalanced tree.
		} 
		// If it's in the right sub-tree, go right.
		else if (key.compareTo(n.getKey()) > 0) {
			n.setRight(delete(n.getRight(), key));
			return balance(n); // Deleting may have unbalanced tree.
		} 
		// Else, we found it! Remove n.
		else {
			// 0 children
			if (n.getLeft() == null && n.getRight() == null)
				return null;
			// 1 child - guaranteed to be balanced.
			if (n.getLeft() == null)
				return n.getRight();
			if (n.getRight() == null)
				return n.getLeft();
			
			// 2 children - deleting may have unbalanced tree.
			K smallestKey = smallest(n.getRight());
			n.setKey(smallestKey);
			n.setRight(delete(n.getRight(), smallestKey));
			return balance(n);
		}
	}
	
	/**
	 * This method returns the smallest key in a given tree.
	 * @param n	the root of the tree to be searched
	 * @return	the smallest key in the tree rooted at n
	 */
	private K smallest(AVLNode<K,V> n) {
		if (n.getLeft() == null)
			return n.getKey();
		return smallest(n.getLeft());
	}
	
	/**
	 * This method ensures that a tree rooted at a given node maintains the AVL tree property.
	 * That is to say, the heights of the two sub-trees differ by at most 1.
	 * @param n	the root of the tree to be balanced
	 * @return	the root of the new balanced tree
	 */
	private AVLNode<K,V> balance(AVLNode<K,V> n) {
		AVLNode<K,V> L = n.getLeft();
		AVLNode<K,V> R = n.getRight();
		
		// Case L:
		if (height(L) > height(R) + 1) {
			// Case LR - perform left rotation at L:
			if (height(L.getLeft()) < height(L.getRight())) {
				n.setLeft(L.getRight());
				L.setRight(L.getRight().getLeft());
				n.getLeft().setLeft(L);
			}
			// Case LL and LR - perform right rotation at n:
			AVLNode<K,V> newRoot = n.getLeft();
			n.setLeft(newRoot.getRight());
			newRoot.setRight(n);
			
			// Update heights as needed
			L = newRoot.getLeft();
			R = newRoot.getRight();
			L.setHeight(1 + Math.max(height(L.getLeft()), height(L.getRight())));
			R.setHeight(1 + Math.max(height(R.getLeft()), height(R.getRight())));
			newRoot.setHeight(1 + Math.max(height(L), height(R)));
			
			return newRoot;
		}
		
		// Case R:
		else if (height(R) > height(L) + 1) {
			// Case RL - perform right rotation at R:
			if (height(R.getLeft()) > height(R.getRight())) {
				n.setRight(R.getLeft());
				R.setLeft(R.getLeft().getRight());
				n.getRight().setRight(R);
			}
			// Case RL and RR - perform left rotation at n:
			AVLNode<K,V> newRoot = n.getRight();
			n.setRight(newRoot.getLeft());
			newRoot.setLeft(n);
			
			// Update heights as needed
			L = newRoot.getLeft();
			R = newRoot.getRight();
			L.setHeight(1 + Math.max(height(L.getLeft()), height(L.getRight())));
			R.setHeight(1 + Math.max(height(R.getLeft()), height(R.getRight())));
			newRoot.setHeight(1 + Math.max(height(L), height(R)));
			
			return newRoot;
		}
		
		// If it's balanced, just update height
		n.setHeight(1 + Math.max(height(n.getLeft()), height(n.getRight())));
		return n;
	}
	
	/**
	 * This method will get the height of a given node.
	 * If the node is null, returns a height of -1.
	 * @param n	an AVLNode
	 * @return	the node's height (-1 if null)
	 */
	private int height(AVLNode<K,V> n) {
		return (n == null ? -1 : n.getHeight());
	}
	
	/**
	 * A string representation of the tree's elements obtained with an in-order traversal.
	 */
	public String toString() {
		return "[" + inOrder(root) + " ]";
	}
	
	/**
	 * Performs an in-order traversal of the tree (recursively), returning the items as strings.
	 * @param n	the root of the tree
	 * @return	an in-order String representation of the tree
	 */
	private String inOrder(AVLNode<K,V> n) {
		if (n == null) return "";
		String returnString = inOrder(n.getLeft());
		returnString += " { " + n.getKey().toString() + " -> " + n.getValue().toString() + " }";
		return returnString + inOrder(n.getRight());
	}
	
	/**
	 * Returns a string representing tree in its full form, with empty nodes represented as "{ null }".
	 * Obtained using a level-order traversal.
	 * @return	string representation of tree
	 */
	public String levelOrder() {
		LinkedList<AVLNode<K,V>> myQueue = new LinkedList<AVLNode<K,V>>();
		
		myQueue.offer(root);
		
		String returnString = "";
		AVLNode<K,V> temp;
		int count = 0;
		int level = 0;
		
		while (level <= height(root)) {
			temp = myQueue.poll();
			if (temp == null) {
				returnString += " { null } ";
				myQueue.offer(null);
				myQueue.offer(null);
			}
			else {
				returnString += " { " + temp.getKey().toString() + " -> " + temp.getValue().toString() + " }";
				myQueue.offer(temp.getLeft());
				myQueue.offer(temp.getRight());
			}
			count++;
			if (count >= Math.pow(2, level)) {
				returnString += "\n";
				level++;
				count = 0;
			}
		}
		
		return returnString;
	}
}