public class UninformedSearch {
 
    private int[][] board = new int[2][4];
    
    private final int[][] solvedBoard = new int[2][4];
    {
        int x = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                solvedBoard[i][j] = ++x;
            }
        }
    }

    public int countWrongTiles() {
        int wrongTiles = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != solvedBoard[i][j]) {
                    wrongTiles++;
                }
            }
        }
        return wrongTiles;
    }

    public int distanceToCorrectPosition(int tile) {
        int currentRow = -1;
        int currentColumn = -1;
        int correctRow = -1;
        int correctColumn = -1;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == tile) {
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

    public static void main(String args[]) {
        UninformedSearch search = new UninformedSearch();
    }
}
