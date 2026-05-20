package org.example;



import org.example.domain.SudokuBoard;
import org.example.service.PuzzleLoader;
import org.example.ui.SudokuGUI;

import javax.swing.*;

public class SudokuGame {


    public static void main(String[] args) {
        SudokuBoard board = prepareBoard(args);
        launchGUI(board);
    }

    /**
     * Prepara o tabuleiro com base nos argumentos da linha de comando.
     * Se não houver argumentos, carrega o puzzle padrão.
     */
    public static SudokuBoard prepareBoard(String[] args) {
        SudokuBoard board = new SudokuBoard();
        if (args == null || args.length == 0) {
            // carregar puzzle default (exemplo) ou imprimir instruções
            System.out.println("Nenhum argumento fornecido. Use: java SudokuGame row,col,value ... (row/col 0..8, value 1..9)");
            // opcional: carregar um puzzle por default:
            String[] defaultPuzzle = PuzzleLoader.loadPuzzle();

            board.initializeFixed(defaultPuzzle);
        } else {
            board.initializeFixed(args);
        }
        return board;
    }

    /**
     * Inicia a interface gráfica com o tabuleiro preparado.
     */
    private static void launchGUI(SudokuBoard board) {
        SwingUtilities.invokeLater(() -> {
            SudokuGUI ui = new SudokuGUI(board);
            ui.createAndShowGUI();
        });
    }
}
