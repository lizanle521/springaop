package com.lzl.design_pattern.W_Interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizanle
 * @Date 2019/4/19 16:14
 */
public class CommandListNode extends Node {
    private List<Node> list= new ArrayList<>();
    @Override
    public void parse(Context context) {
        while (true){
            if(context.getCurrentToken() == null){
                throw new RuntimeException("missing end");
            }else if(context.getCurrentToken().equals("end")){
                context.skipToken("end");
                break;
            }else{
                CommandNode commandNode = new CommandNode();
                commandNode.parse(context);
                list.add(commandNode);
            }
        }
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
