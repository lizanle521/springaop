package com.lzl.design_pattern.P_Mediator;

/**
 * @author lizanle
 * @Date 2019/4/8 11:51
 */
public interface Colleague {
    void setMediator(Mediator mediator);

    void setColleagueEnabled(boolean enabled);
}
