package org.example.ui;


import org.example.domain.GameStatusEnum;
import org.example.domain.MoveResultEnum;
import org.example.domain.SudokuBoard;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class SudokuGUI {

    private static final int GRID_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;

    private static final Color FIXED_CELL_COLOR = Color.LIGHT_GRAY;
    private static final Color NORMAL_CELL_COLOR = Color.WHITE;
    private static final Color ERROR_CELL_COLOR = new Color(255, 200, 200);

    private final SudokuBoard board;

    private JFrame frame;

    final JTextField[][] cells;

    public SudokuGUI(SudokuBoard board) {

        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }

        this.board = board;
        this.cells = new JTextField[GRID_SIZE][GRID_SIZE];
    }

    public void createAndShowGUI() {

        frame = new JFrame("Sudoku");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = createGridPanel();

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.setJMenuBar(createMenuBar());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    JPanel createGridPanel() {

        JPanel gridPanel =
                new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));

        for (int row = 0; row < GRID_SIZE; row++) {

            for (int col = 0; col < GRID_SIZE; col++) {

                JTextField cell = createCell(row, col);

                cells[row][col] = cell;

                gridPanel.add(cell);
            }
        }

        return gridPanel;
    }

    private JTextField createCell(int row, int col) {

        JTextField cell = new JTextField();

        cell.setPreferredSize(new Dimension(50, 50));

        ((AbstractDocument) cell.getDocument())
                .setDocumentFilter(new SudokuFilter());

        cell.setHorizontalAlignment(JTextField.CENTER);

        cell.setFont(new Font("SansSerif", Font.BOLD, 20));

        configureCellBorder(cell, row, col);

        updateCell(cell, row, col);

        configureCellListener(cell, row, col);

        return cell;
    }

    private void configureCellBorder(JTextField cell,
                                     int row,
                                     int col) {

        int top = (row % SUBGRID_SIZE == 0) ? 2 : 1;
        int left = (col % SUBGRID_SIZE == 0) ? 2 : 1;
        int bottom = (row == GRID_SIZE - 1) ? 2 : 1;
        int right = (col == GRID_SIZE - 1) ? 2 : 1;

        cell.setBorder(
                BorderFactory.createMatteBorder(
                        top,
                        left,
                        bottom,
                        right,
                        Color.BLACK
                )
        );
    }

    private void updateCell(JTextField cell,
                            int row,
                            int col) {

        int value = board.getValue(row, col);

        if (value != 0) {
            cell.setText(String.valueOf(value));
        } else {
            cell.setText("");
        }

        if (board.isFixed(row, col)) {

            cell.setEditable(false);
            cell.setBackground(FIXED_CELL_COLOR);

        } else {

            cell.setEditable(true);
            cell.setBackground(NORMAL_CELL_COLOR);
        }
    }

    private void configureCellListener(JTextField cell,
                                       int row,
                                       int col) {

        cell.getDocument().addDocumentListener(
                new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        handleChange();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        handleChange();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        handleChange();
                    }

                    private void handleChange() {

                        if (board.isFixed(row, col)) {
                            return;
                        }

                        SwingUtilities.invokeLater(() -> {

                            String text = cell.getText().trim();

                            // remoção
                            if (text.isEmpty()) {

                                board.removeNumber(row, col);

                                cell.setBackground(NORMAL_CELL_COLOR);

                                return;
                            }

                            int number;

                            try {

                                number = Integer.parseInt(text);

                            } catch (NumberFormatException ex) {

                                cell.setText("");

                                return;
                            }

                            // remove valor anterior antes de validar novo
                            board.removeNumber(row, col);

                            MoveResultEnum result =
                                    board.insertNumber(row, col, number);

                            if (result != MoveResultEnum.OK) {

                                cell.setBackground(ERROR_CELL_COLOR);

                                JOptionPane.showMessageDialog(
                                        frame,
                                        "Jogada inválida: " + result,
                                        "Erro",
                                        JOptionPane.WARNING_MESSAGE
                                );

                                cell.setText("");

                                board.removeNumber(row, col);

                            } else {

                                cell.setBackground(NORMAL_CELL_COLOR);

                                checkGameStatus();
                            }
                        });
                    }
                }
        );
    }

    private void checkGameStatus() {

        GameStatusEnum status = board.checkStatus();

        if (status == GameStatusEnum.SOLVED) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Parabéns! Sudoku resolvido!"
            );
        }
    }

    private JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Opções");

        JMenuItem clearItem = new JMenuItem("Limpar");

        clearItem.addActionListener(e -> clearBoard());

        menu.add(clearItem);

        menuBar.add(menu);

        return menuBar;
    }

    private void clearBoard() {

        board.clearBoard();

        for (int row = 0; row < GRID_SIZE; row++) {

            for (int col = 0; col < GRID_SIZE; col++) {

                updateCell(cells[row][col], row, col);
            }
        }
    }
}