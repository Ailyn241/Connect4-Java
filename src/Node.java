package Connect4;

class Node {
    static int N = 8;
    // char board[][]=new char[N][N]; //' ', 'X' , 'O'
    int board[][] = new int[N][N]; //0 , 1, 2
    Node children[];
    double value;

    Node(int board1[][]) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = board1[i][j];
            }
        }
    }

    int calculateNumOfChildren() {
        return N; //TODO: implement the function
    }

    //Node has already created the array of children
//So this funcion will create the 'n' children (Nodes)
    void createALevel(int level) {
        for (int i = 0; i < children.length; i++) {
            children[i] = createChild(i, level);
        }
    }

    //Current is the parent one
    Node createChild(int childIndex, int level) {
        Node p = new Node(board);//copy the board of the parent

//throw the piece
        int column = p.transformChildIndex2Column(childIndex); //TODO
        p.applyThrow(column); //TODO: places the token on the board
        int numOfChildrenOfp = p.calculateNumOfChildren();
        p.value = level * 10 + childIndex;
        if (level < 2)
            p.children = new Node[numOfChildrenOfp];
        else
            p.children = null;
        return p;
    }

    //This function converts a numOfChild to a columns
    int transformChildIndex2Column(int childIndex) {
//TODO: now contains dummy code
        return childIndex;
    }

    //Given a column, sets the token into the appropiate cell of the board
//First it is needed to calculate the row
    void applyThrow(int column) {
//TODO
    }
}
