/**
 * This class represents nodes in an AVL tree.
 * AVL tree is implemented in AVLTree.java.
 * 
 * @author Eric Alexander
 *
 * @param <K>	Object type for keys
 * @param <V>	Object type for values
 */
package AVLTree;

public class AVLNode<K,V> {
	private K key;
	private V value;
	private AVLNode<K,V> left;
	private AVLNode<K,V> right;
	private int height;
	
	//Added by Niechen
	public AVLNode() {
		key = null;
		value = null;
		left = null;
		right = null;
		height = 0;
	}
	
	public AVLNode(K k, V v) {
		key = k;
		value = v;
		left = null;
		right = null;
		height = 0;
	}
	
	public void setKey(K k) {
		key = k;
	}
	
	public K getKey() {
		return key;
	}
	
	public void setValue(V v) {
		value = v;
	}
	
	public V getValue() {
		return value;
	}
	
	public void setLeft(AVLNode<K,V> n) {
		left = n;
	}
	
	public AVLNode<K,V> getLeft() {
		return left;
	}
	
	public void setRight(AVLNode<K,V> n) {
		right = n;
	}
	
	public AVLNode<K,V> getRight() {
		return right;
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public int getHeight() {
		return height;
	}
	
	//Added by Niechen for pint out tree
	@Override
	public String toString() {
	    if (this.getLeft() != null && this.getRight() != null) {
	        return "Level " + height + ": " + value + " || left " + this.getLeft().key + " | right " + this.getRight().key;
	    } else if (this.getLeft() != null && this.getRight() == null) {
	        return "Level " + height + ": " + value + " || left " + this.getLeft().key;
	    } else if (this.getLeft() == null && this.getRight() != null) {
	        return "Level " + height + ": " + value + " || right " + this.getRight().key;
	    }

	    return "Level " + height + ": " + value;      
	}
}