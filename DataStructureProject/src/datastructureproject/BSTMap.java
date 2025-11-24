package pkg212projectnov2025_phase2;
class BST_node<K extends Comparable<K>, T> {
	public K key;
	public T data;
	public BST_node<K, T> left, right;

	public BST_node(K key, T data) {
		this.key = key;
		this.data = data;
		left = right = null;
	}
}
public class BSTMap<K extends Comparable<K>, T>{
	private BST_node<K, T> root, current;
	public BSTMap() {
		current = root = null;
	}
          public BST_node<K,T> getRoot()
	{
		return root;
	}
       
  
	public boolean full() {
		return false;
	}


	
	public boolean find(K key) {
            BST_node<K,T>p=root;
            while(p!=null)
            {
             if(key.compareTo(p.key)==0) //***
             {
                 current=p;
                 return true;
             }
            else if(key.compareTo(p.key)<0) 
                p=p.left;
             else
                p=p.right;
            }
		return false; 
	}

	
// Insert a new element if does not exist and return true . If k already exists , return false .
        public boolean insert(K key, T val) {
		if (root == null) {
			current = root = new BST_node<K, T>(key, val);
			return true;
                }

		BST_node<K, T> p = root;
		BST_node<K, T> q = null;
		while (p != null) {
			int res = key.compareTo(p.key);
//			if (res == 0) {
//				break;
//			} 
//                        else 
                        {
                            q = p;
				if (res < 0) {
					p = p.left;
				} else {
					p = p.right;
				}
			}
		}
		if (p != null) {
			return false;
		}

		BST_node<K, T> tmp = new BST_node<K, T>(key, val);
		if (key.compareTo(q.key) < 0) {
			q.left = tmp;
		} else {
			q.right = tmp;
		}
		current = tmp;
		return true;
	}
       // Remove the element with key k if it exists and return true . If the element does not exist
       //return false .

	public boolean remove(K k) {
		// Search for k
		K k1 = k;
		BST_node<K, T> p = root;
		BST_node<K, T> q = null; // Parent of p
		while (p != null) {
			int res = k1.compareTo(p.key);
			if (res < 0) {
				q = p;
				p = p.left;
			} else if (res > 0) {
				q = p;
				p = p.right;
			} else { // Found the key

				// Check the three cases
				if ((p.left != null) && (p.right != null)) { // Case 3: two
																// children
					// Search for the min in the right subtree
					BST_node<K, T> min = p.right;
					q = p;
					while (min.left != null) {
						q = min;
						min = min.left;
					}
					p.key = min.key;
					p.data = min.data;
					k1 = min.key;
					p = min;
					// Now fall back to either case 1 or 2
				}

				// The subtree rooted at p will change here
				if (p.left != null) { // One child
					p = p.left;
				} else { // One or no children
					p = p.right;
				}

				if (q == null) { // No parent for p, root must change
					root = p;
				} else {
					if (k1.compareTo(q.key) < 0) {
						q.left = p;
					} else {
						q.right = p;
					}
				}
				current = root;
				return true;

			}
		}

		return false; // Not found
	}        
       
}
