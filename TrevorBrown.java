import java.io.File;
import java.util.Scanner;

public class TrevorBrown {
    public static void main(String[] args) {
        Scanner scan = null;
        BST t = new BST();
        try {
            File file = new File("in.dat");
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                t.insert(scan.nextInt());
            }
        } catch (Exception e) {

        } finally {
            if (scan != null) {
                scan.close();
                scan = null;
            }
            System.out.println("Initial Tree:");
            t.printTree();

            String[] commands = { "", "" };
            Scanner input = new Scanner(System.in);
            while (!commands[0].equals("E")) {
                System.out.print("Command> ");
                commands = input.nextLine().split(" ");
                if (commands.length > 0 && commands[0].equals("E"))
                    break;
                String command = "";
                int key = Integer.MIN_VALUE;
                if (commands.length > 0) {
                    command = commands[0];
                }
                if (commands.length > 1) {
                    try {
                        key = Integer.parseInt(commands[1]);

                    } catch (Exception e) {
                        System.out.println("Please enter an integer as the second argument!");
                    }
                }
                if (key != Integer.MIN_VALUE) {
                    switch (command) {

                        case "S":
                            t.splay(key);
                            System.out.println("Splay is done");

                            break;
                        case "F":
                            t.search(key);
                            break;
                        case "I":
                            t.insert(key);
                            System.out.println("The key is inserted into the tree");

                            t.splay(key);
                            break;
                        case "D":
                            t.delete(key);
                            t.splay(key);
                            break;
                        default:
                            System.out.println("Invalid command!");
                            break;
                    }
                    t.printTree();
                }

            }
            input.close();

        }

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

    private BTNode splay(BTNode root, int key, String directive) {
        // Base cases: root is null or
        // key is present at root
        if (root == null || root.getData() == key) {

            return root;
        }

        // Key lies in left subtree
        if (root.getData() > key) {
            // Key is not in tree, we are done
            if (root.getLeft() == null)
                return root;

            // Zig-Zig (Left Left)
            if (root.getLeft().getData() > key) {
                // First recursively bring the
                // key as root of left-left
                root.getLeft().setLeft(splay(root.getLeft().getLeft(), key, directive));

                // Do first rotation for root,
                // second rotation is done after else
                root = rightRotate(root);
            } else if (root.getLeft().getData() < key) // Zig-Zag (Left Right)
            {
                // First recursively bring
                // the key as root of left-right
                root.getLeft().setRight(splay(root.getLeft().getRight(), key, directive));

                // Do first rotation for root.left
                if (root.getLeft().getRight() != null)
                    root.setLeft(leftRotate(root.getLeft()));
            }

            // Do second rotation for root
            return (root.getLeft() == null) ? root : rightRotate(root);
        } else // Key lies in right subtree
        {
            // Key is not in tree, we are done
            if (root.getRight() == null) {
                return root;

            }

            // Zag-Zig (Right Left)
            if (root.getRight().getData() > key) {
                // Bring the key as root of right-left
                root.getRight().setLeft(splay(root.getRight().getLeft(), key, directive));

                // Do first rotation for root.right
                if (root.getRight().getLeft() != null)
                    root.setRight(rightRotate(root.getRight()));
            } else if (root.getRight().getData() < key)// Zag-Zag (Right Right)
            {
                // Bring the key as root of
                // right-right and do first rotation
                root.getRight().setRight(splay(root.getRight().getRight(), key, directive));
                root = leftRotate(root);
            }

            // Do second rotation for root
            return (root.getRight() == null) ? root : leftRotate(root);
        }

    }

    public void splay(int key) {
        root = splay(root, key, "search");
    }

    public BTNode search(int key) {
        root = splay(root, key, "search");
        if (root.getData() == key) {
            System.out.println("Search is successful");
        } else {
            System.out.println("Search is unsuccessful");
        }
        return root;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private BTNode insert(BTNode root, int key) {
        if (root == null) {
            root = new BTNode(key);
            System.out.println("The key is inserted into the tree");
            return root;
            // return splay(this.root, key, "");
        }
        if (root.getData() > key) {
            root.setLeft(insert(root.getLeft(), key));
        } else if (root.getData() < key) {
            root.setRight(insert(root.getRight(), key));
        } else {
            System.out.println("No duplicate keys!");
            return root;
        }
        return root;

    }

    int smallestNode(BTNode root) {
        int min = root.getData();
        while (root.getLeft() != null) {
            min = root.getLeft().getData();
            root = root.getLeft();
        }
        return min;
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    private BTNode delete(BTNode root, int key) {
        /* Base Case: If the tree is empty */
        if (root == null) {
            System.out.println("The key is not in the tree");
            return root;
        }

        /* Otherwise, recur down the tree */
        if (key < root.getData())
            root.setLeft(delete(root.getLeft(), key));
        else if (key > root.getData())
            root.setRight(delete(root.getRight(), key));

        // if key is same as root's key, then This is the node
        // to be deleted
        else {
            System.out.println("The key is deleted from the tree");
            // node with only one child or no child
            if (root.getLeft() == null)
                return root.getRight();
            else if (root.getRight() == null)
                return root.getLeft();

            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.setData(smallestNode(root.getRight()));

            // Delete the inorder successor
            root.setRight(delete(root.getRight(), root.getData()));
        }

        return root;
    }

    public void printTree() {
        printTree(this.root, 0);
    }

    // #region UTILS
    private void printTree(BTNode root, int space) {
        int COUNT = 5;
        // Base case
        if (root == null)
            return;

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        printTree(root.getRight(), space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.getData() + "\n");

        // Process left child
        printTree(root.getLeft(), space);

    }

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