package org.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {
    private SudokuBoard board;

    /**
     * Este método é executado antes de cada teste.
     * Ele garante que cada teste comece com um novo tabuleiro vazio.
     */
    @BeforeEach
    void setUp() {
        board = new SudokuBoard();
    }

    /**
     * TESTE: Inicialização de um tabuleiro vazio.
     * Verificamos se todas as 81 posições começam com valor 0,
     * não são fixas e não possuem rascunhos (drafts).
     */
    @Test
    void initializeEmpty() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(0, board.getValue(row, col), "O valor inicial deve ser 0");
                assertFalse(board.isFixed(row, col), "Nenhuma célula deve ser fixa inicialmente");
                assertTrue(board.getDrafts(row, col).isEmpty(), "A lista de rascunhos deve estar vazia");
            }
        }
    }

    /**
     * TESTE: Inserção válida de um número.
     * Verificamos se, ao inserir um número permitido em uma célula vazia,
     * o resultado é OK e o valor é realmente salvo no tabuleiro.
     */
    @Test
    void insertNumber_Valid() {
        MoveResultEnum result = board.insertNumber(0, 0, 5);
        assertEquals(MoveResultEnum.OK, result, "A inserção deveria ser permitida");
        assertEquals(5, board.getValue(0, 0), "O valor no tabuleiro deve ser o que foi inserido");
    }

    /**
     * TESTE: Tentativa de inserir em célula fixa.
     * Células fixas (do puzzle original) não podem ser alteradas pelo jogador.
     */
    @Test
    void insertNumber_FixedCell() {
        // Simulamos uma célula fixa definindo-a via sistema (setValue)
        board.setValue(0, 0, 5);
        
        // Tentamos mudar o valor como se fosse um jogador (insertNumber)
        MoveResultEnum result = board.insertNumber(0, 0, 1);
        
        assertEquals(MoveResultEnum.CELL_FIXED, result, "Não deve permitir alterar célula fixa");
        assertEquals(5, board.getValue(0, 0), "O valor original da célula fixa deve ser mantido");
    }

    /**
     * TESTE: Conflito na mesma linha.
     * O Sudoku não permite números repetidos na mesma linha.
     */
    @Test
    void insertNumber_ConflictRow() {
        board.insertNumber(0, 0, 5); // Insere 5 na coluna 0
        MoveResultEnum result = board.insertNumber(0, 1, 5); // Tenta inserir 5 na coluna 1 (mesma linha)
        assertEquals(MoveResultEnum.INVALID_MOVE, result, "Deveria detectar conflito na linha");
    }

    /**
     * TESTE: Conflito na mesma coluna.
     * O Sudoku não permite números repetidos na mesma coluna.
     */
    @Test
    void insertNumber_ConflictColumn() {
        board.insertNumber(0, 0, 5); // Insere 5 na linha 0
        MoveResultEnum result = board.insertNumber(1, 0, 5); // Tenta inserir 5 na linha 1 (mesma coluna)
        assertEquals(MoveResultEnum.INVALID_MOVE, result, "Deveria detectar conflito na coluna");
    }

    /**
     * TESTE: Conflito no bloco 3x3.
     * O Sudoku não permite números repetidos dentro do mesmo quadrante 3x3.
     */
    @Test
    void insertNumber_ConflictBlock() {
        board.insertNumber(0, 0, 5); // Insere 5 no canto superior esquerdo do bloco
        MoveResultEnum result = board.insertNumber(1, 1, 5); // Tenta inserir 5 no meio do mesmo bloco
        assertEquals(MoveResultEnum.INVALID_MOVE, result, "Deveria detectar conflito no bloco 3x3");
    }

    /**
     * TESTE: Remoção de número inserido pelo jogador.
     * Verificamos se o jogador consegue limpar uma célula que ele mesmo preencheu.
     */
    @Test
    void removeNumber_PlayerValue() {
        board.insertNumber(0, 0, 5);
        boolean removed = board.removeNumber(0, 0);
        assertTrue(removed, "A remoção de um valor do jogador deve retornar verdadeiro");
        assertEquals(0, board.getValue(0, 0), "A célula deve voltar a ser 0");
    }

    /**
     * TESTE: Tentativa de remover número fixo.
     * O jogador não pode apagar os números que já vieram no desafio.
     */
    @Test
    void removeNumber_FixedValue() {
        board.setValue(0, 0, 5); // Define como valor fixo
        boolean removed = board.removeNumber(0, 0);
        assertFalse(removed, "Não deve ser possível remover um valor fixo");
        assertEquals(5, board.getValue(0, 0), "O valor fixo deve permanecer intacto");
    }

    /**
     * TESTE: Limpar todo o tabuleiro.
     * Garante que todas as jogadas do usuário sejam apagadas, mas mantendo a lógica de reset.
     */
    @Test
    void clearBoard() {
        board.insertNumber(0, 0, 5);
        board.clearBoard();
        assertEquals(0, board.getValue(0, 0), "O tabuleiro deve estar limpo após o clearBoard");
    }

    /**
     * TESTE: Status de Jogo Resolvido.
     * Verifica se o sistema reconhece quando o tabuleiro está completo e correto.
     */
    @Test
    void checkStatus_Solved() {
        // Matriz completa e válida de Sudoku
        int[][] full = new int[][] {
            {1,2,3, 4,5,6, 7,8,9},
            {4,5,6, 7,8,9, 1,2,3},
            {7,8,9, 1,2,3, 4,5,6},
            {2,3,4, 5,6,7, 8,9,1},
            {5,6,7, 8,9,1, 2,3,4},
            {8,9,1, 2,3,4, 5,6,7},
            {3,4,5, 6,7,8, 9,1,2},
            {6,7,8, 9,1,2, 3,4,5},
            {9,1,2, 3,4,5, 6,7,8}
        };
        // Inicializa o tabuleiro com essa matriz (todos serão fixos neste caso)
        board = new SudokuBoard(full);
        assertEquals(GameStatusEnum.SOLVED, board.checkStatus(), "O status deveria ser SOLVED (Resolvido)");
    }

    /**
     * TESTE: Status de Jogo Incompleto.
     * O tabuleiro tem jogadas válidas, mas ainda há espaços vazios.
     */
    @Test
    void checkStatus_Incomplete() {
        board.insertNumber(0, 0, 1);
        assertEquals(GameStatusEnum.INCOMPLETE, board.checkStatus(), "O status deveria ser INCOMPLETE");
    }

    /**
     * TESTE: Status de Jogo Inválido.
     * Verifica se o sistema detecta erros (números repetidos) no tabuleiro.
     */
    @Test
    void checkStatus_Invalid() {
        // Forçamos a inserção de dois números '1' na mesma linha via setValue (que ignora as regras de insertNumber)
        // Isso simula um estado de erro que o checkStatus deve detectar.
        board.setValue(0, 0, 1);
        board.setValue(0, 1, 1);
        assertEquals(GameStatusEnum.INVALID, board.checkStatus(), "O status deveria ser INVALID devido ao conflito");
    }
}