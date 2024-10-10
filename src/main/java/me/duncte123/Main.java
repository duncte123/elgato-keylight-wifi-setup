package me.duncte123;

import com.corsair.elgato.ClientApi;
import com.corsair.elgato.Constants;
import com.corsair.elgato.model.LightAccessory;
import com.corsair.elgato.model.WifiTypeCapability;

public class Main {
    public static void main(String[] args) throws Exception {
        // ip seems to always be 192.168.62.1, neat :)
        System.out.println("Hello world!");

        final ClientApi setupClient = new ClientApi("http://192.168.62.1:" + Constants.DEFAULT_PORT);

        final var data = setupClient.getAccessoryInfo().get();

        System.out.println(data);

        final var light = new LightAccessory(setupClient);

        light.putWifiInfo(
                "",
                "",
                WifiTypeCapability.WPA2.getIntVal(),
                data.getFirmwareBuildNumber(),
                data.getHardwareBoardType()
        ).get();
    }
}