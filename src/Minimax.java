package Connect4;

public class Minimax {
    public static void main(String argv[]) {
        int N = C4.N;
        int board[][] = new int[N][N]; //create the variable
        for (int i = 0; i < N; i++) { //set 0's
            for (int j = 0; j < N; j++) {
                board[i][j] = 0;
            }
        }
        Node root = createTree(board);
//displayTree(root);
        displayTreeRecursive(root, 1);
    }

    static void printNode(Node p, int level) {
        for (int k = 1; k <= level; k++)
            System.out.print(" ");
        System.out.println(p.value);
    }

    static void displayTreeRecursive(Node parent, int level) {
        if (parent.children == null) return;
        for (int i = 0; i < parent.children.length; i++) {
            printNode(parent.children[i], level);
            displayTreeRecursive(parent.children[i], level + 1);
        }
    }

    static void displayTree(Node root) {
        for (int i = 0; i < root.children.length; i++) {
            System.out.println(root.children[i].value);
            for (int j = 0; j < root.children[i].children.length; j++) {
                System.out.println(root.children[i].children[j].value);

            }
        }
    }

    static Node createRootNode(int board[][]) {

        Node p = new Node(board);
        return p;
    }

    static Node createTree(int board[][]) {
        Node root = createRootNode(board);
        int rootChildren = root.calculateNumOfChildren();
        root.children = new Node[rootChildren];
//create a 2 levels tree
        root.createALevel(1);
        for (int i = 0; i < root.children.length; i++) {
            root.children[i].createALevel(2);
        }
        return root;
    }

}
