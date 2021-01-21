package de.hft_stuttgart.ip1.rmi.server;

import de.hft_stuttgart.ip1.rmi.server.SenderImpl;
import de.hft_stuttgart.ip1.rmi.interfaces.Receiver;
import de.hft_stuttgart.ip1.rmi.interfaces.Registration;
import de.hft_stuttgart.ip1.rmi.interfaces.Sender;

import java.rmi.RemoteException;
import java.util.*;

public class RegistrationImpl implements Registration {
    Map<Long, Receiver> receiverMap = new HashMap<>();
    Random random = new Random();
    @Override
    public synchronized Sender registerReceiver(Receiver receiver) throws RemoteException {
        long random = this.random.nextLong();
        receiverMap.put(random, receiver);
        return new SenderImpl(this, random);
    }

    public synchronized void sendMessage(String message, long random) {
        List<Long> deadList = new ArrayList<>();
        for (Map.Entry<Long, Receiver> rec : receiverMap.entrySet()) {
            System.out.printf("Sending from %d to %d: %s %s%n", random, rec.getKey(), rec.getValue(), message);
            if ( rec.getKey().equals(random) ) {
                System.out.println("Skipped!");
                continue;
            }
            try {
                rec.getValue().receive(message);
            }
            catch(RemoteException ex) {
                ex.printStackTrace();
                deadList.add(rec.getKey());
            }
        }
        for (Long l : deadList) {
            receiverMap.remove(l);
        }
    }
}
