package org.example.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PuzzleLoader {

    private static final Random RANDOM = new Random();

    /**
     * Gera um puzzle no formato:
     * "row,col,value"
     */
    public static String[] loadPuzzle() {

        int[][] puzzle = generatePuzzle();

        List<String> data = new ArrayList<>();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (puzzle[row][col] != 0) {
                    data.add(row + "," + col + "," + puzzle[row][col]);
                }
            }
        }

        return data.toArray(new String[0]);
    }

    /**
     * Cria um puzzle base e remove posições aleatórias
     */
    private static int[][] generatePuzzle() {

        int[][] board = {
                {1, 6, 2, 5, 9, 7, 8, 4, 3},
                {4, 7, 9, 1, 3, 2, 6, 5, 8},
                {5, 3, 8, 6, 4, 8, 2, 9, 1},

                {9, 1, 3, 4, 2, 7, 5, 8, 6},
                {6, 8, 7, 9, 1, 5, 3, 2, 4},
                {2, 5, 4, 8, 6, 3, 1, 7, 9},

                {3, 4, 5, 8, 7, 1, 9, 6, 2},
                {7, 2, 6, 3, 4, 9, 8, 1, 5},
                {8, 9, 1, 2, 5, 6, 4, 3, 7}
        };

        removeNumbers(board, 45);

        return board;
    }

    /**
     * Remove números aleatórios do tabuleiro
     */
    private static void removeNumbers(int[][] board, int amount) {

        while (amount > 0) {

            int row = RANDOM.nextInt(9);
            int col = RANDOM.nextInt(9);

            if (board[row][col] != 0) {
                board[row][col] = 0;
                amount--;
            }
        }
    }
}
