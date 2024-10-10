package me.duncte123.servcies;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

public class Bonjour {
    private final JmDNS jmdns;

    public Bonjour(String domain) throws IOException {
        this.jmdns = JmDNS.create(InetAddress.getLocalHost());

        jmdns.addServiceListener(domain, new TestListener());
    }

    static class TestListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent serviceEvent) {
            System.out.println("Service added " + serviceEvent.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent serviceEvent) {
            System.out.println("Service removed " + serviceEvent.getInfo());

        }

        @Override
        public void serviceResolved(ServiceEvent serviceEvent) {
            System.out.println("Service resolved " + serviceEvent.getInfo());
        }
    }
}
