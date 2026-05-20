package org.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste para a classe PuzzleLoader, que é responsável por carregar
 * os dados iniciais do tabuleiro (o desafio).
 */
class PuzzleLoaderTest {

    /**
     * TESTE: Carregamento do puzzle.
     * Verifica se o loader consegue retornar uma lista válida de strings no formato "linha,coluna,valor".
     */
    @Test
    void loadPuzzle() {
        // Chamamos o método que carrega o puzzle (ex: de um arquivo ou lista estática)
        String[] puzzleData = PuzzleLoader.loadPuzzle();

        // 1. Verificamos se os dados não são nulos e se há pelo menos um item
        assertNotNull(puzzleData, "O puzzle carregado não deve ser nulo");
        assertTrue(puzzleData.length > 0, "O puzzle carregado deve conter pelo menos um número");

        // 2. Para cada item da lista (cada número inicial do Sudoku)
        for (String entry : puzzleData) {
            // Dividimos a string por vírgula (ex: "0,0,5" vira ["0", "0", "5"])
            String[] parts = entry.split(",");
            assertEquals(3, parts.length, "Cada entrada deve ter exatamente 3 partes: linha, coluna e valor");

            // Convertemos os textos para números inteiros
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            int value = Integer.parseInt(parts[2]);

            // 3. Validamos se os valores estão dentro dos limites do Sudoku (0-8 para posições, 1-9 para valores)
            assertTrue(row >= 0 && row < 9, "A linha deve estar entre 0 e 8");
            assertTrue(col >= 0 && col < 9, "A coluna deve estar entre 0 e 8");
            assertTrue(value >= 1 && value <= 9, "O valor deve estar entre 1 e 9");
        }
    }
}