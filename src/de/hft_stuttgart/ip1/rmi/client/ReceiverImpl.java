package de.hft_stuttgart.ip1.rmi.client;

import de.hft_stuttgart.ip1.rmi.interfaces.Receiver;

import java.rmi.RemoteException;

public class ReceiverImpl implements Receiver {

    @Override
    public void receive(String message) throws RemoteException {
        System.out.println("The Message is: " + message);
    }
}
