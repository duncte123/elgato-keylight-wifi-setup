package com.corsair.elgato.model;

public class WiFiData {
    private final String passphrase;
    private final int securityType;
    private final String ssid;

    public WiFiData(String passphrase, String ssid, int securityType) {
        this.passphrase = passphrase;
        this.securityType = securityType;
        this.ssid = ssid;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public int getSecurityType() {
        return securityType;
    }

    public String getSsid() {
        return ssid;
    }

    public int hashCode() {
        return (((this.passphrase.hashCode() * 31) + this.ssid.hashCode()) * 31) + Integer.hashCode(this.securityType);
    }

    public String toString() {
        return "WiFiData(passphrase=" + this.passphrase + ", ssid=" + this.ssid + ", securityType=" + this.securityType + ')';
    }
}
