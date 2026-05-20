package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public final class Cell {
    private final int row;
    private final int col;
    private Integer fixed;    // null = vazio
    private Integer player;   // null = vazio
    private final List<Integer> drafts = new ArrayList<>();

    public Cell(int r, int c) {
        if (r < 0 || r >= 9 || c < 0 || c >= 9) {
            throw new IllegalArgumentException("Row/col must be in range 0..8");
        }
        this.row = r;
        this.col = c;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public Integer getFixed() { return fixed; }
    public void setFixed(Integer value) {
        if (value != null && (value < 1 || value > 9)) {
            throw new IllegalArgumentException("Fixed value must be 1..9 or null");
        }
        this.fixed = value;
    }

    public Integer getPlayer() { return player; }
    public void setPlayer(Integer value) {
        if (value != null && (value < 1 || value > 9)) {
            throw new IllegalArgumentException("Player value must be 1..9 or null");
        }
        this.player = value;
    }

    // Rascunhos (drafts) - evita duplicatas
    public boolean addDraft(int v) {
        if (v < 1 || v > 9) throw new IllegalArgumentException("Draft must be 1..9");
        if (drafts.contains(v)) return false;
        return drafts.add(v);
    }
    public boolean removeDraft(int v) { return drafts.remove((Integer) v); }
    public void clearDrafts() { drafts.clear(); }
    public List<Integer> getDrafts() { return Collections.unmodifiableList(drafts); }

    @Override
    public String toString() {
        return "Cell{" + row + "," + col + " fixed=" + fixed + " player=" + player + " drafts=" + drafts + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell c = (Cell) o;
        return row == c.row && col == c.col;
    }

    @Override
    public int hashCode() {

        return Objects.hash(row, col);
    }
}


