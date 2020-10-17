import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TrevorBrown {
    public static void main(String[] args) {
        BST<Integer> t = new BST<>();

        t.insert(8);
        t.insert(3);
        t.insert(1);
        t.insert(6);
        t.insert(4);
        t.insert(7);

        // 1 5 6
        System.out.println(t);

        System.out.println("Found: " + t.search(9));
    }
}

abstract class SplayTree<T extends Comparable<T>> {
    /**
     * 
     * @param root   The current node in the iteration
     * @param target the node we are searching for
     * @return an array of nodes where the first element is the predecessor.
     */
    protected List<BTNode<T>> getUniquePath(BTNode<T> root, BTNode<T> target) {
        if (root == null)
            return null;

        List<BTNode<T>> path = new ArrayList<>();
        path.add(null);
        getUniquePath(root, target, path);
        // path.add(path.get(0));
        return path;
    }

    protected void getUniquePath(BTNode<T> root, BTNode<T> target, List<BTNode<T>> path) {
        if (root == null)
            return;
        if (root.equals(target)) {
            path.set(0, root);
            return;
        }

        path.add(root);
        path.set(0, root);

        if (root.compareTo(target) > 0)
            getUniquePath(root.getLeft(), target, path);
        else {
            if (root.getRight() != null)
                path.set(0, root.getRight());
            getUniquePath(root.getRight(), target, path);
        }

    }

    protected abstract void splay(BTNode<T> node, T data);

}

interface Dictionary<T extends Comparable<T>> {
    private void insert(BTNode<T> target, BTNode<T> newNode) {
    }

    public void insert(BTNode<T> newNode);

    public T search(T target);

    public T delete(BTNode<T> target);
}

class BST<T extends Comparable<T>> extends SplayTree<T> implements Dictionary<T> {
    private BTNode<T> root;

    // #region CONSTRUCTORS
    public BST(T data) {
        root = new BTNode<T>(data);
    }

    public BST() {
        root = null;
    }
    // #endregion

    protected void splay(BTNode<T> node, T key) {

    }

    // #region INSERT
    public void insert(T data) {
        try {
            root = insert(root, new BTNode<T>(data));
        } catch (Exception e) {
            System.out.println("Duplicate Keys not allowed!");
            return;
        }
        System.out.println("Key successfully inserted!");
    }

    private BTNode<T> insert(BTNode<T> target, BTNode<T> newNode) throws Exception {
        if (target == null) {
            return newNode;
        }
        if (target.compareTo(newNode) > 0) {
            target.setLeft(insert(target.getLeft(), newNode));
        } else if (target.compareTo(newNode) < 0) {
            target.setRight(insert(target.getRight(), newNode));
        } else {
            throw new Exception("Duplicate Keys not allowed!");
        }

        return target;
    }

    // #endregion
    // #region SEARCH
    public T search(T target) {
        return (root == null) ? null : search(new BTNode<T>(target));
    }

    private T search(BTNode<T> target) {
        ArrayList<BTNode<T>> l = (ArrayList<BTNode<T>>) getUniquePath(root, target);
        BTNode<T> predecessor = l.remove(0);

        if (predecessor == null)
            return null;

        if (!predecessor.equals(target)) {
            System.out.println("Key not found!");
        }

        // l[0] is predecessor but target is not in tree.
        // splay at each step
        // splay at predecessor
        System.out.println(l);
        return predecessor.getData();
    }
    // #endregion

    public T delete(BTNode<T> target) {

        // If not found
        return null;
    }

    // #region UTILS
    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(sb, root);
        return sb.toString();
    }

    private void inOrder(StringBuilder sb, BTNode<T> root) {

        if (root == null)
            return;

        inOrder(sb, root.getLeft());
        sb.append(root.toString() + " ");
        inOrder(sb, root.getRight());

    }

    @Override
    public String toString() {
        return inOrder();
    }
    // #endregion

}

class BTNode<T extends Comparable<T>> implements Comparable<BTNode<T>> {

    private T data;
    private BTNode<T> left;
    private BTNode<T> right;

    public BTNode(T data) {
        this.data = data;
    }

    // #region Getters and Setters
    public T getData() {
        return data;
    }

    public BTNode<T> getLeft() {
        return left;
    }

    public BTNode<T> getRight() {
        return right;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setLeft(BTNode<T> left) {
        this.left = left;
    }

    public void setRight(BTNode<T> right) {
        this.right = right;
    }

    public int compareTo(BTNode<T> other) {
        return data.compareTo(other.data);
    }

    public boolean equals(BTNode<T> other) {
        return data.equals(other.data);
    }

    // #endregion
    public String toString() {
        return data.toString();
    }

}