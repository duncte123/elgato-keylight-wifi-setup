package com.corsair.elgato.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessoryData {
    private int firmwareBuildNumber;
    private int hardwareBoardType;

    public int getFirmwareBuildNumber() {
        return firmwareBuildNumber;
    }

    public void setFirmwareBuildNumber(int firmwareBuildNumber) {
        this.firmwareBuildNumber = firmwareBuildNumber;
    }

    public int getHardwareBoardType() {
        return hardwareBoardType;
    }

    public void setHardwareBoardType(int hardwareBoardType) {
        this.hardwareBoardType = hardwareBoardType;
    }

    @Override
    public String toString() {
        return "AccessoryData{" +
                "firmwareBuildNumber=" + firmwareBuildNumber +
                ", hardwareBoardType=" + hardwareBoardType +
                '}';
    }
}
