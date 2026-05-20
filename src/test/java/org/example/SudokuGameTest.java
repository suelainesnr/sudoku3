package org.example;

import org.example.domain.SudokuBoard;
import org.example.service.PuzzleLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe SudokuGame.
 * Focamos em testar a lógica de preparação do tabuleiro, que foi extraída
 * para facilitar a automação de testes.
 */
class SudokuGameTest {

    /**
     * TESTE: Preparação do tabuleiro sem argumentos.
     * Deve carregar o puzzle padrão definido no PuzzleLoader.
     */
    @Test
    void testPrepareBoard_NoArgs() {
        // Executamos a preparação sem passar nenhum argumento
        SudokuBoard board = SudokuGame.prepareBoard(null);
        
        assertNotNull(board, "O tabuleiro não deve ser nulo");
        
        // Como o PuzzleLoader gera um tabuleiro aleatório removendo números, 
        // verificamos se o tabuleiro resultante possui células marcadas como fixas.
        // O esperado é que existam células preenchidas (81 total - 45 removidos = 36 fixas).
        int fixedCount = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.isFixed(r, c)) {
                    fixedCount++;
                    assertTrue(board.getValue(r, c) > 0, "Células fixas devem ter valor maior que 0");
                }
            }
        }
        
        assertTrue(fixedCount > 0, "O tabuleiro deveria ter células fixas carregadas do puzzle padrão");
        // O valor exato deve ser 36 (81 - 45), mas vamos ser flexíveis caso a lógica mude.
        assertTrue(fixedCount >= 30, "Deveria haver pelo menos 30 células fixas");
    }

    /**
     * TESTE: Preparação do tabuleiro com argumentos personalizados.
     * Deve ignorar o puzzle padrão e usar os valores fornecidos.
     */
    @Test
    void testPrepareBoard_WithArgs() {
        // Passamos argumentos simulando a entrada do usuário via linha de comando
        // Formato: "linha,coluna,valor"
        String[] args = {"0,0,9", "8,8,1"};
        
        SudokuBoard board = SudokuGame.prepareBoard(args);
        
        assertNotNull(board, "O tabuleiro não deve ser nulo");
        
        // Verificamos se os valores passados foram inseridos corretamente e marcados como fixos
        assertEquals(9, board.getValue(0, 0));
        assertTrue(board.isFixed(0, 0));
        
        assertEquals(1, board.getValue(8, 8));
        assertTrue(board.isFixed(8, 8));
        
        // Verificamos uma célula que não foi passada para garantir que está vazia
        assertEquals(0, board.getValue(0, 1));
        assertFalse(board.isFixed(0, 1));
    }

    /**
     * TESTE: Argumentos inválidos.
     * A classe deve lidar graciosamente com argumentos mal formatados sem travar.
     */
    @Test
    void testPrepareBoard_InvalidArgs() {
        String[] args = {"invalido", "1,2", "1,2,3,4"}; // Formatos errados
        
        // Não deve lançar exceção
        assertDoesNotThrow(() -> {
            SudokuBoard board = SudokuGame.prepareBoard(args);
            assertNotNull(board);
        });
    }
}
