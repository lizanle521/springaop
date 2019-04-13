package com.lzl.design_pattern.S_State;

/**
 * @author lizanle
 * @data 2019/4/13 8:36 PM
 */
public interface Context {
    /**
     * 设置时间
     * @param hour
     */
    void setClock(int hour);

    /**
     * 改变状态
     * @param state
     */
    void changeState(State state);

    /**
     * 联系报警中心
     * @param msg
     */
    void callSecurityCenter(String msg);

    /**
     * 在警报中心留下记录
     * @param msg
     */
    void recordLog(String msg);
}
