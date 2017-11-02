package com.ef.logging;

public class ConsoleLogger implements Loggable{

    @Override
    public void print(String msg){
        System.out.println(msg);
    }
}
