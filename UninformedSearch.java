public class UninformedSearch {
 
    private int[][] board = new int[5][5];
    
    private final int[][] solvedBoard = new int[5][5];
    {
        int x = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                solvedBoard[i][j] = ++x;
            }
        }
        solvedBoard[4][4] = 0;
    }

    public void printBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printSolvedBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(solvedBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isComplete(int[][] currentBoard) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (currentBoard[i][j] != solvedBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void randomizeBoard() {
        java.util.List<Integer> tiles = new java.util.ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tiles.add(solvedBoard[i][j]);
            }
        }
        java.util.Collections.shuffle(tiles, new java.util.Random());

        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = tiles.get(index++);
            }
        }
    }

    public int[][] copyBoard(int[][] original) {
        int[][] copy = new int[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
}

    public int[] findEmptyTile(int[][] currentBoard) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (currentBoard[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    
    public java.util.List<int[][]> generateNeighbors(int[][] currentBoard) {
        java.util.List<int[][]> neighbors = new java.util.ArrayList<>();

        int[] emptyTile = findEmptyTile(currentBoard);

        if (emptyTile == null) {
            return neighbors;
        }

        int emptyRow = emptyTile[0];
        int emptyColumn = emptyTile[1];

        int[][] directions = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
        };

        for (int[] dir : directions) {
            int newRow = emptyRow + dir[0];
            int newColumn = emptyColumn + dir[1];

            if (newRow >= 0 && newRow < 5 &&
                newColumn >= 0 && newColumn < 5) {

                int[][] newBoard = copyBoard(currentBoard);

                newBoard[emptyRow][emptyColumn] = newBoard[newRow][newColumn];
                newBoard[newRow][newColumn] = 0;

                neighbors.add(newBoard);
            }
        }

        return neighbors;
    }

    public int countWrongTiles() {
        int wrongTiles = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j] != solvedBoard[i][j]) {
                    wrongTiles++;
                }
            }
        }
        return wrongTiles;
    }

    public void getValidMoves() {       
        int[] emptyTile = findEmptyTile(board);
        if (emptyTile == null) {
            return;
        }
        int emptyRow = emptyTile[0];
        int emptyColumn = emptyTile[1];

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newRow = emptyRow + dir[0];
            int newColumn = emptyColumn + dir[1];
            if (newRow >= 0 && newRow < 5 && newColumn >= 0 && newColumn < 5) {
                
                int temp = board[emptyRow][emptyColumn];

                board[emptyRow][emptyColumn] = board[newRow][newColumn];
                board[newRow][newColumn] = temp;

                printBoard();


                board[newRow][newColumn] = board[emptyRow][emptyColumn];
                board[emptyRow][emptyColumn] = temp;
            }
        }
    }

    public int distanceToCorrectPosition(int[][] currentBoard, int tile) {
        int currentRow = -1;
        int currentColumn = -1;
        int correctRow = -1;
        int correctColumn = -1;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (currentBoard[i][j] == tile) {
                    currentRow = i;
                    currentColumn = j;
                }
                if (solvedBoard[i][j] == tile) {
                    correctRow = i;
                    correctColumn = j;
                }
            }
        }

        return Math.abs(currentRow - correctRow) + Math.abs(currentColumn - correctColumn);
    }

    public int totalDistance(int[][] currentBoard) {
        int distance = 0;

        for (int tile = 1; tile <= 24; tile++) {

            distance += distanceToCorrectPosition(currentBoard, tile);
        }

        return distance;
    }

    public static void main(String[] args) {
        UninformedSearch search = new UninformedSearch();
        search.randomizeBoard();
        search.printBoard();
        System.out.println("\n");
        search.printSolvedBoard();

        int[] emptyTile = search.findEmptyTile(search.board);

        System.out.println("Blank row: " + emptyTile[0]);
        System.out.println("Blank column: " + emptyTile[1]);

        java.util.List<int[][]> neighbors = search.generateNeighbors(search.board);

        System.out.println("\nNumber of possible moves: " + neighbors.size());

        for (int[][] neighbor : neighbors) {
            for (int[] row : neighbor) {
                for (int tile : row) {
                    System.out.print(tile + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("Total distance: " + search.totalDistance(search.board));
    }
}
