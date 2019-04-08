package com.lzl.design_pattern.P_Mediator;

import java.awt.*;

/**
 * @author lizanle
 * @Date 2019/4/8 12:07
 */
public class ColleagueButton extends Button implements Colleague {
    private Mediator mediator;
    public ColleagueButton(String caption){
        super(caption);
    }
    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void setColleagueEnabled(boolean enabled) {
        setEnabled(enabled);
    }
}
