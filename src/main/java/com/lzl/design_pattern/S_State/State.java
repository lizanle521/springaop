package com.lzl.design_pattern.S_State;

/**
 * @author lizanle
 * @data 2019/4/13 7:31 PM
 */
public interface State {

    /**
     * 设置时间
     * @param hour
     */
    void doClock(Context context,int hour);


    /**
     * 使用金库
     */
    void doUse(Context context);

    /**
     * 按下警铃
     */
    void doAlarm(Context context);

    /**
     * 正常通话
     */
    void doPhone(Context context);
}
