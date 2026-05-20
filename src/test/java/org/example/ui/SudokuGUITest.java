package org.example.ui;

import org.example.domain.SudokuBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a interface gráfica (SudokuGUI).
 * Como testar interfaces pode ser complexo, focamos em verificar se os
 * componentes são criados corretamente e se refletem o estado do tabuleiro.
 */
class SudokuGUITest {

    private SudokuBoard board;
    private SudokuGUI gui;

    @BeforeEach
    void setUp() {
        // Criamos um tabuleiro com alguns valores iniciais
        board = new SudokuBoard();
        board.setValue(0, 0, 5); // Célula fixa
        
        gui = new SudokuGUI(board);
    }

    /**
     * TESTE: Bloqueio de board nulo.
     * A GUI não pode funcionar sem um tabuleiro de dados.
     */
    @Test
    void constructor_NullBoard() {
        assertThrows(IllegalArgumentException.class, () -> new SudokuGUI(null),
                "Deveria lançar erro ao passar board nulo");
    }

    /**
     * TESTE: Inicialização dos componentes da grade.
     * Verificamos se o painel de 9x9 células é criado corretamente.
     */
    @Test
    void createGridPanel_Initialization() {
        // Configuramos o ambiente para não abrir janela real durante o teste (Headless)
        System.setProperty("java.awt.headless", "true");
        
        JPanel panel = gui.createGridPanel();
        
        assertNotNull(panel, "O painel da grade não deve ser nulo");
        assertEquals(81, panel.getComponentCount(), "O painel deve conter 81 células (9x9)");
        
        // Verifica se a célula (0,0) que definimos como 5 no board foi preenchida na GUI
        JTextField cell00 = gui.cells[0][0];
        assertEquals("5", cell00.getText(), "A célula (0,0) deveria mostrar o valor 5");
        assertFalse(cell00.isEditable(), "Células fixas não devem ser editáveis");
        assertEquals(Color.LIGHT_GRAY, cell00.getBackground(), "Células fixas devem ter cor cinza");
    }

    /**
     * TESTE: Células vazias e editáveis.
     * Verifica se células sem valor inicial estão prontas para o jogador.
     */
    @Test
    void createGridPanel_EmptyCell() {
        System.setProperty("java.awt.headless", "true");
        gui.createGridPanel();
        
        JTextField cell01 = gui.cells[0][1]; // Célula vazia
        assertEquals("", cell01.getText(), "Célula vazia não deve ter texto");
        assertTrue(cell01.isEditable(), "Células vazias devem ser editáveis");
        assertEquals(Color.WHITE, cell01.getBackground(), "Células vazias devem ter cor branca");
    }
}
