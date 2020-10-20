import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TrevorBrown {
    public static void main(String[] args) {
        BST t = new BST();
        t.insert(2);
        t.insert(1);
        t.insert(3);

        // we found 3 but its gone? DID IT I think!!!
        System.out.println(t.search(1));

        System.out.println(t);
    }
}

interface Dictionary {
    private void insert(BTNode target, BTNode newNode) {
    }

    public void insert(BTNode newNode);

    public int search(int target);

    public int delete(BTNode target);
}

class BST {
    private BTNode root;

    // #region CONSTRUCTORS
    public BST(int data) {
        this(new BTNode(data));
    }

    public BST(BTNode root) {
        this.root = root;
    }

    public BST() {
        root = null;
    }
    // #endregion

    private BTNode rightRotate(BTNode x) {
        BTNode y = x.getLeft();
        x.setLeft(y.getRight());
        y.setRight(x);
        return y;
    }

    private BTNode leftRotate(BTNode x) {
        BTNode y = x.getRight();
        x.setRight(y.getLeft());
        y.setLeft(x);
        return y;
    }

    private BTNode splay(BTNode root, int key) {
        // Base cases: root is null or
        // key is present at root
        if (root == null || root.getData() == key)
            return root;

        // Key lies in left subtree
        if (root.getData() > key) {
            // Key is not in tree, we are done
            if (root.getLeft() == null)
                return root;

            // Zig-Zig (Left Left)
            if (root.getLeft().getData() > key) {
                // First recursively bring the
                // key as root of left-left
                root.getLeft().setLeft(splay(root.getLeft().getLeft(), key));

                // Do first rotation for root,
                // second rotation is done after else
                root = rightRotate(root);
            } else if (root.getLeft().getData() < key) // Zig-Zag (Left Right)
            {
                // First recursively bring
                // the key as root of left-right
                root.getLeft().setRight(splay(root.getLeft().getRight(), key));

                // Do first rotation for root.left
                if (root.getLeft().getRight() != null)
                    root.setLeft(leftRotate(root.getLeft()));
            }

            // Do second rotation for root
            return (root.getLeft() == null) ? root : rightRotate(root);
        } else // Key lies in right subtree
        {
            // Key is not in tree, we are done
            if (root.getRight() == null)
                return root;

            // Zag-Zig (Right Left)
            if (root.getRight().getData() > key) {
                // Bring the key as root of right-left
                root.getRight().setLeft(splay(root.getRight().getLeft(), key));

                // Do first rotation for root.right
                if (root.getRight().getLeft() != null)
                    root.setRight(rightRotate(root.getRight()));
            } else if (root.getRight().getData() < key)// Zag-Zag (Right Right)
            {
                // Bring the key as root of
                // right-right and do first rotation
                root.getRight().setRight(splay(root.getRight().getRight(), key));
                root = leftRotate(root);
            }

            // Do second rotation for root
            return (root.getRight() == null) ? root : leftRotate(root);
        }

    }

    public BTNode search(int key) {
        root = search(root, key);
        return root;
    }

    private BTNode search(BTNode root, int key) {
        root = splay(root, key);
        return root;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private BTNode insert(BTNode root, int key) {
        if (root == null) {
            root = new BTNode(key);
            return splay(root, key);
        }
        if (root.getData() > key) {
            root.setLeft(insert(root.getLeft(), key));
        } else if (root.getData() < key) {
            root.setRight(insert(root.getRight(), key));
        } else {
            System.out.println("No duplicate keys!");
        }
        return root;

    }

    // #region UTILS
    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(sb, root);
        return sb.toString();
    }

    private void inOrder(StringBuilder sb, BTNode root) {

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

class BTNode {

    private int data;
    private BTNode left;
    private BTNode right;

    public BTNode(int data) {
        this.data = data;
    }

    // #region Getters and Setters
    public int getData() {
        return data;
    }

    public BTNode getLeft() {
        return left;
    }

    public BTNode getRight() {
        return right;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setLeft(BTNode left) {
        this.left = left;
    }

    public void setRight(BTNode right) {
        this.right = right;
    }

    public int compareTo(BTNode other) {
        return data - other.data;
    }

    public boolean equals(BTNode other) {
        return data == other.data;
    }

    // #endregion
    public String toString() {
        return data + "";
    }

}