package com.lzl.design_pattern.L_Decorate;

/**
 * @author lizanle
 * @data 2019/4/1 9:24 PM
 */
public class SideBorder extends Border {
    public SideBorder(Display display, char boarderChar) {
        super(display);
        this.boarderChar = boarderChar;
    }

    private char boarderChar;


    @Override
    public int getColumns() {
        return display.getColumns()+2;
    }

    @Override
    public int getRows() {
        return display.getRows();
    }

    @Override
    public String getRowText(int row) {
        return boarderChar+display.getRowText(row)+boarderChar;
    }
}
