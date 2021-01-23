package de.hft_stuttgart.ip1.rmi.client;

import de.hft_stuttgart.ip1.rmi.client.ReceiverImpl;
import de.hft_stuttgart.ip1.rmi.interfaces.FileTransfer;
import de.hft_stuttgart.ip1.rmi.interfaces.Registration;
import de.hft_stuttgart.ip1.rmi.interfaces.Sender;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Main implements FileTransfer {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.out.println("Starting the Client");
        ReceiverImpl receiver = new ReceiverImpl();
        UnicastRemoteObject.exportObject(receiver, 0);
        Registry registry = LocateRegistry.getRegistry(28765);
        Registration registration = (Registration) registry.lookup(Registration.class.getName()); // TBD
        Sender sender = registration.registerReceiver(receiver);
        while ( true ) {
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            if ( line == null || line.strip().length() == 0 ) {
                break;
            }
            sender.sendMessage(line);
        }
        UnicastRemoteObject.unexportObject(receiver, true);
    }


    @Override
    public String[] listFiles(String pattern) throws RemoteException {
        return new String[0];
    }

    @Override
    public void sendFile(String name, byte[] data) throws RemoteException {

    }

    @Override
    public byte[] receiveFile(String name) throws RemoteException {
        return new byte[0];
    }

    @Override
    public boolean deleteFile(String name) throws RemoteException {
        return false;
    }
}
