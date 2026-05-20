import org.example.domain.SudokuBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    private SudokuBoard board;

    /**
     * CONFIGURAÇÃO INICIAL:
     * Este método é executado AUTOMATICAMENTE antes de cada teste (@BeforeEach).
     * Ele garante que cada teste use um novo objeto SudokuBoard,
     * evitando que um teste interfira no outro.
     */
    @BeforeEach
    public void setUp() {
        board = new SudokuBoard();
    }

    /**
     * TESTE 1: Tabuleiro completamente preenchido
     *
     * O que testamos: Se o tabuleiro estiver 100% preenchido (sem zeros),
     * TODAS as células devem ser marcadas como "fixed" (fixas/imutáveis).
     *
     * Por quê: Células fixas são aquelas que fazem parte do puzzle original
     * e não podem ser modificadas pelo usuário.
     */
    @Test
    public void testInitializeFixed_AllFixed() {
        // Criamos um tabuleiro vazio
        board = new SudokuBoard();

        // Preenchemos manualmente cada célula com valores
        int[][] full = new int[][] {
            {1,2,3,4,5,6,7,8,9},
            {4,5,6,7,8,9,1,2,3},
            {7,8,9,1,2,3,4,5,6},
            {2,3,4,5,6,7,8,9,1},
            {5,6,7,8,9,1,2,3,4},
            {8,9,1,2,3,4,5,6,7},
            {3,4,5,6,7,8,9,1,2},
            {6,7,8,9,1,2,3,4,5},
            {9,1,2,3,4,5,6,7,8}
        };

        // Preenchemos o tabuleiro linha por linha, coluna por coluna
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board.setValue(r, c, full[r][c]);
            }
        }

        // Agora chamamos initializeFixed()
        board.initializeFixed();

        // Verificamos CADA célula do tabuleiro (9x9 = 81 células)
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                assertTrue(board.isFixed(r, c),
                    "Erro: célula em (" + r + "," + c + ") deveria ser fixa!");
            }
        }
    }

    /**
     * TESTE 2: Tabuleiro parcialmente preenchido
     *
     * O que testamos: Se o tabuleiro tem posições vazias (representadas por 0),
     * APENAS as células com valores iniciais devem ser marcadas como fixed.
     * As células vazias (0) NÃO devem ser marcadas como fixed.
     *
     * Por quê: O jogador precisa poder preencher as posições vazias, então
     * elas não podem estar marcadas como fixas.
     */
    @Test
    public void testInitializeFixed_PartialFixed() {
        int[][] partial = new int[][] {
            {5,3,0, 0,7,0, 0,0,0},
            {6,0,0, 1,9,5, 0,0,0},
            {0,9,8, 0,0,0, 0,6,0},
            {8,0,0, 0,6,0, 0,0,3},
            {4,0,0, 8,0,3, 0,0,1},
            {7,0,0, 0,2,0, 0,0,6},
            {0,6,0, 0,0,0, 2,8,0},
            {0,0,0, 4,1,9, 0,0,5},
            {0,0,0, 0,8,0, 0,7,9}
        };

        board = new SudokuBoard(partial);
        board.initializeFixed();

        // Verificamos cada célula
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (partial[r][c] == 0) {
                    // Se havia 0 (vazio) na posição original, NÃO deve ser fixa
                    assertFalse(board.isFixed(r, c),
                        "Erro: célula vazia em (" + r + "," + c + ") não deveria ser fixa!");
                } else {
                    // Se havia um valor na posição original, DEVE ser fixa
                    assertTrue(board.isFixed(r, c),
                        "Erro: célula preenchida em (" + r + "," + c + ") deveria ser fixa!");
                }
            }
        }
    }

    /**
     * TESTE 3: Valores não são modificados
     *
     * O que testamos: O método initializeFixed() NÃO deve alterar os valores
     * das células. Ele apenas marca quais são fixas, sem mudar nenhum número.
     *
     * Por quê: Este teste garante que o método não tem "efeitos colaterais"
     * indesejados (bug) que modificassem os dados.
     */
    @Test
    public void testInitializeFixed_DoesNotModifyValues() {
        int[][] partial = new int[][] {
            {5,3,0, 0,7,0, 0,0,0},
            {6,0,0, 1,9,5, 0,0,0},
            {0,9,8, 0,0,0, 0,6,0},
            {8,0,0, 0,6,0, 0,0,3},
            {4,0,0, 8,0,3, 0,0,1},
            {7,0,0, 0,2,0, 0,0,6},
            {0,6,0, 0,0,0, 2,8,0},
            {0,0,0, 4,1,9, 0,0,5},
            {0,0,0, 0,8,0, 0,7,9}
        };

        board = new SudokuBoard(partial);

        // Criamos uma cópia dos valores ANTES de chamar initializeFixed()
        int[][] valuesBefore = new int[9][9];
        for (int r = 0; r < 9; r++) {
            System.arraycopy(partial[r], 0, valuesBefore[r], 0, 9);
        }

        // Chamamos o método
        board.initializeFixed();

        // Verificamos que os valores DEPOIS são iguais aos valores ANTES
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                assertEquals(valuesBefore[r][c], board.getValue(r, c),
                    "Erro: valor foi modificado em (" + r + "," + c + ")!");
            }
        }
    }
}
