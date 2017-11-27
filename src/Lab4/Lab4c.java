package Lab4;

public class Lab4c {

    public static void main(String[] args) {

        int size = 8;
        currentTileNum = 1;
        board = new int[size][size];

        tileBoard(0,0, 1,1,size);

        printBoard();
    }

    private static int currentTileNum;
    private static int[][] board;

    private static void tileBoard(int tr, int tc, int dr, int dc, int size) {

        // This is for when there is only one block
        if(size == 1) {
            return;
        }

        int tileNum = currentTileNum++;
        int chunkSize = size/2;

        // Top left chunk
        if(dr < (tr + chunkSize) && dc < (tc + chunkSize)) {
            System.out.println("Defect in top left chunk " + chunkSize);
            // Found defect in chunk
            tileBoard(tr, tc, dr, dc, chunkSize);
        } else {
            System.out.println("normal chunk top left size : " + chunkSize);
            // Normal chunk
            board[tr + chunkSize - 1][tc + chunkSize - 1] = tileNum;
            tileBoard(tr, tc, tr + chunkSize - 1, tc + chunkSize - 1, chunkSize);
        }

        // Top right chunk
        if(dr < (tr + chunkSize) && dc >= (tc + chunkSize)) { //  Can ignore col check due to previous if
            System.out.println("Defect in top right chunk " + chunkSize);
            // Found defect in chunk
            tileBoard(tr, tc + chunkSize, dr, dc, chunkSize);
        } else {
            System.out.println("normal chunk top right size : " + chunkSize);
            // Normal chunk
            board[tr + chunkSize - 1][tc + chunkSize] = tileNum;
            tileBoard(tr, tc + chunkSize, tr + chunkSize - 1, tc + chunkSize, chunkSize);
        }

        // Bottom right chunk
        if(dr >= (tr + chunkSize) && dc >= (tc + chunkSize)) {
            System.out.println("Defect in bottom right chunk " + chunkSize);
            // Found defect in chunk
            tileBoard(tr + chunkSize, tc + chunkSize, dr, dc, chunkSize);
        } else {
            System.out.println("normal chunk bottom right size : " + chunkSize);
            // Normal chunk
            board[tr + chunkSize][tc + chunkSize] = tileNum;
            tileBoard(tr + chunkSize, tc + chunkSize, tr + chunkSize, tc + chunkSize, chunkSize);
        }

        // Bottom left chunk
        if(dr >= (tr + chunkSize) && dc < (tc + chunkSize)) {
            System.out.println("Defect in bottom left chunk " + chunkSize);
            // Found defect in chunk
            tileBoard(tr + chunkSize, tc, dr, dc, chunkSize);
        } else {
            System.out.println("normal chunk bottom left size : " + chunkSize);
            // Normal chunk
            board[tr + chunkSize][tc + chunkSize - 1] = tileNum;
            tileBoard(tr + chunkSize, tc, tr + chunkSize, tc + chunkSize - 1, chunkSize);
        }
    }

    private static void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.println("");
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] <= 9) {
                    System.out.print(" " + board[i][j] + "  ");
                } else {
                    System.out.print(" " + board[i][j] + " ");
                }
            }
        }
    }

}
