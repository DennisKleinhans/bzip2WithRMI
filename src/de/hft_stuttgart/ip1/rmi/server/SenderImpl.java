package de.hft_stuttgart.ip1.rmi.server;

import de.hft_stuttgart.ip1.rmi.interfaces.Sender;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SenderImpl extends UnicastRemoteObject implements Sender {
    RegistrationImpl registration;
    long random;
    public SenderImpl (RegistrationImpl registration, long random) throws RemoteException{
        this.registration = registration;
        this.random = random;
    }

    @Override
    public void sendMessage(String message) {
        registration.sendMessage(message, random);
    }
}
