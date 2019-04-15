package com.lzl.design_pattern.S_State;

/**
 * @author lizanle
 * @Date 2019/4/15 12:49
 */
public class NightState implements State {
    private static NightState dayState = new NightState();

    public static NightState getInstance(){
        return dayState;
    }
    private NightState() {
    }

    @Override
    public void doClock(Context context, int hour) {
        if(hour > 9 || hour < 17){
            context.changeState(DayState.getInstance());
        }
    }

    @Override
    public void doUse(Context context) {
        context.callSecurityCenter("使用金库（晚上）");
    }

    @Override
    public void doAlarm(Context context) {
        context.callSecurityCenter("按下警铃（晚上）");
    }

    @Override
    public void doPhone(Context context) {
        context.recordLog("晚上通话录音");
    }
}
