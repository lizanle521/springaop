package com.lzl.design_pattern.S_State;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lizanle
 * @Date 2019/4/15 13:54
 */
public class SafeFrame extends Frame implements ActionListener,Context {
    private TextField textClock = new TextField(60);

    private TextArea textScreen = new TextArea(10,60);

    private Button buttonUse = new Button("使用金库");
    private Button buttonAlarm = new Button("按下警铃");
    private Button buttonPhone = new Button("正常通话");
    private Button buttonExit = new Button("结束");

    private State state = DayState.getInstance();

    public SafeFrame(String title) throws HeadlessException {
        super(title);

        setBackground(Color.lightGray);
        setLayout(new BorderLayout());

        add(textClock,BorderLayout.NORTH);
        textClock.setEditable(false);
        add(textScreen,BorderLayout.CENTER);
        textScreen.setEditable(false);

        Panel panel = new Panel();
        panel.add(buttonUse);
        panel.add(buttonAlarm);
        panel.add(buttonPhone);
        panel.add(buttonExit);

        add(panel,BorderLayout.SOUTH);

        pack();
        setVisible(true);

        buttonUse.addActionListener(this);
        buttonAlarm.addActionListener(this);
        buttonPhone.addActionListener(this);
        buttonExit.addActionListener(this);
    }

    @Override
    public void setClock(int hour) {
        String s = "现在的时间是：";
        if(hour < 10) {
            s += "0" + hour + ":00";
        }else{
            s += hour + ":00";
        }
        System.out.println(s);
        textClock.setText(s);
        state.doClock(this,hour);
    }


    @Override
    public void changeState(State state) {
        this.state = state;
    }

    @Override
    public void callSecurityCenter(String msg) {
        textScreen.setText("call!"+msg);
    }

    @Override
    public void recordLog(String msg) {
        textScreen.setText("record..."+msg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.toString());
        if (e.getSource() == buttonUse){
            state.doUse(this);
        }else if(e.getSource() == buttonPhone){
            state.doPhone(this);
        }else if(e.getSource() == buttonAlarm){
            state.doAlarm(this);
        }else if(e.getSource() == buttonExit){
            System.exit(0);
        }else{
            System.out.println("?"+e);
        }
    }
}
