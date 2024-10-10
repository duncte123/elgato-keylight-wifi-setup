package com.corsair.elgato.model;

public enum WifiTypeCapability {
    WEP(1),
    WPA(0),
    WPA2(2);

    private final int intVal;

    WifiTypeCapability(int intVal) {
        this.intVal = intVal;
    }

    public int getIntVal() {
        return intVal;
    }
}
