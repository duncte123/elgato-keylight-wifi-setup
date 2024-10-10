package me.duncte123;

import com.corsair.elgato.Constants;
import me.duncte123.servcies.Bonjour;

public class Main {
    public static void main(String[] args) throws Exception {
        // ip seems to always be 192.168.62.1, neat :)
        new Bonjour(Constants.KEYLIGHT_SETUP_DOMAIN);
        System.out.println("Hello world!");
    }
}