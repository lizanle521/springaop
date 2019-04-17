package com.lzl.design_pattern.V_Command;

import java.awt.*;

/**
 * @author lizanle
 * @Date 2019/4/17 14:46
 */
public class DrawCanvas extends Canvas implements Drawable {
    private Color color = Color.RED;
    private int radius = 6;
    private CommandStack history;

    public DrawCanvas(int width,int height,CommandStack history) {
        setSize(width, height);
        setBackground(Color.white);
        this.history = history;
    }

    @Override
    public void draw(int x, int y) {
        Graphics graphics = getGraphics();
        graphics.setColor(color);
        graphics.fillOval(x-radius,y-radius,radius*2,radius*2);
    }

    @Override
    public void paint(Graphics g) {
        history.execute();
    }
}
