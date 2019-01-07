package com.lzl.design_pattern.J_Strategy.Interfaces;

import com.lzl.design_pattern.J_Strategy.classes.Hand;

public interface Strategy {
    Hand nextHand();
    void stuty(boolean win);
}
