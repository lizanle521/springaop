package com.lzl.design_pattern.W_Interpreter;

import java.util.StringTokenizer;

/**
 * @author lizanle
 * @Date 2019/4/19 15:57
 */
public class Context {
    private StringTokenizer tokenizer;
    private String currentToken;

    public Context(String currentToken) {
        tokenizer = new StringTokenizer(currentToken);
        nextToken();
    }

    public  String nextToken() {
        if(tokenizer.hasMoreTokens()){
            currentToken = tokenizer.nextToken();
        }else{
            currentToken = null;
        }
        return currentToken;
    }

    public String getCurrentToken(){
        return currentToken;
    }

    public void skipToken(String token){
        if(!token.equals(currentToken)){
            throw new RuntimeException(token + " is not " + currentToken);
        }
        nextToken();
    }

    public int currentNumber(){
        int number = 0;
        number = Integer.parseInt(currentToken);
        return number;
    }
}
