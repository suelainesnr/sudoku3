package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para a entidade Cell.
 * Esta classe verifica se a lógica de uma célula individual do Sudoku está correta,
 * incluindo limites de posição, valores permitidos e gestão de rascunhos.
 */
class CellTest {

    /**
     * TESTE: Criação de uma célula com coordenadas válidas.
     * Verifica se a célula armazena corretamente sua linha e coluna.
     */
    @Test
    @DisplayName("Deve criar uma célula com coordenadas válidas")
    void constructor_ValidCoordinates() {
        Cell cell = new Cell(5, 3);
        assertEquals(5, cell.getRow(), "A linha deve ser 5");
        assertEquals(3, cell.getCol(), "A coluna deve ser 3");
    }

    /**
     * TESTE: Criação de uma célula com coordenadas fora do limite (0-8).
     * Verifica se o sistema impede a criação de células em posições inexistentes.
     */
    @Test
    @DisplayName("Deve lançar exceção para coordenadas fora do limite")
    void constructor_InvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> new Cell(-1, 0), "Linha negativa deve ser inválida");
        assertThrows(IllegalArgumentException.class, () -> new Cell(9, 0), "Linha > 8 deve ser inválida");
        assertThrows(IllegalArgumentException.class, () -> new Cell(0, -1), "Coluna negativa deve ser inválida");
        assertThrows(IllegalArgumentException.class, () -> new Cell(0, 9), "Coluna > 8 deve ser inválida");
    }

    /**
     * TESTE: Definição de valor fixo.
     * Testa valores válidos (1-9), null (vazio) e valores inválidos.
     */
    @Test
    @DisplayName("Deve gerenciar corretamente o valor fixo (fixed)")
    void setFixed_Validation() {
        Cell cell = new Cell(0, 0);

        // Válido
        cell.setFixed(5);
        assertEquals(5, cell.getFixed(), "Deve aceitar o valor 5");

        // Null (vazio)
        cell.setFixed(null);
        assertNull(cell.getFixed(), "Deve aceitar null");

        // Inválidos
        assertThrows(IllegalArgumentException.class, () -> cell.setFixed(0), "0 não é permitido");
        assertThrows(IllegalArgumentException.class, () -> cell.setFixed(10), "10 não é permitido");
    }

    /**
     * TESTE: Definição de valor do jogador.
     * Testa valores válidos (1-9), null (vazio) e valores inválidos.
     */
    @Test
    @DisplayName("Deve gerenciar corretamente o valor do jogador (player)")
    void setPlayer_Validation() {
        Cell cell = new Cell(0, 0);

        // Válido
        cell.setPlayer(9);
        assertEquals(9, cell.getPlayer(), "Deve aceitar o valor 9");

        // Null (vazio)
        cell.setPlayer(null);
        assertNull(cell.getPlayer(), "Deve aceitar null");

        // Inválidos
        assertThrows(IllegalArgumentException.class, () -> cell.setPlayer(0), "0 não é permitido");
        assertThrows(IllegalArgumentException.class, () -> cell.setPlayer(10), "10 não é permitido");
    }

    /**
     * TESTE: Gestão de rascunhos (drafts).
     * Verifica adição, remoção, prevenção de duplicatas e limpeza da lista.
     */
    @Test
    @DisplayName("Deve gerenciar corretamente a lista de rascunhos (drafts)")
    void draftManagement() {
        Cell cell = new Cell(0, 0);

        // Adição válida
        assertTrue(cell.addDraft(1), "Deve permitir adicionar o rascunho 1");
        assertTrue(cell.addDraft(2), "Deve permitir adicionar o rascunho 2");

        // Duplicata (não deve permitir)
        assertFalse(cell.addDraft(1), "Não deve permitir rascunho duplicado");
        assertEquals(2, cell.getDrafts().size(), "O tamanho da lista deve ser 2");

        // Valor inválido no rascunho
        assertThrows(IllegalArgumentException.class, () -> cell.addDraft(0), "Rascunho 0 é inválido");

        // Remoção
        assertTrue(cell.removeDraft(1), "Deve remover o rascunho 1");
        assertFalse(cell.getDrafts().contains(1), "A lista não deve mais conter o 1");

        // Limpeza total
        cell.clearDrafts();
        assertTrue(cell.getDrafts().isEmpty(), "A lista de rascunhos deve estar vazia após o clear");
    }

    /**
     * TESTE: Imutabilidade da lista de rascunhos retornada.
     * O método getDrafts() deve retornar uma lista que não pode ser alterada externamente.
     */
    @Test
    @DisplayName("A lista de rascunhos retornada deve ser imutável")
    void getDrafts_Immutability() {
        Cell cell = new Cell(0, 0);
        List<Integer> drafts = cell.getDrafts();
        assertThrows(UnsupportedOperationException.class, () -> drafts.add(5), "Não deve ser possível adicionar itens diretamente na lista retornada");
    }

    /**
     * TESTE: Igualdade (equals) e HashCode.
     * Duas células são consideradas iguais se estiverem na mesma posição (linha e coluna),
     * independentemente dos valores que contêm.
     */
    @Test
    @DisplayName("Deve comparar células corretamente via equals e hashCode")
    void equalityTest() {
        Cell cell1 = new Cell(1, 1);
        Cell cell2 = new Cell(1, 1);
        Cell cell3 = new Cell(1, 2);

        // Mesmo objeto
        assertEquals(cell1, cell1);

        // Objetos diferentes, mesma posição
        assertEquals(cell1, cell2, "Células na mesma posição devem ser iguais");
        assertEquals(cell1.hashCode(), cell2.hashCode(), "Células iguais devem ter o mesmo hashCode");

        // Posições diferentes
        assertNotEquals(cell1, cell3, "Células em posições diferentes não devem ser iguais");
        
        // Comparação com null ou outro tipo
        assertNotEquals(null, cell1);
        assertNotEquals("string", cell1);
    }

    /**
     * TESTE: Representação em texto (toString).
     * Verifica se o método toString contém as informações principais da célula.
     */
    @Test
    @DisplayName("Deve gerar uma representação em String correta")
    void toStringTest() {
        Cell cell = new Cell(4, 5);
        cell.setFixed(7);
        String str = cell.toString();
        
        assertTrue(str.contains("4") && str.contains("5"), "Deve conter a linha e coluna");
        assertTrue(str.contains("fixed=7"), "Deve conter o valor fixo");
    }
}
