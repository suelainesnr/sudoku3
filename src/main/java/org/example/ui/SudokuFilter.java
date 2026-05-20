package org.example.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class SudokuFilter extends DocumentFilter {
    boolean isValid(String text, int length) {
        if (text == null || text.isEmpty()) {
            return true;
        }
        if (length > 1) return false;

        if (text.length() != 1) return false;
        char c = text.charAt(0);
        return c >= '1' && c <= '9';
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        int currentLength = fb.getDocument().getLength();
        int newLength = currentLength - length + (text == null ? 0 : text.length());
        if (isValid(text, newLength)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}


