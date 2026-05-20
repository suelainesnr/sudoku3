package org.example.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class SudokuBoard {
    private int[][] fixedBoard = new int[9][9];   // números fixos
    private int[][] playerBoard = new int[9][9];  // jogadas do usuário
    private  List<List<List<Integer>>> draftBoard = new ArrayList<>(9); // rascunhos


    public SudokuBoard() {
        for (int i = 0; i < 9; i++) {
            draftBoard.add(new ArrayList<>()); // linha
            for (int j = 0; j < 9; j++) {
                draftBoard.get(i).add(new ArrayList<>()); // coluna
            }
        }
    }

    public SudokuBoard(int[][] board) {
        this();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                fixedBoard[r][c] = board[r][c];
            }
        }
    }

    public void setValue(int row, int col, int value) {
        if (isOutOfBounds(row, col)) return;
        fixedBoard[row][col] = value;
    }

    public void initializeFixed() {
        // Método vazio para compatibilidade com testes antigos ou lógica de reset
    }

    public void initializeFixed(String[] args) {
        if (args == null) return;
        for (String arg : args) {
            if (arg == null) continue;
            String[] parts = arg.split(",");
            if (parts.length != 3) {
                //
                continue;
            }
            try {
                int row = Integer.parseInt(parts[0].trim());
                int col = Integer.parseInt(parts[1].trim());
                int val = Integer.parseInt(parts[2].trim());
                if (row < 0 || row > 8 || col < 0 || col > 8 || val < 1 || val > 9) {

                    continue;
                }
                fixedBoard[row][col] = val;
            } catch (NumberFormatException ex) {

                continue;
            }
        }
    }


    public MoveResultEnum insertNumber(int row, int col, int num) {

        // valida índices
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            return MoveResultEnum.OUT_OF_RANGE;
        }

        // valida número
        if (num < 1 || num > 9) {
            return MoveResultEnum.OUT_OF_RANGE;
        }

        // célula fixa
        if (fixedBoard[row][col] != 0) {
            return MoveResultEnum.CELL_FIXED;
        }

        // já preenchida pelo jogador
        if (playerBoard[row][col] != 0) {
            return MoveResultEnum.CELL_ALREADY_FILLED;
        }

        // regra do sudoku
        if (!isValidMove(row, col, num, false)) {
            return MoveResultEnum.INVALID_MOVE;
        }

        // sucesso
        playerBoard[row][col] = num;
        return MoveResultEnum.OK;
    }

    public boolean removeNumber(int row, int col) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            return false; // índice inválido
        }
        if (fixedBoard[row][col] != 0) {
            return false; // não pode remover fixo
        }
        if (playerBoard[row][col] == 0){
            return false; // já está vazio
        }
        playerBoard[row][col] = 0; // remove número
        return true;
    }

    public void clearBoard() {
        for (int i = 0; i < 9; i++) {
            Arrays.fill(playerBoard[i], 0);
            for (int j = 0; j < 9; j++) {
                draftBoard.get(i).get(j).clear();
            }
        }
    }
    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= 9 || col < 0 || col >= 9;
    }

    private boolean isInvalidNumber(int num) {
        return num < 1 || num > 9;
    }
    public List<int[]> getConflicts(int row, int col, int num) {
        List<int[]> conflicts = new ArrayList<>();

        // Validações
        if (isOutOfBounds(row, col) || isInvalidNumber(num)) {
            return conflicts; // ou lançar exceção
        }

        // Linha
        for (int j = 0; j < 9; j++) {
            if (fixedBoard[row][j] == num || playerBoard[row][j] == num) {
                conflicts.add(new int[]{row, j});
            }
        }

        // Coluna
        for (int i = 0; i < 9; i++) {
            if (fixedBoard[i][col] == num || playerBoard[i][col] == num) {
                conflicts.add(new int[]{i, col});
            }
        }

        // Bloco 3x3
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (fixedBoard[i][j] == num || playerBoard[i][j] == num) {
                    conflicts.add(new int[]{i, j});
                }
            }
        }

        return conflicts;
    }
    public boolean isValidMove(int row, int col, int num, boolean ignoreCurrentCell) {

        // 1. Validações
        if (isOutOfBounds(row, col) || isInvalidNumber(num)) {
            return false; // ou throw IllegalArgumentException
        }

        // 2. Busca conflitos
        List<int[]> conflicts = getConflicts(row, col, num);

        // 3. Se for ignorar a célula atual, remove ela da lista
        if (ignoreCurrentCell) {
            conflicts.removeIf(c -> c[0] == row && c[1] == col);
        }

        // 4. Se sobrou conflito → inválido
        return conflicts.isEmpty();
    }

    public GameStatusEnum checkStatus() {

        boolean hasEmpty = false;
        boolean hasConflict = false;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                int value = playerBoard[i][j] != 0
                        ? playerBoard[i][j]
                        : fixedBoard[i][j];

                // célula vazia
                if (value == 0) {
                    hasEmpty = true;
                    continue;
                }

                // verifica conflitos ignorando a própria célula
                if (!isValidMove(i, j, value, true)) {
                    hasConflict = true;
                }
            }
        }

        // cheio + conflito
        if (!hasEmpty && hasConflict) {
            return GameStatusEnum.INVALID;
        }

        // cheio + sem conflito
        if (!hasEmpty) {
            return GameStatusEnum.SOLVED;
        }

        // incompleto + conflito
        if (hasConflict) {
            return GameStatusEnum.INVALID;
        }

        // incompleto + sem conflito
        return GameStatusEnum.INCOMPLETE;
    }

    public int getValue(int row, int col) {

        if (playerBoard[row][col] != 0) {
            return playerBoard[row][col];
        }

        return fixedBoard[row][col];
    }

    // verifica se a posição pertence ao tabuleiro fixo
    public boolean isFixed(int row, int col) {
        return fixedBoard[row][col] != 0;
    }

    // acesso aos rascunhos
    public List<Integer> getDrafts(int row, int col) {

        return Collections.unmodifiableList(draftBoard.get(row).get(col));
    }
}


