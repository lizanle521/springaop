package com.lzl.design_pattern.S_State;

/**
 * @author lizanle
 * @Date 2019/4/15 12:49
 */
public class DayState implements State {
    private static DayState dayState = new DayState();

    public  static DayState getInstance(){
        return dayState;
    }
    private DayState() {
    }

    @Override
    public void doClock(Context context, int hour) {
        if(hour <= 9 || hour >= 17){
            context.changeState(NightState.getInstance());
        }
    }

    @Override
    public void doUse(Context context) {
        context.recordLog("使用金库(白天)");
    }

    @Override
    public void doAlarm(Context context) {
        context.callSecurityCenter("按下警铃（白天）");
    }

    @Override
    public void doPhone(Context context) {
        context.callSecurityCenter("正常通话（白天）");
    }
}
