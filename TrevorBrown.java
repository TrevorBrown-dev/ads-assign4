public class TrevorBrown {
    public static void main(String[] args) {
        BST<Integer> t = new BST<>();
        t.insert(5);
        t.insert(6);
        t.insert(1);
        // Rejected
        t.insert(1);

        // 1 5 6
        System.out.println(t);
    }
}

interface SplayTree<T extends Comparable<T>> {
    private void splay(BTNode<T> n, T k) {
    }
}

interface Dictionary<T extends Comparable<T>> {
    private void insert(BTNode<T> target, BTNode<T> newNode) {
    }

    public void insert(BTNode<T> newNode);

    public T search(BTNode<T> target);

    public T delete(BTNode<T> target);
}

class BST<T extends Comparable<T>> implements SplayTree<T>, Dictionary<T> {
    private BTNode<T> root;

    public BST(T data) {
        root = new BTNode<T>(data);
    }

    public BST() {
        root = null;
    }

    private void splay(BTNode<T> node, T key) {

    }

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

    public T search(BTNode<T> target) {

        // If not found
        return null;
    }

    public T delete(BTNode<T> target) {

        // If not found
        return null;
    }

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

    // #endregion
    public String toString() {
        return data.toString();
    }

}