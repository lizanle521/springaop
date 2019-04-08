package com.lzl.design_pattern.P_Mediator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lizanle
 * @Date 2019/4/8 12:26
 */
public class LoginFrame extends Frame implements ActionListener,Mediator {
    // 游客 和 用户选项
    private ColleagueCheckBox checkGuest;
    private ColleagueCheckBox checkLogin;

    // 用户名 密码
    private ColleagueTextField username;
    private ColleagueTextField passwd;

    // 登录和取消按钮
    private ColleagueButton cancel;
    private ColleagueButton ok;

    public LoginFrame(String caption){
        super(caption);

        setBackground(Color.lightGray);
        setLayout(new GridLayout(4,2));

        createColleague();

        add(this.checkGuest);
        add(this.checkLogin);

        add(new Label("username:"));
        add(username);
        add(new Label("passwd:"));
        add(passwd);

        add(cancel);
        add(ok);

        colleagueChanged();

        pack();
        setVisible(true);
    }

    @Override
    public void createColleague() {
        CheckboxGroup checkboxGroup = new CheckboxGroup();
        checkGuest = new ColleagueCheckBox("Guest",checkboxGroup,true);
        checkLogin = new ColleagueCheckBox("Login",checkboxGroup,true);

        username = new ColleagueTextField("",10);
        passwd = new ColleagueTextField("",10);

        cancel = new ColleagueButton("Cancle");
        ok = new ColleagueButton("Ok");


        checkGuest.setMediator(this);
        checkLogin.setMediator(this);
        username.setMediator(this);
        passwd.setMediator(this);
        cancel.setMediator(this);
        ok.setMediator(this);

        checkGuest.addItemListener(checkGuest);
        checkLogin.addItemListener(checkLogin);
        username.addTextListener(username);
        passwd.addTextListener(passwd);
        cancel.addActionListener(this);
        ok.addActionListener(this);
    }

    @Override
    public void colleagueChanged() {
        if(checkGuest.getState()){
            username.setColleagueEnabled(false);
            passwd.setColleagueEnabled(false);
            cancel.setColleagueEnabled(true);
            ok.setColleagueEnabled(true);
        }else{
            username.setColleagueEnabled(true);
            cancel.setColleagueEnabled(true);
            passwdChanged();
        }
    }

    private void passwdChanged() {
        if(username.getText().length() > 0){
            passwd.setColleagueEnabled(true);
            if(passwd.getText().length() > 0){
                ok.setColleagueEnabled(true);
            }else{
                ok.setColleagueEnabled(false);
            }
        }else{
            passwd.setColleagueEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.toString());
        System.exit(1);
    }
}
