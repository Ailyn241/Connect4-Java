package Connect4;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class C4 {
    static int N = Node.N;
    static int EMPTY = 0;
    static int HUMAN = 1;
    static int PC = 2;
    static Scanner sc = new Scanner(System.in);
    static boolean isPvsP = false;

    public static void main(String[] args) {
        System.out.println("Select a game mode \n1 - person vs PC\n2 - person vs person\n0 - exit");

        try {
            int gameMode = sc.nextInt();
            if (gameMode == 2) {
                isPvsP = true;
            } else {
                if (gameMode == 0) {
                    System.exit(0);
                } else {
                    if (gameMode != 1) {
                        System.out.println("Incorrect mode number");
                        System.exit(1);
                    }
                }
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
            System.out.println("Number wasn`t inputted");
            System.exit(2);
        }


        //создаем доску
        int board[][] = new int[N][N]; // 8x8
        for (int i = 0; i < N; i++) { //вписываем в нее нули 0 (пустые ячейки)
            for (int j = 0; j < N; j++) {
                board[i][j] = EMPTY;
            }
        }
        while (true) {
            printBoard(board); //печатаем доску в консоль
            humanTurn(board); //ход первого игрока
            printBoard(board); //печатаем доску в консоль
            boolean winner = isWinner(HUMAN, board); // проверяем на победителя
            if (winner) {
                System.out.println("Player " + HUMAN + " won");
                System.exit(0);
            }
            pcTurn(board); //ход второго игрока (или компьютера)
            winner = isWinner(PC, board);  // проверяем на победителя
            if (winner) {
                printBoard(board); //печатаем доску в консоль
                System.out.println("Player " + PC + " won");
                System.exit(0);
            }
        }
    }

    // проверка или какой-либо игрок выиграл (собрал 4 в ряд)
    static boolean isWinner(int player, int[][] board) {
        // проверка на 4 по горизонтали
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == player &&
                        board[row][col + 1] == player &&
                        board[row][col + 2] == player &&
                        board[row][col + 3] == player) {
                    return true;
                }
            }
        }
        // проверка на 4 по вертикали
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col] == player &&
                        board[row + 2][col] == player &&
                        board[row + 3][col] == player) {
                    return true;
                }
            }
        }
        // проверка на 4 по диагонали
        for (int row = 3; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == player &&
                        board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == player &&
                        board[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }
        // проверка на 4 по обратной диагонали
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player &&
                        board[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    //ход первого игрока
    static void humanTurn(int board[][]) {
        System.out.println("Player 1, Enter a column (1-" + N + ")");
        int col = sc.nextInt(); // берем значение колонки с консоли
        int row = getRow(board, col, HUMAN);  // берем первую пустую строку для этой колонки
        board[row][col] = HUMAN; // запоняем ее значением игрока
    }

    //ход второго игрока (или компьютера)
    static void pcTurn(int board[][]) {
        if (isPvsP) {
            System.out.println("Player 2, Enter a column (1-" + N + ")");
            int col = sc.nextInt(); // берем значение с консоли
            int row = getRow(board, col, PC);
            board[row][col] = PC;
        } else {
            int col = isItAlmostWin(PC, board, 3);
            if (col == 10) {
                col = isItAlmostWin(HUMAN, board, 3);
                if (col == 10) {
                    col = isItAlmostWin(PC, board, 2);
                    if (col == 10) {
                        col = isItAlmostWin(PC, board, 1);
                        if (col == 10) {
                            col = makeRandom(board);
                        }
                    }
                }
            }
            int row = getRow(board, col, PC);
            board[row][col] = PC;
        }
    }


    // расчитываем и возвращаем текущую строку в игре
    private static int getRow(int[][] board, int col, int player) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][col] == 0) {
                board[row][col] = player;
                return row;
            }
        }
        return 0;
    }

    // печатаем доску
    static void printBoard(int board[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == EMPTY) {
                    if (j == 0) {
                        System.out.print("| |");
                    } else {
                        System.out.print(" |");// на месте нулей печатаем пробел
                    }
                }
                if (board[i][j] == HUMAN) {
                    if (j != 0) {
                        System.out.print("X|"); // на месте первого игрока (1) печатаем Х
                    } else {
                        System.out.print("|X|");
                    }
                }
                if (board[i][j] == PC) {
                    if (j != 0) {
                        System.out.print("O|");// на месте второго игрока (2) печатаем О
                    } else {
                        System.out.print("|O|");
                    }
                }
            }
            System.out.println("");
        }
    }

    static int isItAlmostWin(int player, int[][] board, int neededToCheck) {
        int result = 10;
        int neededToWin = 4;
        // проверка на neededToCheck по горизонтали и вернуть 4-ю
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {

                boolean isThereSuitablePosition = false;
                int suitableCol = 10;
                int positionMissed = 0;
                for (int i = 0; i <= neededToCheck; i++) {
                    if (board[row][col + i] != player) {
                        positionMissed++;
                        if (board[row][col + i] == EMPTY && isRowsFilled(col + i, row, board)) {
                            suitableCol = col + i;
                            isThereSuitablePosition = true;
                        }
                    }
                }
                if (positionMissed > (neededToWin - neededToCheck)) {
                    isThereSuitablePosition = false;
                    suitableCol = 10;
                }

                if (isThereSuitablePosition) {
                    return suitableCol;
                } else {

                    positionMissed = 0;
                    for (int i = -1; i < neededToCheck; i++) {
                        if (col != 0 && board[row][col + i] != player) {
                            positionMissed++;
                            if (positionMissed == 1 && board[row][col + i] == EMPTY && isRowsFilled(col + i, row, board)) {
                                isThereSuitablePosition = true;
                                suitableCol = col + i;
                            }
                        }
                    }
                    if (positionMissed <= (neededToWin - neededToCheck) && isThereSuitablePosition) {

                        return suitableCol;
                    }

                }

                /*if (board[row][col] == player &&
                        board[row][col + 1] == player &&
                        board[row][col + 2] == player && isRowsFilled(col + 3, row, board) &&
                        board[row][col + 3] == EMPTY
                ) {
                    result = col + 3;
                    return result;
                }
                if (board[row][col] == player &&
                        board[row][col + 2] == player &&
                        board[row][col + 3] == player && isRowsFilled(col + 1, row, board)
                        &&
                        board[row][col + 1] == EMPTY
                ) {
                    result = col + 1;
                    return result;
                }
                if (board[row][col] == player &&
                        board[row][col + 1] == player &&
                        board[row][col + 3] == player && isRowsFilled(col + 2, row, board) &&
                        board[row][col + 2] == EMPTY
                ) {
                    result = col + 2;
                    return result;
                }
                if (col != 0 && board[row][col] == player &&
                        board[row][col + 1] == player &&
                        board[row][col + 2] == player && isRowsFilled(col - 1, row, board) &&
                        board[row][col - 1] == EMPTY
                ) {
                    result = col - 1;
                    return result;
                }*/
            }
        }
        // проверка на 3 по вертикали и вернуть 4-ю
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int suitableCol = 10;
                int positionMissed = 0;
                for (int i = 0; i <= neededToCheck; i++) {
                    if (board[row + i][col] != player) {
                        if (board[row + i][col] == EMPTY) {
                            suitableCol = col;
                        }
                        positionMissed++;
                    }
                }
                if (positionMissed <= (neededToWin - neededToCheck) && suitableCol != 10) {
                    return suitableCol;
                }
                /*if (board[row][col] == player &&
                        board[row + 1][col] == player &&
                        board[row + 2][col] == player && board[row + 3][col] == EMPTY) {
                    result = col;
                    return result;
                }*/
            }
        }
        // проверка на 3 по диагонали и вернуть 4-ю
        for (int row = 3; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                boolean isThereSuitablePosition = false;
                int suitableCol = 10;
                int positionMissed = 0;
                for (int i = 0; i <= neededToCheck; i++) {
                    if (board[row - i][col + i] != player) {
                        positionMissed++;
                        if (positionMissed == 1 && board[row - i][col + i] == EMPTY && isRowsFilled(col + i, row - i, board)) {
                            suitableCol = col + i;
                            isThereSuitablePosition = true;
                        }
                    }
                }

                if (positionMissed > (neededToWin - neededToCheck)) {
                    isThereSuitablePosition = false;
                    suitableCol = 10;
                }
                if (isThereSuitablePosition) {
                    return suitableCol;
                } else {
                    positionMissed = 0;

                    for (int i = -1; i < neededToCheck; i++) {
                        if (i == -1) {
                            if (col != 0 && row < (N - 1) && board[row + 1][col - 1] != player) {
                                positionMissed++;
                                if (positionMissed == 1 && board[row + 1][col - 1] == EMPTY && isRowsFilled(col - 1, row + 1, board)) {
                                    suitableCol = col - 1;
                                    isThereSuitablePosition = true;
                                }
                            } else {
                                if (col == 0 || row >= (N - 1)) {
                                    positionMissed++;
                                }
                            }
                        } else {
                            if (board[row - i][col + i] != player) {
                                positionMissed++;
                                if (positionMissed == 1 && board[row - i][col + i] == EMPTY && isRowsFilled(col + i, row - i, board)) {
                                    suitableCol = col + i;
                                    isThereSuitablePosition = true;
                                }
                            }
                        }
                    }

                    if (positionMissed <= (neededToWin - neededToCheck) && isThereSuitablePosition) {
                        return suitableCol;
                    }
                }



                /*if (board[row][col] == player &&
                        board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == player && isRowsFilled(col + 3, row - 3, board) &&
                        board[row - 3][col + 3] == EMPTY) {
                    result = col + 3;
                    return result;
                }
                if (board[row][col] == player &&
                        board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == EMPTY && isRowsFilled(col + 2, row - 2, board) &&
                        board[row - 3][col + 3] == player) {
                    result = col + 2;
                    return result;
                }
                if (board[row][col] == player &&
                        board[row - 1][col + 1] == EMPTY &&
                        board[row - 2][col + 2] == player && isRowsFilled(col + 1, row - 1, board) &&
                        board[row - 3][col + 3] == player) {
                    result = col + 1;
                    return result;
                }
                if (col != 0 && (row < N - 1) && board[row][col] == player &&
                        board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == player && isRowsFilled(col - 1, row + 1, board) &&
                        board[row + 1][col - 1] == EMPTY) {
                    result = col - 1;
                    return result;
                }*/
            }
        }
        // проверка на 3 по обратной диагонали и вернуть 4-ю
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                boolean isThereSuitablePosition = false;
                int suitableCol = 10;
                int positionMissed = 0;

                for (int i = 0; i <= neededToCheck; i++) {
                    if (board[row + i][col + i] != player) {
                        positionMissed++;
                        if (positionMissed == 1 && board[row + i][col + i] == EMPTY && isRowsFilled(col + i, row + i, board)) {
                            suitableCol = col + i;
                            isThereSuitablePosition = true;
                        }
                    }
                }

                if (positionMissed > (neededToWin - neededToCheck)) {
                    isThereSuitablePosition = false;
                    suitableCol = 10;
                }

                if (isThereSuitablePosition) {
                    return suitableCol;
                } else {
                    positionMissed = 0;

                    for (int i = -1; i < neededToCheck; i++) {
                        if (col != 0 && row != 0 && board[row + i][col + i] != player) {
                            positionMissed++;
                            if (positionMissed == 1 && board[row + i][col + i] == EMPTY && isRowsFilled(col + i, row + i, board)) {
                                suitableCol = col + i;
                                isThereSuitablePosition = true;
                            }
                        }
                    }

                    if (positionMissed <= (neededToWin - neededToCheck) && isThereSuitablePosition) {
                        return suitableCol;
                    }
                }

                /*if (board[row][col] == player &&
                        board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player &&
                        board[row + 3][col + 3] == EMPTY && isRowsFilled(col + 3, row + 3, board)) {
                    result = col + 3;
                    return result;
                }
                if (board[row][col] == player &&
                        board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == EMPTY &&
                        board[row + 3][col + 3] == player && isRowsFilled(col + 2, row + 2, board)) {
                    result = col + 2;
                    return result;
                }
                if (board[row][col] == player &&
                        board[row + 1][col + 1] == EMPTY &&
                        board[row + 2][col + 2] == player &&
                        board[row + 3][col + 3] == player && isRowsFilled(col + 1, row + 1, board)) {
                    result = col + 1;
                    return result;
                }
                if (row != 0 && col != 0 && board[row][col] == player &&
                        board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player &&
                        board[row - 1][col - 1] == EMPTY && isRowsFilled(col - 1, row - 1, board)) {
                    result = col - 1;
                    return result;
                }*/
            }
        }
        return result;
    }

    private static boolean isRowsFilled(int col, int row, int[][] board) {

        for (int i = board.length - 1; i < row; i--) {
            if (board[i][col] == EMPTY) {
                return false;
            }
        }

        return true;
    }

    private static int makeRandom(int[][] board) {
        int col = 10;
        boolean isFound = false;

        while (!isFound) {
            int random = (int) (Math.random() * 8);
            if (isThereEmptyPosition(board, random)) {
                col = random;
                isFound = true;
            }
        }
        return col;
    }

    private static boolean isThereEmptyPosition(int[][] board, int col) {

        for (int i = 0; i < N; i++) {
            if (board[i][col] == EMPTY) {
                return true;
            }
        }

        return false;
    }
}