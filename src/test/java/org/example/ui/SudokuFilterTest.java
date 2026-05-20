package org.example.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe SudokuFilter.
 * Esta classe é responsável por garantir que o usuário digite apenas
 * o que é permitido em uma célula do Sudoku.
 */
class SudokuFilterTest {

    private SudokuFilter filter;

    @BeforeEach
    void setUp() {
        filter = new SudokuFilter();
    }

    /**
     * TESTE: Entradas válidas.
     * Deve aceitar qualquer dígito de 1 a 9 se o comprimento final for 1.
     */
    @Test
    void isValid_ValidInputs() {
        for (int i = 1; i <= 9; i++) {
            String input = String.valueOf(i);
            assertTrue(filter.isValid(input, 1), "Deveria aceitar o número " + input);
        }
    }

    /**
     * TESTE: Entrada vazia.
     * Deve aceitar texto vazio (quando o usuário apaga o número).
     */
    @Test
    void isValid_EmptyInput() {
        assertTrue(filter.isValid("", 0), "Deveria aceitar texto vazio");
        assertTrue(filter.isValid(null, 0), "Deveria aceitar nulo como vazio");
    }

    /**
     * TESTE: Comprimento inválido.
     * O Sudoku só permite um dígito por célula.
     */
    @Test
    void isValid_TooLong() {
        assertFalse(filter.isValid("12", 2), "Não deveria aceitar dois dígitos");
        assertFalse(filter.isValid("5", 2), "Não deveria aceitar se o comprimento final for > 1");
    }

    /**
     * TESTE: Caracteres inválidos.
     * Não deve aceitar letras, símbolos ou o número zero.
     */
    @Test
    void isValid_InvalidCharacters() {
        assertFalse(filter.isValid("0", 1), "Não deveria aceitar o número 0");
        assertFalse(filter.isValid("a", 1), "Não deveria aceitar letras");
        assertFalse(filter.isValid("#", 1), "Não deveria aceitar símbolos");
        assertFalse(filter.isValid(" ", 1), "Não deveria aceitar espaços");
    }
}
