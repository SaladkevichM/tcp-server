package com.server.behavior;

public class Weather implements Behavior {

    @Override
    public String apply(String input) {
        return input.toUpperCase();
    }

}
