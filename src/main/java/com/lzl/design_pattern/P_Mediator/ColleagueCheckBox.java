package com.lzl.design_pattern.P_Mediator;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author lizanle
 * @Date 2019/4/8 12:21
 */
public class ColleagueCheckBox extends Checkbox implements ItemListener, Colleague {
    private Mediator mediator;

    public ColleagueCheckBox(String caption,CheckboxGroup checkboxGroup,boolean state){
        super(caption,checkboxGroup,state);
    }
    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void setColleagueEnabled(boolean enabled) {
        setEnabled(enabled);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        mediator.colleagueChanged();
    }
}
