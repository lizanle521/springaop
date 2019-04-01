package com.lzl.design_pattern.L_Decorate;

/**
 * @author lizanle
 * @data 2019/4/1 9:26 PM
 */
public class FullBorder extends Border {
    public FullBorder(Display display) {
        super(display);
    }

    @Override
    public int getColumns() {
        return display.getColumns()+2;
    }

    @Override
    public int getRows() {
        return display.getRows()+2;
    }

    @Override
    public String getRowText(int row) {
        if(row == 0){
            return "+" + makeLine('-',display.getColumns()) +"+";
        }else if(row == display.getRows() + 1){
            return "+" + makeLine('-',display.getColumns())+"+";
        }else{
            return "|"+display.getRowText(row-1)+"|";
        }
    }

    private String makeLine(char s, int columns) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < columns; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
