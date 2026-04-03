public class SplayTreeTask {

    static class SplayTree {

        private static class Node {
            int  value;
            Node left, right;
            Node(int v) { value = v; left = null; right = null; }
        }

        private Node root;

        private Node rotateRight(Node y) {
            Node x  = y.left;
            y.left  = x.right;
            x.right = y;
            return x;
        }

        private Node rotateLeft(Node x) {
            Node y  = x.right;
            x.right = y.left;
            y.left  = x;
            return y;
        }

        private Node splay(Node root, int key) {
            if (root == null || root.value == key)
                return root;

            if (key < root.value) {
                if (root.left == null) return root;

                // Zig-Zig (left-left)
                if (key < root.left.value) {
                    root.left.left = splay(root.left.left, key);
                    root = rotateRight(root);
                }
                // Zig-Zag (left-right)
                else if (key > root.left.value) {
                    root.left.right = splay(root.left.right, key);
                    if (root.left.right != null)
                        root.left = rotateLeft(root.left);
                }
                return (root.left == null) ? root : rotateRight(root);
            }

            else {
                if (root.right == null) return root;

                // Zig-Zag (right-left)
                if (key < root.right.value) {
                    root.right.left = splay(root.right.left, key);
                    if (root.right.left != null)
                        root.right = rotateRight(root.right);
                }
                // Zig-Zig (right-right)
                else if (key > root.right.value) {
                    root.right.right = splay(root.right.right, key);
                    root = rotateLeft(root);
                }
                return (root.right == null) ? root : rotateLeft(root);
            }
        }

        public void insert(int key) {
            if (root == null) {
                root = new Node(key);
                System.out.println("  insert(" + key + ")  → root = " + key);
                return;
            }

            root = splay(root, key);

            Node newNode = new Node(key);
            if (key < root.value) {
                newNode.right = root;
                newNode.left  = root.left;
                root.left     = null;
            } else {
                newNode.left  = root;
                newNode.right = root.right;
                root.right    = null;
            }

            root = newNode;
            System.out.println("  insert(" + key + ")  → new root = "
                               + root.value);
        }

        public boolean search(int key) {
            if (root == null) return false;

            root = splay(root, key);   // splaying: moves key to root

            if (root.value == key) {
                System.out.println("  search(" + key + ") → true  "
                                   + "(splayed to root)");
                return true;
            }
            System.out.println("  search(" + key + ") → false "
                               + "(not found, root = " + root.value + ")");
            return false;
        }
    }

    public static void main(String[] args) {

        SplayTree tree = new SplayTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);

        System.out.println("Splay Tree Search (10 found): " + tree.search(10));
    }
}