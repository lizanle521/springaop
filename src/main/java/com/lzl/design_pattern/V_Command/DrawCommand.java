package com.lzl.design_pattern.V_Command;

import java.awt.*;

/**
 * @author lizanle
 * @Date 2019/4/17 14:41
 */
public class DrawCommand implements Command {
    private Drawable drawable;
    private Point point;

    public DrawCommand(Drawable drawable, Point point) {
        this.drawable = drawable;
        this.point = point;
    }

    @Override
    public void execute() {
        drawable.draw(point.x,point.y);
    }
}
