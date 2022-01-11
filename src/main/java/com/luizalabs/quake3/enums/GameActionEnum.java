package com.luizalabs.quake3.enums;

public enum GameActionEnum {

    START_GAME("InitGame:"),
    NEW_PLAYER("ClientUserinfoChanged:"),
    KILL("Kill:"),
    WORLD("<world>");


    String value;

    GameActionEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
