package de.hft_stuttgart.ip1.rmi.server;

import de.hft_stuttgart.ip1.rmi.server.RegistrationImpl;
import de.hft_stuttgart.ip1.rmi.interfaces.Registration;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting the Server");
        RegistrationImpl registrationImpl = new RegistrationImpl();

        try {
            Registry registry = LocateRegistry.createRegistry(28765);
            UnicastRemoteObject.exportObject(registrationImpl, 0);
            registry.bind(Registration.class.getName(), registrationImpl);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
