package com.lzl.design_pattern.I_Bridge;

public class Main {
    public static void main(String[] args) {
        SubDisplayDelegate displayDelegate = new SubDisplayDelegate(new CharDisplay());
        displayDelegate.multiDisplay(10);

        SubDisplayDelegate subDisplayDelegate = new SubDisplayDelegate(new StringDisplay());
        subDisplayDelegate.multiDisplay(10);
    }
}
