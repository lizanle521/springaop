package com.lzl.design_pattern.L_Decorate;

/**
 * @author lizanle
 * @data 2019/4/1 9:14 PM
 */
public abstract class Display {
    /**
     * 获取横向字符数
     * @return
     */
    public abstract int getColumns();

    /**
     * 获取纵向行数
     * @return
     */
    public abstract int getRows();

    /**
     * 获取第row行的字符
     * @param row
     * @return
     */
    public abstract String getRowText(int row);

    public final void show(){
        for (int i = 0; i < getRows(); i++) {
            System.out.println(getRowText(i));
        }
    }
}
