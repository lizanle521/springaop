package com.lzl.design_pattern.V_Command;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author lizanle
 * @Date 2019/4/17 14:43
 */
public class CommandStack implements Command {
    private Stack<Command> commandStack = new Stack<>();

    @Override
    public void execute() {
        Iterator<Command> iterator = commandStack.iterator();
        while (iterator.hasNext()){
            Command next = iterator.next();
            next.execute();
        }
    }

    public void append(Command command){
        if(command != this){
            commandStack.push(command);
        }
    }

    public void undo(){
        if(!commandStack.isEmpty()){
            commandStack.pop();
        }
    }

    public void clear(){
        commandStack.clear();
    }
}
